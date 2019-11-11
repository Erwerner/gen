package soup.idvm;

import java.util.ArrayList;

import soup.Genome;
import soup.block.BlockType;
import soup.block.iBlock;

import datatypes.Pos;

public class Idvm implements iIdvm {

	private Genome mGenomeOrigin;
	private Genome mGenome;
	private int mHunger;
	private int mLastFood;
	private Pos mMidPosition = new Pos(0,0);
	private IdvmCell[][] mCellGrid = new IdvmCell[4][4];

	public Idvm(Genome pGenome)  {
		mGenomeOrigin = pGenome;
		mGenome   = pGenome;
		mHunger = mGenome.hunger;
		grow();
		grow();
		grow();
		grow();
	}

	public void grow() {
		IdvmCell lCell = mGenome.getCell();
		Pos lPos = lCell.getPosOnIdvm();
		mCellGrid[lPos.x+1][lPos.y+1] = lCell;
		refreshCellPos(lPos.x+1, lPos.y+1);
	}

	public boolean isAlive() {
		for(iBlock iCell : getUsedBlocks()){
			if(iCell.getBlockType() == BlockType.LIFE){
				return true;
			}
		}
		return false;
	}

	public Boolean isHungry() {
		return mLastFood <= mHunger;
	}

	public Boolean isBarrier() {
		return false;
	}

	public BlockType getBlockType() {
		return BlockType.IDVM;
	}

	public void setPosition(Pos pPos) {
		mMidPosition = pPos;
		for(int x = 0; x<=3;x++){
			for(int y=0;y<=3;y++){
				refreshCellPos(x, y);
			}
		}
	}

	private void refreshCellPos(int pCellX, int pCellY) {
		IdvmCell lCell = mCellGrid[pCellX][pCellY];
		if(lCell != null){
			Pos lNewPos = new Pos(mMidPosition.x-2+pCellX, mMidPosition.y-2+pCellY);
			lCell.setPosition(lNewPos);
		}
	}

	public Pos getPosition() {
		return mMidPosition;
	}

	public ArrayList<iBlock> getUsedBlocks() {
		ArrayList<iBlock> lBlocks = new ArrayList<iBlock>();
		for(IdvmCell[] iRow : mCellGrid){
			for(iBlock iCell: iRow){
				if(iCell != null){
					lBlocks.add(iCell);
				}
			}
		}
		return lBlocks ;
	}
	public void killCell(Pos pPos){
		mCellGrid[pPos.x - mMidPosition.x+2][pPos.y - mMidPosition.y+2].kill();
	}
}
