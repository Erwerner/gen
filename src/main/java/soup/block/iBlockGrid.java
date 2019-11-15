package soup.block;

import soup.idvm.iIdvm;
import datatypes.Pos;
import exceptions.ExOutOfGrid;

public interface iBlockGrid {

	iBlock getBlock(Pos pos) throws ExOutOfGrid;

	void setBlock(Pos pos, iBlock block);

	void setRandomBlock(iBlock foodBlock);

	void addInitialIdvm(iIdvm pIdvm);

	void clearBlocks();

}
