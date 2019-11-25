package core.soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;

import core.datatypes.Decisions;
import core.datatypes.Direction;
import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import core.genes.MoveDecisionsProbability;
import core.soup.block.BlockGrid;
import core.soup.block.BlockType;
import core.soup.block.iBlock;

//TODO 2 Test Class
public class IdvmMoveCalculation {

	private Direction mCurrentDirection = Direction.NORTH;
	private Decisions mCalculatedDecision = Decisions.UP;

	public IdvmMoveCalculation(BlockGrid pBlockGrid) {
		super();
	}

	public Pos calcPosFromDirection(Direction pDirection, Pos pIdvmPos, ArrayList<iBlock> pIdvmBlocks)
			throws PosIsOutOfGrid {

		for (iBlock iBlock : pIdvmBlocks) {
			Pos lPosFromDirection = iBlock.getPos().getPosFromDirection(pDirection);
			lPosFromDirection.isInGrid();
		}

		Pos lNewPos = pIdvmPos.getPosFromDirection(pDirection);
		return lNewPos;
	}

//TODO add blind
	public Pos getMovingPosition(iIdvm pIdvm,
			HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> pMovementSequences, IdvmSensor pIdvmSensor)
			throws PosIsOutOfGrid {
		Direction lTargetDirection = mCurrentDirection;
		IdvmState lState = pIdvm.getState();
		if (lState != IdvmState.IDLE) { // && lState!= IdvmState.BLIND) {
			lTargetDirection = pIdvmSensor.getTargetDirection(lState, pIdvm.getUsedBlocks(BlockType.SENSOR));
		}
		Direction lDirection = calcMovingDirection(pMovementSequences, lState, lTargetDirection);

		Pos lNewPos = calcPosFromDirection(lDirection, pIdvm.getPos(), pIdvm.getUsedBlocks());
		return lNewPos;
	}

	public Direction calcMovingDirection(HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> pMovementSequences,
			IdvmState lState, Direction pTargetDirection) {
		ArrayList<MoveDecisionsProbability> lSequence = pMovementSequences.get(lState);
		try {
			mCalculatedDecision = lSequence.get(0).getDecision();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		Direction lNewDirection = null;
		switch (mCalculatedDecision) {
		case CURRENT:
			return mCurrentDirection;
		case CURRENT_OPPOSITE:
			lNewDirection = mCurrentDirection.opposite();
			break;
		case CURRENT_SITE1:
			lNewDirection = mCurrentDirection.side1();
			break;
		case CURRENT_SITE2:
			lNewDirection = mCurrentDirection.side2();
			break;
		case TARGET:
			lNewDirection = pTargetDirection;
			break;
		case TARGET_OPPOSITE:
			lNewDirection = pTargetDirection.opposite();
			break;
		case TARGET_SITE1:
			lNewDirection = pTargetDirection.side2();
			break;
		case TARGET_SITE2:
			lNewDirection = pTargetDirection.side2();
		case DOWN:
			lNewDirection = Direction.SOUTH;
			break;
		case LEFT:
			lNewDirection = Direction.WEST;
			break;
		case NOTHING:
			lNewDirection = Direction.NOTHING;
			break;
		case RIGHT:
			lNewDirection = Direction.EAST;
			break;
		case UP:
			lNewDirection = Direction.NORTH;
			break;
		}
		mCurrentDirection = lNewDirection;
		return lNewDirection;
	}

	public Decisions getCalculatedDirection() {
		return mCalculatedDecision;
	}

}
