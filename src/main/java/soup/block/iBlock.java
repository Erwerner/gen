package soup.block;

import datatypes.Pos;


public interface iBlock {
	BlockType getBlockType();
	void setPosition(Pos pPos);
	Pos getPosition();
}
