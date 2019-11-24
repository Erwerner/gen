package core.soup.block;

import core.datatypes.Pos;

public interface iBlock {
	BlockType getBlockType();

	iBlock setPosition(Pos pPos);

	Pos getPos();

	// TODO 3 REF move to new interface iLiving
	void step();

	void setNull();
}
