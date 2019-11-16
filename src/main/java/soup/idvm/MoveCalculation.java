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
import exceptions.ExWrongDirection;
import exceptions.ExWrongState;
import genes.MovementSequence;

public class MoveCalculation implements iIdvmMoveCalculation {

	private iBlockGrid mBlockGrid;
	private Direction mCurrentDirection = Direction.UP;

	public MoveCalculation(iBlockGrid pBlockGrid) {
		super();
		mBlockGrid = pBlockGrid;
	}

	public Pos calcPosFromDirection(Direction pDirection, Pos pIdvmPos,
			ArrayList<iBlock> pIdvmBlocks) throws ExOutOfGrid {
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

		Direction lDirection = pDirection;

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
		throw new ExWrongDirection();
	}

	public Pos getMovingPosition(iIdvm pIdvm,
			HashMap<IdvmState, MovementSequence> pMovementSequences)
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
			HashMap<IdvmState, MovementSequence> pMovementSequences,
			IdvmState lState, Direction pTargetDirection) {
		MovementSequence lSequence = pMovementSequences.get(lState);
		Direction lDirection = lSequence.getDirection();
		switch (lDirection) {
		case CURRENT:
			return mCurrentDirection;
		case CURRENT_OPPOSITE: 
			lDirection = mCurrentDirection.opposite(); 
			break;
		case TARGET:
			lDirection = pTargetDirection;
			break;
		case TARGET_OPPOSITE:
			lDirection = pTargetDirection.opposite();
			break;
		default:
			break;
		} 
		mCurrentDirection = lDirection;
		return lDirection;
	}

}
