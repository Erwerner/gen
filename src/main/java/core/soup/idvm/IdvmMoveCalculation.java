package core.soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import core.datatypes.Decisions;
import core.datatypes.Direction;
import core.datatypes.Pos;
import core.exceptions.DetectionFailed;
import core.exceptions.PosIsOutOfGrid;
import core.exceptions.WrongBlockType;
import core.exceptions.WrongState;
import core.genes.MoveDecisionsProbability;
import core.soup.block.BlockType;
import core.soup.block.iBlock;
import core.soup.block.iBlockGrid;

public class IdvmMoveCalculation {

	private iBlockGrid mBlockGrid;
	private Direction mCurrentDirection = Direction.NORTH;
	private Decisions mCalculatedDecision = Decisions.UP;

	public IdvmMoveCalculation(iBlockGrid pBlockGrid) {
		super();
		mBlockGrid = pBlockGrid;
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

	// TODO REF Sensor Class
	public Direction getTargetDirection(IdvmState pState, HashMap<Pos, Sensor> pDetectedPos) {
		BlockType lSearchBlock;
		if (pDetectedPos == null)
			throw new DetectionFailed();

		switch (pState) {
		case FOOD:
		case FOOD_HUNGER:
			lSearchBlock = BlockType.FOOD;
			break;
		case ENEMY:
		case ENEMY_HUNGER:
			lSearchBlock = BlockType.ENEMY;
			break;
		default:
			throw new WrongState();
		}

		for (Entry<Pos, Sensor> iPos : pDetectedPos.entrySet()) {
			iBlock lGridBlock;
			try {
				lGridBlock = mBlockGrid.getBlock(iPos.getKey());
				if (lGridBlock != null && lGridBlock.getBlockType() == lSearchBlock) {
					Pos lSensorPos = iPos.getValue().getPos();
					Direction lDircetion = lSensorPos.getDircetionTo(iPos.getKey());
					return lDircetion;
				}
			} catch (PosIsOutOfGrid e) {
			}
		}
		throw new WrongBlockType();
	}

	public Pos getMovingPosition(iIdvm pIdvm,
			HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> pMovementSequences) throws PosIsOutOfGrid {
		Direction lTargetDirection = mCurrentDirection;
		IdvmState lState = pIdvm.getState();
		if (lState != IdvmState.IDLE && lState != IdvmState.BLIND) {
			lTargetDirection = getTargetDirection(lState, pIdvm.getDetectedPos());
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
