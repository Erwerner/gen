package core.soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import core.datatypes.Decisions;
import core.datatypes.Direction;
import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import core.genes.Genome;
import core.genes.MoveDecisionsProbability;
import core.soup.block.Block;
import core.soup.block.BlockGrid;
import core.soup.block.BlockType;
import core.soup.block.Enemy;
import core.soup.block.Food;
import core.soup.block.IdvmCell;
import core.soup.block.Partner;
import core.soup.block.iBlock;
import globals.Config;

public class Idvm extends Block implements iIdvm {
	private static final long serialVersionUID = 1L;
	Genome mGenomeOrigin;
	Genome mGenomeUsing;
	private IdvmCellGrid mCellGrid = new IdvmCellGrid();
	private HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> mMovementSequences = new HashMap<IdvmState, ArrayList<MoveDecisionsProbability>>();
	private BlockGrid mBlockGrid;
	private int mStepCount;
	private int mEnergy = Config.cInitialEnergy;
	private IdvmMoveCalculation mMoveCalculation;
	private IdvmSensor mIdvmSensor;
	private int mPartnerCount = 0;
	// TODO 2 IMPL Target Order in Genome

	public Idvm(Genome pGenome) {
		super(BlockType.IDVM);
		try {
			mPos = new Pos(0, 0);
			mGenomeOrigin = (Genome) pGenome.clone();
			mGenomeUsing = (Genome) pGenome.clone();

			grow();
			grow();
			grow();
			grow();

			for (IdvmState iState : pGenome.moveSequencesForState.keySet()) {
				@SuppressWarnings("unchecked")
				ArrayList<MoveDecisionsProbability> lMoveProbability = (ArrayList<MoveDecisionsProbability>) pGenome.moveSequencesForState
						.get(iState).clone();
				mMovementSequences.put(iState, lMoveProbability);
			}

		} catch (CloneNotSupportedException e) {
			throw new RuntimeException();
		}
	}

	private void grow() {
		if (mEnergy - Config.cGrowCost < 0)
			return;
		if (mGenomeUsing.cellGrow.size() == 0)
			return;
		IdvmCell lCell = mGenomeUsing.cellGrow.get(0);
		mGenomeUsing.cellGrow.remove(0);
		if (lCell.getBlockType() != BlockType.NULL) {
			mCellGrid.appendCell(lCell, mPos);
			mEnergy = mEnergy - Config.cGrowCost;
		}
		popAllSequences();
	}

	public boolean isAlive() {
		if (mEnergy <= 0)
			return false;
		for (iBlock iCell : getUsedBlocks()) {
			if (iCell.getBlockType() == BlockType.LIFE) {
				return true;
			}
		}
		return false;
	}

	public Boolean isHungry() {
		return mEnergy < mGenomeUsing.getHunger().getValue();
	}

	public iBlock setPosition(Pos pPos) {
		super.setPosition(pPos);
		mCellGrid.refreshAllCells(mPos);
		return this;
	}

	public ArrayList<iBlock> getUsedBlocks(BlockType pBlockType) {
		return mCellGrid.getGridBlocksOfType(pBlockType);
	}

	public void step() {
		mStepCount++;
		for (@SuppressWarnings("unused")
		iBlock iCount : getUsedBlocks(BlockType.LIFE)) {
			for (int i = 0; i < Config.cLifeEnergyCost; i++)
				mEnergy--;
		}
		move();
	}

	// TODO 5 IMPL turn
	@SuppressWarnings("unused")
	private void move() {
		for (iBlock iCount : getUsedBlocks(BlockType.MOVE)) {
			IdvmState lState = getState();
			Direction lTargetDirection = mIdvmSensor.getTargetDirection(lState, getUsedBlocks(BlockType.SENSOR));
			for (int i = 0; i < 10; i++) {
				try {
					Pos lNewPos = mMoveCalculation.getMovingPosition(this, mMovementSequences, lTargetDirection);
					if (!lNewPos.equals(mPos)) {
						for (int iEnergyCount = 0; iEnergyCount < Config.cMoveEnergyCost; iEnergyCount++)
							mEnergy--;
						setPosition(lNewPos);
						break;
					}
				} catch (PosIsOutOfGrid e) {
				}
			}
		}
	}

	public void interactWithFood(Food pFood) {
		mEnergy = mEnergy + Config.cFoodEnergy;
		if (mEnergy > Config.cMaxEnergy)
			mEnergy = Config.cMaxEnergy;
		grow();
		pFood.setNull();
	}

	public void interactWithPartner(Partner pPartner) {
		mBlockGrid.setRandomBlock(pPartner);
		if (mEnergy - Config.cPairingCost < 0)
			return;
		mEnergy = mEnergy - Config.cPairingCost;
		mPartnerCount++;
	}

	private void popAllSequences() {
		for (Entry<IdvmState, ArrayList<MoveDecisionsProbability>> iSequence : mMovementSequences.entrySet()) {
			try {
				iSequence.getValue().remove(0);
			} catch (RuntimeException e) {
				// Empty Sequence
			}
		}
	}

	// TODO 4 IMPL defence
	public void interactWithEnemy(Enemy pEnemy) {
		Pos lKillPos = new Pos(pEnemy.getPos().x - mPos.x + 1, pEnemy.getPos().y - mPos.y + 1);
		mCellGrid.removeCell(lKillPos);
	}

	public HashMap<Pos, Sensor> getDetectedPos() {
		ArrayList<iBlock> lSensors = getUsedBlocks(BlockType.SENSOR);
		return mIdvmSensor.getDetectedPos(lSensors);
	}

	public void setBlockGrid(BlockGrid pBlockGrid) {
		mBlockGrid = pBlockGrid;
		mMoveCalculation = new IdvmMoveCalculation(mBlockGrid);
		mIdvmSensor = new IdvmSensor(mBlockGrid);
	}

	public int getStepCount() {
		return mStepCount;
	}

	public int getPartnerCount() {
		return mPartnerCount;
	}

	public Direction getTargetDirection() {
		return mIdvmSensor.getTargetDirection(getState(), getUsedBlocks(BlockType.SENSOR));
	}

	public int getEnergyCount() {
		return mEnergy;
	}

	public Decisions getCalculatedDirection() {
		return mMoveCalculation.getCalculatedDecision();
	}

	public Genome getGenomeOrigin() {
		return mGenomeOrigin;
	}

	// TODO 4 IMPL cell type connection
	public ArrayList<iBlock> getUsedBlocks() {
		return mCellGrid.getGridBlocks();
	}

	public IdvmState getState() {
		return mIdvmSensor.getState(getUsedBlocks(BlockType.SENSOR).size() != 0, isHungry(),getDetectedPos(), mGenomeUsing.mTargetDetectionOrder);
	}
}
