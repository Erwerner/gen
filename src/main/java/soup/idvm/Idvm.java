package soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import datatypes.Direction;
import datatypes.Pos;
import exceptions.ExFailedDetection;
import exceptions.ExOutOfGrid;
import exceptions.ExWrongDirection;
import exceptions.ExWrongState;
import genes.Genome;
import genes.MoveProbability;
import genes.MovementSequence;
import soup.block.BlockType;
import soup.block.Enemy;
import soup.block.Food;
import soup.block.iBlock;
import soup.block.iBlockGrid;

public class Idvm implements iIdvm {

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

	public Idvm(Genome pGenome) {
		mGenomeOrigin = pGenome;
		mCellGrow = pGenome.cellGrow;
		mHunger = pGenome.hunger;
		grow();
		grow();
		grow();
		grow();

		for (IdvmState iState : pGenome.movementSequences.keySet()) {
			addaptMovementSequence(iState, pGenome);
		}
	}

	private void addaptMovementSequence(IdvmState pState, Genome pGenome) {
		// TODO more dynamic
		ArrayList<MoveProbability> lMoveProbability = (ArrayList<MoveProbability>) pGenome.movementSequences.get(pState)
				.clone();
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

	public BlockType getBlockType() {
		return BlockType.IDVM;
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
			Pos lNewPos = new Pos(mMidPosition.x - 1 + pCellX, mMidPosition.y - 1 + pCellY);
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
		Direction lTargetDirection = null;
		IdvmState lState = getState();
		if (lState != IdvmState.IDLE)
			lTargetDirection = getTargetDirection();
		MovementSequence lSequence = mMovementSequences.get(lState);
		Direction lDirection = lSequence.getDirection();
		move(lDirection, lTargetDirection);
	}

	public Direction getTargetDirection() {
		IdvmState lState = getState();
		BlockType lSearchBlock;
		HashMap<Pos, Sensor> lDetectedPos = getDetectedPos();
		if (lDetectedPos == null)
			throw new ExFailedDetection();

		switch (lState) {
		case FOOD:
			lSearchBlock = BlockType.FOOD;
			break;
		default:
			throw new ExWrongState();
		}

		for (Entry<Pos, Sensor> iPos : lDetectedPos.entrySet()) {
			iBlock lGridBlock = mBlockGrid.getBlock(iPos.getKey());
			if (lGridBlock != null && lGridBlock.getBlockType() == lSearchBlock) {
				Direction lDircetion;
				Pos lSensorPos = iPos.getValue().getPos();
				lDircetion = lSensorPos.getDircetionTo(iPos.getKey());
				return lDircetion;
			}
		}
		throw new ExWrongDirection();
	}

	private void eat(Food pFood) {
		mEnergy = 100;
		grow();
		for (Entry<IdvmState, MovementSequence> iSequence : mMovementSequences.entrySet()) {
			iSequence.getValue().pop();
		}
	}

	private void move(Direction pDirection, Direction pTarget) {
		switch (pDirection) {
		case UP:
		case DOWN:
		case LEFT:
		case RIGHT:
		case NOTHING:
			break;
		default:
			throw new ExWrongDirection();
		}

		Pos lNewPos = mMidPosition.getPosFromDirection(pDirection);
		setPosition(lNewPos);
		mEnergy--;
	}

	public iBlock interactWithFood(Food pFood) {
		eat(pFood);
		return null;
	}

	public iBlock interactWithEnemy(Enemy pEnemy) {
		killCell(pEnemy.getPos());
		return pEnemy;
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
				if (lGridBlock != null && lGridBlock.getBlockType() == BlockType.FOOD)
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
								(Sensor) new Sensor().setPosition(iCell.getPos()));
					}
				}
			}
		}
		return lDetectedPos;
	}

	public void setBlockGrid(iBlockGrid pBlockGrid) {
		mBlockGrid = pBlockGrid;
	}

	public int getStepCount() {
		return mStepCount;
	}

	public void detectCollisions() {
		iBlock lInteractedBlock = null;
		ArrayList<Pos> lIdvmPos = new ArrayList<Pos>();
		for (iBlock iBlock : getUsedBlocks()) {
			lIdvmPos.add(iBlock.getPos());
		}
		for (Pos iPos : lIdvmPos) {
			// TODO Border Check
			iBlock lGridBlock = mBlockGrid.getBlock(iPos);
			if (lGridBlock != null) {
				switch (lGridBlock.getBlockType()) {
				case FOOD:
					lInteractedBlock = interactWithFood((Food) lGridBlock);
					mBlockGrid.setBlock(iPos, lInteractedBlock);
					break;
				case ENEMY:
					lInteractedBlock = interactWithEnemy((Enemy) lGridBlock);
					mBlockGrid.setBlock(iPos, lInteractedBlock);
					break;
				default:
					break;
				}
			}
		}

	}

}
