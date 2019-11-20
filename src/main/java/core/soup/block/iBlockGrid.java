package core.soup.block;

import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import core.soup.idvm.iIdvm;

public interface iBlockGrid {

	iBlock getBlock(Pos pos) throws PosIsOutOfGrid;

	void setBlock(Pos pos, iBlock block) throws PosIsOutOfGrid;

	void setRandomBlock(iBlock foodBlock);

	void addInitialIdvm(iIdvm pIdvm);

	void clearBlocks();

}
