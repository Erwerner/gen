package soup.block;

import datatypes.Pos;

public abstract class Block implements iBlock {

	public Block(BlockType pBlockType) {
		super();
		mBlockType = pBlockType;
	}

	private BlockType mBlockType;
	protected Pos mPos;

	public BlockType getBlockType() {
		return mBlockType;
	}

	public iBlock setPosition(Pos pPos) {
		mPos = pPos;
		return this;
	}

	public Pos getPos() {
		return mPos;
	}
	@Override
	public String toString(){
		return mBlockType + " " + mPos;
	}
	
	public void setNull(){
		mBlockType = BlockType.NULL;
	}
}
