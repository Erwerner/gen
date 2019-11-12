package soup.block;

import datatypes.Pos;

public class EmptyBlock implements iBlock {

	private Pos mPos;

	public BlockType getBlockType() {
		return BlockType.NOTHING;
	}

	public void setPosition(Pos pPos) {
		mPos = pPos;
	}

	public Pos getPosition() {
		return mPos;
	}

}
