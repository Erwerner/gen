package core.soup.block;

import java.io.Serializable;

import core.datatypes.Pos;

public abstract class Block implements Serializable,  iBlock {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Block(BlockType pBlockType) {
		super();
		mBlockType = pBlockType;
	}

	public Block() {
		// For serialization
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
