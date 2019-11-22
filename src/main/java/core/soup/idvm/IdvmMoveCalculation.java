package core.soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.exceptions.DetectionFailed;
import core.exceptions.PosIsOutOfGrid;
import core.exceptions.WrongBlockType;
import core.exceptions.WrongDecision;
import core.exceptions.WrongState;
import core.genes.MoveDecisionsProbability;
import core.soup.block.BlockType;
import core.soup.block.iBlock;
import core.soup.block.iBlockGrid;

public class IdvmMoveCalculation implements iIdvmMoveCalculation {

	private iBlockGrid mBlockGrid;
	private Decisions mCurrentDirection = Decisions.UP;
	private Decisions mCalculatedDirection = Decisions.UP;

	public IdvmMoveCalculation(iBlockGrid pBlockGrid) {
		super();
		mBlockGrid = pBlockGrid;
	}

	public Pos calcPosFromDirection(Decisions pDirection, Pos pIdvmPos, ArrayList<iBlock> pIdvmBlocks)
			throws PosIsOutOfGrid {
		Decisions lDirection = pDirection;
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
			throw new WrongDecision(pDirection);
		}

		for (iBlock iBlock : pIdvmBlocks) {
			Pos lPosFromDirection = iBlock.getPos().getPosFromDirection(lDirection);
			lPosFromDirection.isInGrid();
		}

		Pos lNewPos = pIdvmPos.getPosFromDirection(lDirection);
		return lNewPos;
	}

	// TODO REF Sensor Class
	public Decisions getTargetDirection(IdvmState pState, HashMap<Pos, Sensor> pDetectedPos) {
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
					Decisions lDircetion = lSensorPos.getDircetionTo(iPos.getKey());
					return lDircetion;
				}
			} catch (PosIsOutOfGrid e) {
			}
		}
		throw new WrongBlockType();
	}

	public Pos getMovingPosition(iIdvm pIdvm,
			HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> pMovementSequences) throws PosIsOutOfGrid {
		Decisions lTargetDirection = mCurrentDirection;
		IdvmState lState = pIdvm.getState();
		if (lState != IdvmState.IDLE) {
			lTargetDirection = getTargetDirection(lState, pIdvm.getDetectedPos());
		}
		Decisions lDirection = calcMovingDirection(pMovementSequences, lState, lTargetDirection);

		Pos lNewPos = calcPosFromDirection(lDirection, pIdvm.getPos(), pIdvm.getUsedBlocks());
		return lNewPos;
	}

	public Decisions calcMovingDirection(HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> pMovementSequences,
			IdvmState lState, Decisions pTargetDirection) {
		ArrayList<MoveDecisionsProbability> lSequence = pMovementSequences.get(lState);
		try {
			mCalculatedDirection = lSequence.get(0).getDecision();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		Decisions lNewDirection = null;
		switch (mCalculatedDirection) {
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
			lNewDirection = mCalculatedDirection;
			break;
		}
		setCurrentDirection(lNewDirection);
		return lNewDirection;
	}

	private void setCurrentDirection(Decisions pDirection) {
		switch (pDirection) {
		case UP:
		case DOWN:
		case RIGHT:
		case LEFT:
		case NOTHING:
			break;

		default:
			throw new WrongDecision(pDirection);
		}
		mCurrentDirection = pDirection;
	}

	public Decisions getCalculatedDirection() {
		return mCalculatedDirection;
	}

}
