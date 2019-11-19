package soup.block;

import soup.idvm.iIdvm;
import datatypes.Pos;
import globals.exceptions.ExOutOfGrid;

public interface iBlockGrid {

	iBlock getBlock(Pos pos) throws ExOutOfGrid;

	void setBlock(Pos pos, iBlock block) throws ExOutOfGrid;

	void setRandomBlock(iBlock foodBlock);

	void addInitialIdvm(iIdvm pIdvm);

	void clearBlocks();

}
