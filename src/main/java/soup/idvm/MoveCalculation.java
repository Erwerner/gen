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

		// TODO FIX not working; write test
		for (iBlock iBlock : pIdvmBlocks) {
			iBlock.getPos().getPosFromDirection(lDirection).isInGrid();
		}

		Pos lNewPos = pIdvmPos.getPosFromDirection(lDirection);
		return lNewPos;
	}

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
			HashMap<IdvmState, MovementSequence> pMovementSequences,
			Direction pTargetDirection) throws ExOutOfGrid {
		Direction lTargetDirection;
		IdvmState lState = pIdvm.getState();
		if (lState != IdvmState.IDLE)
			lTargetDirection = getTargetDirection(lState,
					pIdvm.getDetectedPos());
		MovementSequence lSequence = pMovementSequences.get(lState);
		Direction lDirection = lSequence.getDirection();

		Pos lNewPos = calcPosFromDirection(lDirection, pIdvm.getPos(),
				pIdvm.getUsedBlocks());
		return lNewPos;
	}

}