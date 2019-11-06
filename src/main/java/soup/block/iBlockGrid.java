package soup.block;

import datatypes.Position;

public interface iBlockGrid {

	iBlock getBlock(Position pos);

	void setBlock(Position pos, iBlock block);

	void setRandomBlock(iBlock foodBlock);

}
