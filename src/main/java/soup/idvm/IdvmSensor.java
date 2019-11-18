package soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import soup.block.BlockType;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import datatypes.Pos;
import exceptions.ExOutOfGrid;

//TODO 3 Test Class
public class IdvmSensor {

	private iBlockGrid mBlockGrid;

	public IdvmSensor(iBlockGrid pBlockGrid) {
		super();
		mBlockGrid = pBlockGrid;
	}

	public IdvmState getState(HashMap<Pos, Sensor> pDetectedPos) {
		IdvmState lState = IdvmState.IDLE;
		if (detectSurroundingBlockType(BlockType.ENEMY, pDetectedPos))
			lState = IdvmState.ENEMY;
		if (detectSurroundingBlockType(BlockType.FOOD, pDetectedPos))
			lState = IdvmState.FOOD;
		// TODO hungry
		// if (isHungry()) {
		// switch (lState) {
		// case FOOD:
		// lState = IdvmState.FOOD_HUNGER;
		// break;
		// case ENEMY:
		// lState = IdvmState.ENEMY_HUNGER;
		// break;
		//
		// default:
		// break;
		// }
		// }
		return lState;

	}

	private boolean detectSurroundingBlockType(BlockType pBlockType,
			HashMap<Pos, Sensor> pDetectedPos) {
		for (Entry<Pos, Sensor> iPos : pDetectedPos.entrySet()) {
			Pos lPos = iPos.getKey();
			try {
				lPos.isInGrid();
				iBlock lGridBlock = mBlockGrid.getBlock(lPos);
				if (lGridBlock != null
						&& lGridBlock.getBlockType() == pBlockType)
					return true;
			} catch (ExOutOfGrid e) {
			}
		}
		return false;
	}

	// TODO 2 IMPL detect range depends on sensors
	public HashMap<Pos, Sensor> getDetectedPos(ArrayList<iBlock> pSensors) {
		HashMap<Pos, Sensor> lDetectedPos = new HashMap<Pos, Sensor>();
		for (iBlock iCell : pSensors) {
			for (int x = -4; x <= 4; x++) {
				for (int y = -4; y <= 4; y++) {
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
