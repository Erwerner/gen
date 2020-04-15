package core.soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import core.datatypes.Direction;
import core.datatypes.Pos;
import core.exceptions.DetectionFailed;
import core.exceptions.PosIsOutOfGrid;
import core.exceptions.WrongBlockType;
import core.soup.block.BlockGrid;
import core.soup.block.BlockType;
import core.soup.block.iBlock;
import globals.Config;

public class IdvmSensor {

	private BlockGrid mBlockGrid;

	public IdvmSensor(BlockGrid pBlockGrid) {
		super();
		mBlockGrid = pBlockGrid;
	}

	public Direction getTargetDirection(IdvmState pState, ArrayList<iBlock> pSensors) {
		BlockType lSearchBlock;
		HashMap<Pos, Sensor> lDetectedPos = getDetectedPos(pSensors);
		if (lDetectedPos == null)
			throw new DetectionFailed();
//TODO 1 Hunger
		switch (pState) {
		case FOOD:
		case FOOD_HUNGER:
			lSearchBlock = BlockType.FOOD;
			break;
		case ENEMY:
		case ENEMY_HUNGER:
			lSearchBlock = BlockType.ENEMY;
			break;
		case PARTNER:
		case PARTNER_HUNGER:
			lSearchBlock = BlockType.PARTNER;
			break;
		default:
			return null;
		}

		for (Entry<Pos, Sensor> iPos : lDetectedPos.entrySet()) {
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

	public boolean detectSurroundingBlockType(BlockType pBlockType, HashMap<Pos, Sensor> pDetectedPos) {
		for (Entry<Pos, Sensor> iPos : pDetectedPos.entrySet()) {
			Pos lPos = iPos.getKey();
			try {
				lPos.isInGrid();
				iBlock lGridBlock = mBlockGrid.getBlock(lPos);
				if (lGridBlock != null && lGridBlock.getBlockType() == pBlockType)
					return true;
			} catch (PosIsOutOfGrid e) {
			}
		}
		return false;
	}

	public HashMap<Pos, Sensor> getDetectedPos(ArrayList<iBlock> pSensors) {
		HashMap<Pos, Sensor> lDetectedPos = new HashMap<Pos, Sensor>();
		for (iBlock iCell : pSensors) {
			for (int x = Config.cSensorRange * -1; x <= Config.cSensorRange; x++) {
				for (int y = Config.cSensorRange * -1; y <= Config.cSensorRange; y++) {
					int lCellX = iCell.getPos().x;
					int lCellY = iCell.getPos().y;
					lDetectedPos.put(new Pos(lCellX + x, lCellY + y),
							(Sensor) new Sensor().setPosition(iCell.getPos()));
				}
			}
		}
		return lDetectedPos;
	}

	// TODO 5 IMPL sensor range
	// TODO 7 REF move To Idvm
	public IdvmState getState(Boolean pHasSensor, Boolean pIsHungry, HashMap<Pos, Sensor> pDetectedPos,
			ArrayList<BlockType> pTargetDetectionOrder) {
		IdvmState lState = IdvmState.IDLE;
		if (!pHasSensor)
			return IdvmState.BLIND;
		for (BlockType iBlockType : pTargetDetectionOrder) {
			if (detectSurroundingBlockType(iBlockType, pDetectedPos))
				switch (iBlockType) {
				case PARTNER:
					lState = IdvmState.PARTNER;
					break;
				case ENEMY:
					lState = IdvmState.ENEMY;
					break;
				case FOOD:
					lState = IdvmState.FOOD;
					break;
				default:
					throw new RuntimeException();
				}
			if (lState != IdvmState.IDLE) {
				break;
			}
		}
		if (pIsHungry) {
			//TODO 1 Hunger
			lState = lState.getStateAsHunger();
		}
		return lState;
	}
}
