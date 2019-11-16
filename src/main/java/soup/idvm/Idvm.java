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
import exceptions.ExOutOfGrid;

public class Idvm extends Block implements iIdvm {

	public static final int cMaxEnergy = 240;
	@SuppressWarnings("unused")
	private Genome mGenomeOrigin;
	// private Pos mMidPosition = new Pos(0, 0);
	private IdvmCell[][] mCellGrid = new IdvmCell[4][4];
	private HashMap<IdvmState, MovementSequence> mMovementSequences = new HashMap<IdvmState, MovementSequence>();
	private ArrayList<IdvmCell> mCellGrow;
	private int mHunger;
	private iBlockGrid mBlockGrid;
	private int mStepCount;
	private int mEnergy = cMaxEnergy;
	private iIdvmMoveCalculation mMoveCalculation;

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
		super.setPosition(pPos);
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
			Pos lNewPos = new Pos(mPos.x - 1 + pCellX, mPos.y - 1 + pCellY);
			lCell.setPosition(lNewPos);
		}
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
		mCellGrid[pPos.x - mPos.x + 1][pPos.y - mPos.y + 1] = null;
	}

	public void step() {
		mStepCount++;
		mEnergy--;
		move();
	}

	private void eat(Food pFood) {
		mEnergy = cMaxEnergy;
		grow();
		for (Entry<IdvmState, MovementSequence> iSequence : mMovementSequences
				.entrySet()) {
			iSequence.getValue().pop();
		}
	}

	private void move() {
		Direction lTargetDirection = null;
		for (int i = 0; i < 10; i++) {
			try {
				Pos lNewPos;
				lNewPos = mMoveCalculation.getMovingPosition(this,
						mMovementSequences, lTargetDirection);
				setPosition(lNewPos);
				mEnergy--;
				break;
			} catch (ExOutOfGrid e) {
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

	public int getEnergyCount() {
		return mEnergy;
	}
}
