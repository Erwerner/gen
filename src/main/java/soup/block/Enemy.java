package soup.block;

import datatypes.Pos;

public class Enemy implements iBlock {

	private Pos mPos;

	public BlockType getBlockType() {
		return BlockType.ENEMY;
	}

	public void setPosition(Pos pPos) {
		mPos = pPos;
	}

	public Pos getPosition() {
		return mPos;
	}

}
