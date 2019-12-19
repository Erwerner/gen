package core.soup.block;

import java.io.Serializable;

import core.datatypes.Pos;
import core.genes.iGene;
import globals.Helpers;
import lombok.Data;

@Data
public class IdvmCell extends Block implements Serializable, iGene {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pos mPosOnIdvm;
	private BlockType mBlockType;

	public IdvmCell(BlockType pCellType, Pos pPos) {
		super(BlockType.CELL);
		mBlockType = pCellType;
		mPosOnIdvm = pPos;
		mPos = new Pos(0, 0);
	}

	public BlockType getBlockType() {
		return mBlockType;
	}

	public Pos getPosOnIdvm() {
		return mPosOnIdvm;
	}

	@Override
	public String toString() {

		String lString;
		lString = mBlockType.toString() + "," + mPosOnIdvm.x + "," + mPosOnIdvm.y + "," + mPos.x + "," + mPos.y;
		return lString;
	}

	@Override
	public boolean equals(Object o) {
		IdvmCell other = (IdvmCell) o;
		return this.mBlockType == other.mBlockType && this.mPos.equals(other.mPos)
				&& this.mPosOnIdvm.equals(other.mPosOnIdvm);
	}

	public void step() {
	}

	public void mutate() {
		BlockType[] lCellTypes = { BlockType.LIFE, BlockType.DEFENCE, BlockType.MOVE, BlockType.SENSOR };
		mBlockType = (BlockType) Helpers.rndArrayEntry(lCellTypes);
		mPosOnIdvm = new Pos(Helpers.rndIntRange(-1, 2), Helpers.rndIntRange(-1, 2));
	}

	@Override
	public iGene clone() throws CloneNotSupportedException {
		return new IdvmCell(mBlockType, new Pos(mPosOnIdvm.x, mPosOnIdvm.y));
	}
}
