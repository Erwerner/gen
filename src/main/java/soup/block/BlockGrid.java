package soup.block;

import org.junit.internal.runners.TestMethod;

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

	public void setRandomBlock(iBlock pBlock) {
		while (true) {
			Pos lPos = Pos.getRandomSoupPosition();
			if (getBlock(lPos) == null) {
				setBlock(lPos, pBlock);
				pBlock.setPosition(lPos);
				return;
			}
		}
	}

	public void addInitialIdvm(iIdvm pIdvm) {
		Pos lPos = new Pos(Constants.soupSize / 2, Constants.soupSize / 2);
		pIdvm.setPosition(lPos);
		for (iBlock iBlock : pIdvm.getUsedBlocks()) {
			refreshBlock(iBlock);
		}
	}

	private void refreshBlock(iBlock pBlock) {
		Pos lPos = pBlock.getPos();
		mGrid[lPos.x][lPos.y] = pBlock;
	}

	public void clearBlocks() {
		for (int x = 0; x < Constants.soupSize; x++) {
			for (int y = 0; y < Constants.soupSize; y++) {
				mGrid[x][y] = null;
			}
		}
	}
}
