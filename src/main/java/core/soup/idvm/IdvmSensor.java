package core.soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import core.soup.block.BlockType;
import core.soup.block.iBlock;
import core.soup.block.iBlockGrid;

//TODO 3 Test Class
public class IdvmSensor {

	private iBlockGrid mBlockGrid;

	public IdvmSensor(iBlockGrid pBlockGrid) {
		super();
		mBlockGrid = pBlockGrid;
	}

	// TODO REF 6 move to Idvm
	// TODO 2 IMPL dynamic target order
	// TODO 4 IMPL sensor range
	// TODO 6 IMPL idle and blind hunger
	public IdvmState getState(HashMap<Pos, Sensor> pDetectedPos, boolean pIsHungry) {
		if (pDetectedPos.size() == 0)
			return IdvmState.BLIND;
		if (detectSurroundingBlockType(BlockType.ENEMY, pDetectedPos))
			if (pIsHungry) {
				return IdvmState.ENEMY_HUNGER;
			} else {
				return IdvmState.ENEMY;
			}
		if (detectSurroundingBlockType(BlockType.FOOD, pDetectedPos))
			if (pIsHungry) {
				return IdvmState.FOOD_HUNGER;
			} else {
				return IdvmState.FOOD;
			}
		return IdvmState.IDLE;

	}

	private boolean detectSurroundingBlockType(BlockType pBlockType, HashMap<Pos, Sensor> pDetectedPos) {
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
