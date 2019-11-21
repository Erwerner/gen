package core.soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import core.genes.Genome;
import core.genes.MoveDecisionsProbability;
import core.soup.block.Block;
import core.soup.block.BlockType;
import core.soup.block.Enemy;
import core.soup.block.Food;
import core.soup.block.IdvmCell;
import core.soup.block.iBlock;
import core.soup.block.iBlockGrid;

public class Idvm extends Block implements iIdvm {

	public static final int cMaxEnergy = 300;
	private Genome mGenomeOrigin;
	private IdvmCell[][] mCellGrid = new IdvmCell[4][4];
	private HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> mMovementSequences = new HashMap<IdvmState, ArrayList<MoveDecisionsProbability>>();
	private ArrayList<IdvmCell> mCellGrow;
	private int mHunger;
	private iBlockGrid mBlockGrid;
	private int mStepCount;
	private int mEnergy = cMaxEnergy / 2;
	private iIdvmMoveCalculation mMoveCalculation;
	private IdvmSensor mIdvmSensor;

	public Idvm(Genome pGenome) {
		super(BlockType.IDVM);
		mPos = new Pos(0, 0);
		try {
			mGenomeOrigin = (Genome) pGenome.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException();
		}
		mCellGrow = pGenome.cellGrow;
		mHunger = pGenome.getHunger().getValue();

		grow();
		grow();
		grow();
		grow();

		for (IdvmState iState : pGenome.moveSequencesForState.keySet()) {
			addaptMovementSequence(iState, pGenome);
		}
	}

	@SuppressWarnings("unchecked")
	private void addaptMovementSequence(IdvmState pState, Genome pGenome) {
		ArrayList<MoveDecisionsProbability> lMoveProbability = (ArrayList<MoveDecisionsProbability>) pGenome.moveSequencesForState
				.get(pState).clone();
		mMovementSequences.put(pState, lMoveProbability);
	}

	// TODO REF Class Cell Grid
	private void grow() {
		if (mCellGrow.size() == 0)
			return;
		IdvmCell lCell = mCellGrow.get(0);
		mCellGrow.remove(0);
		Pos lPos = lCell.getPosOnIdvm();
		mCellGrid[lPos.x + 1][lPos.y + 1] = lCell;
		refreshCellPos(lPos.x + 1, lPos.y + 1);
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
		return mEnergy < mHunger;
	}

	public iBlock setPosition(Pos pPos) {
		super.setPosition(pPos);
		for (int x = 0; x <= 3; x++) {
			for (int y = 0; y <= 3; y++) {
				refreshCellPos(x, y);
			}
		}
		return this;
	}

	// TODO REF Class Cell Grid
	private void refreshCellPos(int pCellX, int pCellY) {
		IdvmCell lCell = mCellGrid[pCellX][pCellY];
		if (lCell != null) {
			Pos lNewPos = new Pos(mPos.x - 1 + pCellX, mPos.y - 1 + pCellY);
			lCell.setPosition(lNewPos);
		}
	}

	// TODO REF Class Cell Grid
	public ArrayList<iBlock> getUsedBlocks(BlockType pBlockType) {
		ArrayList<iBlock> lBlocks = new ArrayList<iBlock>();
		for (iBlock iBlock : getUsedBlocks())
			if (iBlock.getBlockType() == pBlockType)
				lBlocks.add(iBlock);
		return lBlocks;
	}

	// TODO REF Class Cell Grid
	public ArrayList<iBlock> getUsedBlocks() {
		ArrayList<iBlock> lBlocks = new ArrayList<iBlock>();
		for (IdvmCell[] iRow : mCellGrid) {
			for (iBlock iCell : iRow) {
				if (iCell != null) {
					lBlocks.add(iCell);
				}
			}
		}
		return lBlocks;
	}

	// TODO REF Class Cell Grid
	public void killCell(Pos pPos) {
		mCellGrid[pPos.x - mPos.x + 1][pPos.y - mPos.y + 1] = null;
	}

	public void step() {
		mStepCount++;
		for (iBlock iCount : getUsedBlocks(BlockType.LIFE))
			mEnergy--;
		move();
	}

	@SuppressWarnings("unused")
	private void move() {
		for (iBlock iCount : getUsedBlocks(BlockType.MOVE)) {
			for (int i = 0; i < 10; i++) {
				try {
					Pos lNewPos = mMoveCalculation.getMovingPosition(this, mMovementSequences);
					setPosition(lNewPos);
					break;
				} catch (PosIsOutOfGrid e) {
				}
			}
		}
	}

	public void interactWithFood(Food pFood) {
		mEnergy = cMaxEnergy;
		grow();
		popAllSequences();
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

	public void interactWithEnemy(Enemy pEnemy) {
		killCell(pEnemy.getPos());
	}

	public IdvmState getState() {
		return mIdvmSensor.getState(getDetectedPos());
	}

	public HashMap<Pos, Sensor> getDetectedPos() {
		ArrayList<iBlock> lSensors = getUsedBlocks(BlockType.SENSOR);
		return mIdvmSensor.getDetectedPos(lSensors);
	}

	public void setBlockGrid(iBlockGrid pBlockGrid) {
		mBlockGrid = pBlockGrid;
		mMoveCalculation = new IdvmMoveCalculation(mBlockGrid);
		mIdvmSensor = new IdvmSensor(mBlockGrid);
	}

	public int getStepCount() {
		return mStepCount;
	}

	// TODO REF Class Block Grid
	public void detectCollisions() {
		for (iBlock iBlock : getUsedBlocks()) {
			Pos iPos = iBlock.getPos();
			iBlock lGridBlock;
			try {
				lGridBlock = mBlockGrid.getBlock(iPos);
				if (lGridBlock != null) {
					switch (lGridBlock.getBlockType()) {
					case FOOD:
						if (iBlock.getBlockType() != BlockType.SENSOR)
							break;
						interactWithFood((Food) lGridBlock);
						lGridBlock.setNull();
						break;
					case ENEMY:
						interactWithEnemy((Enemy) lGridBlock);
						break;
					default:
						break;
					}
				}
			} catch (PosIsOutOfGrid e) {
			}
		}
	}

	public Decisions getTargetDirection() {
		return mMoveCalculation.getTargetDirection(getState(), getDetectedPos());
	}

	public int getEnergyCount() {
		return mEnergy;
	}

	public Decisions getCalculatedDirection() {
		return mMoveCalculation.getCalculatedDirection();
	}

	public Genome getGenomeOrigin() {
		return mGenomeOrigin;
	}
}
