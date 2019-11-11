package mvc.present;

import soup.block.iBlock;
import datatypes.Pos;

public interface iPresentSoup {
	Pos getIdvmPosition();
	iBlock getBlock(Pos pos);
	boolean isIdvmAlive();
	int getStepCount();
	Boolean isHungry();
}
