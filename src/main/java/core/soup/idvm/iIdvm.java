package core.soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;

import ui.presenter.iPresentDevIdvmStats;
import ui.presenter.iPresentIdvm;
import core.datatypes.Pos;
import core.soup.block.BlockGrid;
import core.soup.block.iBlock;

// TOTO REF delete interface
public interface iIdvm extends iLiving, iBlock, iPresentIdvm,
		iPresentDevIdvmStats {

	ArrayList<iBlock> getUsedBlocks();

	HashMap<Pos, Sensor> getDetectedPos();

	IdvmState getState();

	void setBlockGrid(BlockGrid pBlockGrid);

	boolean isAlive();

	Boolean isHungry();
}
