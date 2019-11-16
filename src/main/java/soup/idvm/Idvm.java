package soup.idvm;

import genes.Genome;
import genes.MoveProbability;
import genes.MovementSequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import soup.block.Block;
import soup.block.BlockType;
import soup.block.Enemy;
import soup.block.Food;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import datatypes.Direction;
import datatypes.Pos;
import exceptions.ExFailedDetection;
import exceptions.ExOutOfGrid;
import exceptions.ExWrongDirection;
import exceptions.ExWrongState;

public class Idvm extends Block implements iIdvm {

	@SuppressWarnings("unused")
	private Genome mGenomeOrigin;
	private Pos mMidPosition = new Pos(0, 0);
	private IdvmCell[][] mCellGrid = new IdvmCell[4][4];
	private HashMap<IdvmState, MovementSequence> mMovementSequences = new HashMap<IdvmState, MovementSequence>();
	private ArrayList<IdvmCell> mCellGrow;
	private int mHunger;
	private iBlockGrid mBlockGrid;
	private int mStepCount;
	private int mEnergy = 100;
	private iIdvmMoveCalculation mMoveCalculation;

	public Idvm(Genome pGenome) {
		super(BlockType.IDVM);
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

	private void addaptMovementSequence(IdvmState pState, Genome pGenome) {
		ArrayList<MoveProbability> lMoveProbability = (ArrayList<MoveProbability>) pGenome.movementSequences
				.get(pState).clone();
		mMovementSequences.put(pState, new MovementSequence(lMoveProbability));
	}

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
		mMidPosition = pPos;
		for (int x = 0; x <= 3; x++) {
			for (int y = 0; y <= 3; y++) {
				refreshCellPos(x, y);
			}
		}
		return this;
	}

	private void refreshCellPos(int pCellX, int pCellY) {
		IdvmCell lCell = mCellGrid[pCellX][pCellY];
		if (lCell != null) {
			Pos lNewPos = new Pos(mMidPosition.x - 1 + pCellX, mMidPosition.y
					- 1 + pCellY);
			lCell.setPosition(lNewPos);
		}
	}

	public Pos getPos() {
		return mMidPosition;
	}

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

	public void killCell(Pos pPos) {
		mCellGrid[pPos.x - mMidPosition.x + 1][pPos.y - mMidPosition.y + 1] = null;
	}

	public void step() {
		mStepCount++;
		try {
			move();
		} catch (ExOutOfGrid e) {
		}
	}

	private void eat(Food pFood) {
		mEnergy = 100;
		grow();
		for (Entry<IdvmState, MovementSequence> iSequence : mMovementSequences
				.entrySet()) {
			iSequence.getValue().pop();
		}
	}

	private void move() throws ExOutOfGrid {
		Direction lTargetDirection;
		IdvmState lState = getState();
		if (lState != IdvmState.IDLE)
			lTargetDirection = mMoveCalculation.getTargetDirection(lState,
					getDetectedPos());
		MovementSequence lSequence = mMovementSequences.get(lState);
		Direction lDirection = lSequence.getDirection();

		Pos lNewPos = mMoveCalculation.calcMovingPosition(lDirection,
				mMidPosition, getUsedBlocks());
		setPosition(lNewPos);
		mEnergy--;
	}

	public void interactWithFood(Food pFood) {
		eat(pFood);
	}

	public void interactWithEnemy(Enemy pEnemy) {
		killCell(pEnemy.getPos());
	}

	public IdvmState getState() {
		IdvmState lState = IdvmState.IDLE;
		if (detectFood())
			lState = IdvmState.FOOD;
		return lState;
	}

	private boolean detectFood() {
		for (Entry<Pos, Sensor> iPos : getDetectedPos().entrySet()) {
			Pos lPos = iPos.getKey();
			try {
				lPos.isInGrid();
				iBlock lGridBlock = mBlockGrid.getBlock(lPos);
				if (lGridBlock != null
						&& lGridBlock.getBlockType() == BlockType.FOOD)
					return true;
			} catch (ExOutOfGrid e) {
			}
		}
		return false;
	}

	public HashMap<Pos, Sensor> getDetectedPos() {
		HashMap<Pos, Sensor> lDetectedPos = new HashMap<Pos, Sensor>();
		for (iBlock iCell : getUsedBlocks()) {
			if (iCell.getBlockType() == BlockType.SENSOR) {
				for (int x = -1; x <= 1; x++) {
					for (int y = -1; y <= 1; y++) {
						int lCellX = iCell.getPos().x;
						int lCellY = iCell.getPos().y;
						lDetectedPos.put(new Pos(lCellX + x, lCellY + y),
								(Sensor) new Sensor().setPosition(iCell
										.getPos()));
					}
				}
			}
		}
		return lDetectedPos;
	}

	public void setBlockGrid(iBlockGrid pBlockGrid) {
		mBlockGrid = pBlockGrid;
		mMoveCalculation = new MoveCalculation(mBlockGrid);
	}

	public int getStepCount() {
		return mStepCount;
	}

	public void detectCollisions() {
		ArrayList<Pos> lIdvmPos = new ArrayList<Pos>();
		for (iBlock iBlock : getUsedBlocks()) {
			lIdvmPos.add(iBlock.getPos());
		}
		for (Pos iPos : lIdvmPos) {
			iBlock lGridBlock;
			try {
				lGridBlock = mBlockGrid.getBlock(iPos);
				if (lGridBlock != null) {
					switch (lGridBlock.getBlockType()) {
					case FOOD:
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
		return mMoveCalculation
				.getTargetDirection(getState(), getDetectedPos());
	}
}
