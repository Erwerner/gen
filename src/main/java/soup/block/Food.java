package soup.block;

import datatypes.Pos;


public class Food implements iBlock {

	private Pos mPos;

	public Boolean isBarrier() {
		return false;
	}

	public BlockType getBlockType() {
		return BlockType.FOOD;
	}

	public void setPosition(Pos pPos) {
		mPos = pPos;
	}

	public Pos getPosition() {
		return mPos;
	}
	
}
