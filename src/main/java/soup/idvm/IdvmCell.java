package soup.idvm;

import lombok.Data;
import datatypes.Pos;
import soup.block.Block;
import soup.block.BlockType;
import soup.block.iBlock;

@Data
public class IdvmCell extends Block {

	private Pos mPosOnIdvm;
	private BlockType mBlockType;
    
	public IdvmCell(BlockType pCellType, Pos pPos){
		super(BlockType.CELL);
		mBlockType = pCellType;
		mPosOnIdvm = pPos;
	}

	public BlockType getBlockType() {
		return mBlockType;
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
	public void step() { }

}
