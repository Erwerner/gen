package soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import soup.block.BlockType;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import datatypes.Direction;
import datatypes.Pos;
import exceptions.ExFailedDetection;
import exceptions.ExOutOfGrid;
import exceptions.ExWrongBlockType;
import exceptions.ExWrongDirection;
import exceptions.ExWrongState;
import genes.MoveProbability;

public class MoveCalculation implements iIdvmMoveCalculation {

	private iBlockGrid mBlockGrid;
	private Direction mCurrentDirection = Direction.UP;

	public MoveCalculation(iBlockGrid pBlockGrid) {
		super();
		mBlockGrid = pBlockGrid;
	}

	public Pos calcPosFromDirection(Direction pDirection, Pos pIdvmPos,
			ArrayList<iBlock> pIdvmBlocks) throws ExOutOfGrid {
		Direction lDirection = pDirection;
		switch (pDirection) {
		case UP:
		case DOWN:
		case LEFT:
		case RIGHT:
		case NOTHING:
			break;
		case CURRENT:
			lDirection = mCurrentDirection;
			break;
		default:
			throw new ExWrongDirection(pDirection);
		}

		for (iBlock iBlock : pIdvmBlocks) {
			Pos lPosFromDirection = iBlock.getPos().getPosFromDirection(
					lDirection);
			lPosFromDirection.isInGrid();
		}

		Pos lNewPos = pIdvmPos.getPosFromDirection(lDirection);
		return lNewPos;
	}

	// TODO REF Sensor Class
	public Direction getTargetDirection(IdvmState pState,
			HashMap<Pos, Sensor> pDetectedPos) {
		BlockType lSearchBlock;
		if (pDetectedPos == null)
			throw new ExFailedDetection();

		switch (pState) {
		case FOOD:
			lSearchBlock = BlockType.FOOD;
			break;
		case ENEMY:
			lSearchBlock = BlockType.ENEMY;
			break;
		default:
			throw new ExWrongState();
		}

		for (Entry<Pos, Sensor> iPos : pDetectedPos.entrySet()) {
			iBlock lGridBlock;
			try {
				lGridBlock = mBlockGrid.getBlock(iPos.getKey());
				if (lGridBlock != null
						&& lGridBlock.getBlockType() == lSearchBlock) {
					Direction lDircetion;
					Pos lSensorPos = iPos.getValue().getPos();
					lDircetion = lSensorPos.getDircetionTo(iPos.getKey());
					return lDircetion;
				}
			} catch (ExOutOfGrid e) {
			}
		}
		throw new ExWrongBlockType();
	}

	public Pos getMovingPosition(iIdvm pIdvm,
			HashMap<IdvmState, ArrayList<MoveProbability>> pMovementSequences)
			throws ExOutOfGrid {
		Direction lTargetDirection = mCurrentDirection;
		IdvmState lState = pIdvm.getState();
		if (lState != IdvmState.IDLE) {
			lTargetDirection = getTargetDirection(lState,
					pIdvm.getDetectedPos());
		}
		Direction lDirection = calcMovingDirection(pMovementSequences, lState,
				lTargetDirection);

		Pos lNewPos = calcPosFromDirection(lDirection, pIdvm.getPos(),
				pIdvm.getUsedBlocks());
		return lNewPos;
	}

	public Direction calcMovingDirection(
			HashMap<IdvmState, ArrayList<MoveProbability>> pMovementSequences,
			IdvmState lState, Direction pTargetDirection) {
		ArrayList<MoveProbability> lSequence = pMovementSequences.get(lState);
		Direction lSequenceDirection = lSequence.get(0).getDirection();
		Direction lNewDirection = null;
		switch (lSequenceDirection) {
		case CURRENT:
			return mCurrentDirection;
		case CURRENT_OPPOSITE:
			lNewDirection = mCurrentDirection.opposite();
			break;
		case CURRENT_SITE1:
			lNewDirection = mCurrentDirection.site1();
			break;
		case CURRENT_SITE2:
			lNewDirection = mCurrentDirection.site2();
			break;
		case TARGET:
			lNewDirection = pTargetDirection;
			break;
		case TARGET_OPPOSITE:
			lNewDirection = pTargetDirection.opposite();
			break;
		case TARGET_SITE1:
			lNewDirection = pTargetDirection.site2();
			break;
		case TARGET_SITE2:
			lNewDirection = pTargetDirection.site2();
			break;
		default:
			lNewDirection = lSequenceDirection;
			break;
		}
		setCurrentDirection(lNewDirection);
		return lNewDirection;
	}

	private void setCurrentDirection(Direction pDirection) {
		switch (pDirection) {
		case UP:
		case DOWN:
		case RIGHT:
		case LEFT:
		case NOTHING:
			break;

		default:
			throw new ExWrongDirection(pDirection);
		}
		mCurrentDirection = pDirection;
	}

}
