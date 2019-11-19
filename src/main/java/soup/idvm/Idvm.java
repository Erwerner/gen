package soup.idvm;

import genes.Genome;
import genes.MoveProbability;
import globals.exceptions.ExOutOfGrid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import soup.block.Block;
import soup.block.BlockType;
import soup.block.Enemy;
import soup.block.Food;
import soup.block.IdvmCell;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import datatypes.Direction;
import datatypes.Pos;

public class Idvm extends Block implements iIdvm {

	public static final int cMaxEnergy = 300;
	@SuppressWarnings("unused")
	private Genome mGenomeOrigin;
	private IdvmCell[][] mCellGrid = new IdvmCell[4][4];
	private HashMap<IdvmState, ArrayList<MoveProbability>> mMovementSequences = new HashMap<IdvmState, ArrayList<MoveProbability>>();
	private ArrayList<IdvmCell> mCellGrow;
	private int mHunger;
	private iBlockGrid mBlockGrid;
	private int mStepCount;
	private int mEnergy = cMaxEnergy / 2;
	private iIdvmMoveCalculation mMoveCalculation;
	private IdvmSensor mIdvmSensor;
	private Direction mCurrentDirection;

	public Idvm(Genome pGenome) {
		super(BlockType.IDVM);
		mPos = new Pos(0, 0);
		mGenomeOrigin = pGenome;
		mCellGrow = pGenome.cellGrow;
		mHunger = pGenome.getHunger();
		
		grow();
		grow();
		grow();
		grow();

		for (IdvmState iState : pGenome.movementSequences.keySet()) {
			addaptMovementSequence(iState, pGenome);
		}
	}

	@SuppressWarnings("unchecked")
	private void addaptMovementSequence(IdvmState pState, Genome pGenome) {
		ArrayList<MoveProbability> lMoveProbability = (ArrayList<MoveProbability>) pGenome.movementSequences.get(pState)
				.clone();
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

	private void eat(Food pFood) {
		mEnergy = cMaxEnergy;
		grow();
		for (Entry<IdvmState, ArrayList<MoveProbability>> iSequence : mMovementSequences.entrySet()) {
			try {
				iSequence.getValue().remove(0);
			} catch (RuntimeException e) {
				// Empty Sequence
			}
		}
	}

	@SuppressWarnings("unused")
	private void move() {
		for (iBlock iCount : getUsedBlocks(BlockType.MOVE)) {
			for (int i = 0; i < 10; i++) {
				try {
					Pos lNewPos = mMoveCalculation.getMovingPosition(this, mMovementSequences);
					mCurrentDirection = mPos.getDircetionTo(lNewPos);
					setPosition(lNewPos);
					// mEnergy--;
					break;
				} catch (ExOutOfGrid e) {
				}
			}
		}
	}

	public void interactWithFood(Food pFood) {
		eat(pFood);
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

	// TODO REF Class Cell Grid
	public void detectCollisions() {
		ArrayList<Pos> lIdvmPos = new ArrayList<Pos>();
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
			} catch (ExOutOfGrid e) {
			}
		}
	}

	public Direction getTargetDirection() {
		return mMoveCalculation.getTargetDirection(getState(), getDetectedPos());
	}

	public int getEnergyCount() {
		return mEnergy;
	}

	public Direction getCalculatedDirection() {
		return mMoveCalculation.getCalculatedDirection();
	}
}
