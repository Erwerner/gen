package soup.block;

import soup.idvm.iIdvm;
import datatypes.Constants;
import datatypes.Pos;

public class BlockGrid implements iBlockGrid {
	private iBlock[][] mGrid;

	public BlockGrid() {
		mGrid = new iBlock[Constants.soupSize][Constants.soupSize];
	}

	public iBlock getBlock(Pos pos) {
		return mGrid[pos.x][pos.y];
	}

	public void setBlock(Pos pos, iBlock block) {
		mGrid[pos.x][pos.y] = block;
	}

	public void setRandomBlock(iBlock foodBlock) {
		while (true) {
			Pos pos = Pos.getRandomSoupPosition();
			if (getBlock(pos) == null) {
				setBlock(pos, foodBlock);
				return;
			}
		}
	}

	public void addInitialIdvm(iIdvm pIdvm) {
		Pos lPos = new Pos(Constants.soupSize/2, Constants.soupSize/2);
		pIdvm.setPosition(lPos);
		for(iBlock iBlock : pIdvm.getUsedBlocks()) {
			refreshBlock(iBlock);
		}
	}

	private void refreshBlock(iBlock pBlock) {
		Pos lPos = pBlock.getPosition();
		mGrid[lPos.x][lPos.y] = pBlock;
	}
}