package soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;

import mvc.present.iPresentIdvm;
import soup.block.Enemy;
import soup.block.Food;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import datatypes.Direction;
import datatypes.Pos;

public interface iIdvm extends iBlock, iPresentIdvm {

	ArrayList<iBlock> getUsedBlocks();

	void interactWithFood(Food pFood);

	void interactWithEnemy(Enemy pEnemy);

	void killCell(Pos pPos);

	HashMap<Pos, Sensor> getDetectedPos();

	IdvmState getState();

	//Direction getTargetDirection();

	void setBlockGrid(iBlockGrid pBlockGrid);

	boolean isAlive();

	Boolean isHungry();

	void detectCollisions();
}
