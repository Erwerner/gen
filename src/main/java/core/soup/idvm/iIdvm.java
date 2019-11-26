package core.soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;

import ui.presenter.iPresentDevIdvmStats;
import ui.presenter.iPresentIdvm;
import core.datatypes.Pos;
import core.soup.block.BlockGrid;
import core.soup.block.BlockType;
import core.soup.block.Enemy;
import core.soup.block.Food;
import core.soup.block.iBlock;

// TOTO REF delete interface
public interface iIdvm extends iLiving, iBlock, iPresentIdvm, iPresentDevIdvmStats {

	ArrayList<iBlock> getUsedBlocks();

	void interactWithFood(Food pFood);

	void interactWithEnemy(Enemy pEnemy);

	HashMap<Pos, Sensor> getDetectedPos();

	IdvmState getState();

	void setBlockGrid(BlockGrid pBlockGrid);

	boolean isAlive();

	Boolean isHungry();

	ArrayList<iBlock> getUsedBlocks(BlockType pBlockType);
}
