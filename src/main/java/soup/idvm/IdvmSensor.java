package soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import soup.block.BlockType;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import datatypes.Pos;
import globals.exceptions.ExOutOfGrid;

//TODO 3 Test Class
public class IdvmSensor {

	private iBlockGrid mBlockGrid;

	public IdvmSensor(iBlockGrid pBlockGrid) {
		super();
		mBlockGrid = pBlockGrid;
	}

	public IdvmState getState(HashMap<Pos, Sensor> pDetectedPos) {
		if (detectSurroundingBlockType(BlockType.ENEMY, pDetectedPos))
			return IdvmState.ENEMY;
		if (detectSurroundingBlockType(BlockType.FOOD, pDetectedPos))
			return IdvmState.FOOD;
		return IdvmState.IDLE;

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

	// TODO 3 IMPL detect range depends on sensors
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
