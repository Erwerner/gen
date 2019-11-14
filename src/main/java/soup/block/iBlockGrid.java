package soup.block;

import soup.idvm.iIdvm;
import datatypes.Pos;

public interface iBlockGrid {

	iBlock getBlock(Pos pos);

	void setBlock(Pos pos, iBlock block);

	void setRandomBlock(iBlock foodBlock);

	void addInitialIdvm(iIdvm pIdvm);

	void clearBlocks();

}
