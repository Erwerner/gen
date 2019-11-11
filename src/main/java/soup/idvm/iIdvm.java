package soup.idvm;

import java.util.ArrayList;

import soup.block.iBlock;

public interface iIdvm extends iBlock {

	boolean isAlive();
	
	ArrayList<iBlock> getUsedBlocks();

	Boolean isHungry();

}
