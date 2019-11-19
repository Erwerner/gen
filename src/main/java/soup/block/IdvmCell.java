package soup.block;

import genes.iGene;
import globals.Global;

import java.util.Random;

import lombok.Data;
import datatypes.Pos;

@Data
public class IdvmCell extends Block implements iGene {

	private Pos mPosOnIdvm;
	private BlockType mBlockType;

	public IdvmCell(BlockType pCellType, Pos pPos) {
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
	public String toString() {

		String lString;
		lString = mBlockType.toString() + "," + mPosOnIdvm.x + ","
				+ mPosOnIdvm.y + "," + mPos.x + "," + mPos.y;
		return lString;
	}

	@Override
	public boolean equals(Object o) {
		IdvmCell other = (IdvmCell) o;
		return this.mBlockType == other.mBlockType
				&& this.mPos.equals(other.mPos)
				&& this.mPosOnIdvm.equals(other.mPosOnIdvm);
	}
	
	public void step() {
	}

	public void mutate(Double pMutationRate) {
		if (Global.checkChance(pMutationRate)) { 
			// TODO REF move to Globals
			Random lRandom = new Random();
			BlockType[] lCellTypes = { BlockType.LIFE,//TODO BlockType.DEFENCE,
					BlockType.MOVE, BlockType.SENSOR };
			int lRndBlock = lRandom.nextInt(lCellTypes.length);
			mBlockType = lCellTypes[lRndBlock];
		}
		if (Global.checkChance(pMutationRate)) {
			// TODO REF move to Globals
			Random lRandom = new Random();
			Pos lNewPos = new Pos(lRandom.nextInt(4)-1,lRandom.nextInt(4)-1);
			mPosOnIdvm = lNewPos;
		}
	}
}
