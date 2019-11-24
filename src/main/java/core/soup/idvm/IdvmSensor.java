package core.soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import core.datatypes.Direction;
import core.datatypes.Pos;
import core.exceptions.DetectionFailed;
import core.exceptions.PosIsOutOfGrid;
import core.exceptions.WrongBlockType;
import core.exceptions.WrongState;
import core.soup.block.BlockGrid;
import core.soup.block.BlockType;
import core.soup.block.iBlock;

//TODO 3 Test Class
public class IdvmSensor {

	private BlockGrid mBlockGrid;

	public IdvmSensor(BlockGrid pBlockGrid) {
		super();
		mBlockGrid = pBlockGrid;
	}

	// TODO add hunger
	public Direction getTargetDirection(IdvmState pState, ArrayList<iBlock> pSensors) {
		BlockType lSearchBlock;
		HashMap<Pos, Sensor> lDetectedPos = getDetectedPos(pSensors);
		if (lDetectedPos == null)
			throw new DetectionFailed();

		switch (pState) {
		case FOOD:
			// case FOOD_HUNGER:
			lSearchBlock = BlockType.FOOD;
			break;
		case ENEMY:
			// case ENEMY_HUNGER:
			lSearchBlock = BlockType.ENEMY;
			break;
		default:
			throw new WrongState();
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
			for (int x = -3; x <= 3; x++) {
				for (int y = -3; y <= 3; y++) {
					int lCellX = iCell.getPos().x;
					int lCellY = iCell.getPos().y;
					lDetectedPos.put(new Pos(lCellX + x, lCellY + y),
							(Sensor) new Sensor().setPosition(iCell.getPos()));
				}
			}
		}
		return lDetectedPos;
	}
}
