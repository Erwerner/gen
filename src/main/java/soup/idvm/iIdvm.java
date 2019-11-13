package soup.idvm;

import java.util.ArrayList;
import java.util.HashMap;

import mvc.present.iPresentIdvm;

import datatypes.Direction;
import datatypes.Pos;

import soup.block.Enemy;
import soup.block.Food;
import soup.block.iBlock;
import soup.block.iBlockGrid;

public interface iIdvm extends iBlock {

	ArrayList<iBlock> getUsedBlocks();

	iBlock interactWithFood(Food pFood);

	iBlock interactWithEnemy(Enemy pEnemy);

	void killCell(Pos pPos);

	HashMap<Pos, Sensor> getDetectedPos();

	IdvmState getState();

	Direction getTargetDirection();

	void setBlockGrid(iBlockGrid pBlockGrid);

	boolean isAlive();

	Boolean isHungry();
}
