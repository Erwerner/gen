package soup.block;

import datatypes.Pos;


public interface iBlock {
	Boolean isBarrier();
	BlockType getBlockType();
	void setPosition(Pos pPos);
	Pos getPosition();
}
