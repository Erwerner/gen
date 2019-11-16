package soup.block;

import datatypes.Pos;


public interface iBlock {
	BlockType getBlockType();
	iBlock setPosition(Pos pPos);
	Pos getPos();
	void step();
	void setNull();
}
