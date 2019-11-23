package core.soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;

import core.datatypes.Pos;
import core.soup.block.Enemy;
import core.soup.block.Food;
import core.soup.block.iBlock;
import core.soup.block.iBlockGrid;
import ui.presenter.iPresentIdvm;

// TOTO REF delete interface
public interface iIdvm extends iBlock, iPresentIdvm {

	ArrayList<iBlock> getUsedBlocks();

	void interactWithFood(Food pFood);

	void interactWithEnemy(Enemy pEnemy);

	HashMap<Pos, Sensor> getDetectedPos();

	IdvmState getState();

	// Direction getTargetDirection();

	void setBlockGrid(iBlockGrid pBlockGrid);

	boolean isAlive();

	Boolean isHungry();

	void detectCollisions();
}
