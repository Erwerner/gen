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
import core.soup.block.IdvmCell;
import core.soup.block.iBlock;
import globals.Config;

public class Idvm extends Block implements iIdvm {
	private static final long serialVersionUID = 1L;
	private int mStepCount;
	private int mEnergy = Config.cInitialEnergy;
	private int mPartnerCount = 0;
	Genome mGenomeOrigin;
	Genome mGenomeUsing;
	// TODO 3 REF use frome Genome
	private HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> mMovementSequences = new HashMap<IdvmState, ArrayList<MoveDecisionsProbability>>();
	private IdvmCellGrid mCellGrid = new IdvmCellGrid();
	private IdvmMoveCalculation mMoveCalculation;
	private IdvmSensor mIdvmSensor;

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

			mMovementSequences = mGenomeUsing.moveSequencesForState;

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

		for (Entry<IdvmState, ArrayList<MoveDecisionsProbability>> iSequence : mMovementSequences.entrySet())
			if (iSequence.getValue().size() > 0)
				iSequence.getValue().remove(0);
	}

	public boolean isAlive() {
		return mEnergy > 0 && !mCellGrid.getGridBlocksOfType(BlockType.LIFE).isEmpty();
	}

	public Boolean isHungry() {
		return mEnergy < mGenomeUsing.getHunger().getValue();
	}

	public iBlock setPosition(Pos pPos) {
		super.setPosition(pPos);
		mCellGrid.refreshAllCells(mPos);
		return this;
	}

	public void step() {
		mStepCount++;
		for (@SuppressWarnings("unused")
		iBlock iCount : mCellGrid.getGridBlocksOfType(BlockType.LIFE)) {
			for (int i = 0; i < Config.cLifeEnergyCost; i++)
				mEnergy--;
		}
		move();
	}

	// TODO 5 IMPL turn
	@SuppressWarnings("unused")
	private void move() {
		for (iBlock iCount : mCellGrid.getGridBlocksOfType(BlockType.MOVE)) {
			IdvmState lState = getState();
			Direction lTargetDirection = mIdvmSensor.getTargetDirection(lState,
					mCellGrid.getGridBlocksOfType(BlockType.SENSOR));
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

	public void interactWithFood() {
		mEnergy += Config.cFoodEnergy;
		if (mEnergy > Config.cMaxEnergy)
			mEnergy = Config.cMaxEnergy;
		grow();
	}

	public void interactWithPartner() {
		if (mEnergy - Config.cPairingCost < 0)
			return;
		mEnergy -= Config.cPairingCost;
		mPartnerCount++;
	}

	// TODO 4 IMPL defence
	public void interactWithEnemy(Enemy pEnemy) {
		Pos lKillPos = new Pos(pEnemy.getPos().x - mPos.x + 1, pEnemy.getPos().y - mPos.y + 1);
		mCellGrid.removeCell(lKillPos);
	}

	public HashMap<Pos, Sensor> getDetectedPos() {
		ArrayList<iBlock> lSensors = mCellGrid.getGridBlocksOfType(BlockType.SENSOR);
		return mIdvmSensor.getDetectedPos(lSensors);
	}

	public void setBlockGrid(BlockGrid pBlockGrid) {
		mMoveCalculation = new IdvmMoveCalculation(pBlockGrid);
		mIdvmSensor = new IdvmSensor(pBlockGrid);
	}

	public int getStepCount() {
		return mStepCount;
	}

	public int getPartnerCount() {
		return mPartnerCount;
	}

	public Direction getTargetDirection() {
		return mIdvmSensor.getTargetDirection(getState(), mCellGrid.getGridBlocksOfType(BlockType.SENSOR));
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

	public ArrayList<iBlock> getUsedBlocks() {
		return mCellGrid.getGridBlocks();
	}

	public IdvmState getState() {
		return mIdvmSensor.getState(mCellGrid.getGridBlocksOfType(BlockType.SENSOR).size() != 0, isHungry(),
				getDetectedPos(), mGenomeUsing.mTargetDetectionOrder);
	}
}
