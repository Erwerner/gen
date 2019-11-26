package core.soup.block;

import core.datatypes.Pos;

public interface iBlock {
	BlockType getBlockType();

	iBlock setPosition(Pos pPos);

	Pos getPos();

	void setNull();
}
