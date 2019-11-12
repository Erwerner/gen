package soup.idvm;

import lombok.Data;
import datatypes.Pos;
import soup.block.BlockType;
import soup.block.iBlock;

@Data
public class IdvmCell implements iBlock {

	private Pos mPos;
	private Pos mPosOnIdvm;
	private BlockType mBlockType;
    
	public IdvmCell(BlockType pCellType, Pos pPos){
		mBlockType = pCellType;
		mPosOnIdvm = pPos;
		mPos = pPos;
	}
	public Boolean isBarrier() {
		return false;
	}

	public BlockType getBlockType() {
		return mBlockType;
	}

	public void setPosition(Pos pPos) {
		mPos = pPos;
	}

	public Pos getPosition() {
		return mPos;
	}
	public Pos getPosOnIdvm() {
		return mPosOnIdvm;
	}

    @Override
    public String toString(){
    	
    	String lString;
    	lString = mBlockType.toString() + "," + 
    				mPosOnIdvm.x + "," + mPosOnIdvm.y +"," +
    				mPos.x + "," + mPos.y;
		return lString;
    }
    @Override
	public boolean equals(Object o){
		IdvmCell other = (IdvmCell) o;
		return this.mBlockType == other.mBlockType &&
				this.mPos.equals(other.mPos) &&
				this.mPosOnIdvm.equals(other.mPosOnIdvm);
	}
	public void kill() {
		mBlockType = BlockType.NOTHING;
	}

}
