package soup.block;

import datatypes.Pos;
import exceptions.ExOutOfGrid;
import globals.Constants;
import soup.idvm.iIdvm;

public class BlockGrid implements iBlockGrid {
	private iBlock[][] mGrid;

	public BlockGrid() {
		mGrid = new iBlock[Constants.soupSize][Constants.soupSize];
	}

	public iBlock getBlock(Pos pos) throws ExOutOfGrid {
		pos.isInGrid();
		return mGrid[pos.x][pos.y];
	}

	public void setBlock(Pos pos, iBlock block) throws ExOutOfGrid {
		pos.isInGrid();
		mGrid[pos.x][pos.y] = block;
	}

	public void setRandomBlock(iBlock pBlock) {
		while (true) {
			Pos lPos = Pos.getRandomSoupPosition();
			try {
				if (getBlock(lPos) == null) {
					setBlock(lPos, pBlock);
					pBlock.setPosition(lPos);
					return;
				}
			} catch (ExOutOfGrid e) {
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
