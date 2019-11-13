package soup.block;

import datatypes.Pos;


public interface iBlock {
	BlockType getBlockType();
	iBlock setPosition(Pos pPos);
	Pos getPosition();
	void step();
}
