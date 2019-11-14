package soup;

import java.util.ArrayList;

import soup.block.BlockGrid;
import soup.block.Food;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import soup.idvm.iIdvm;
import datatypes.Constants;
import datatypes.Pos;
import mvc.Model;
import mvc.present.iPresentSoup;

public class Soup extends Model implements iPresentSoup {
	private iIdvm mIdvm;
	private iBlockGrid mBlockGrid;
	private ArrayList<iBlock> mAllBlocks = new ArrayList<iBlock>();

	public Soup(iIdvm pIdvm) {
		mBlockGrid = new BlockGrid();
		mIdvm = pIdvm;
		mIdvm.setBlockGrid(mBlockGrid);
		mBlockGrid.addInitialIdvm(mIdvm);
		initFoodBlocks();
		finishSoup();
	}

	private void finishSoup() {
		Pos lIdvmPos = mIdvm.getPos();
		for (int x = lIdvmPos.x - 1; x <= lIdvmPos.x + 1; x++) {
			for (int y = lIdvmPos.y - 1; y <= lIdvmPos.y + 1; y++) {
				mBlockGrid.setBlock(new Pos(x, y), null);
			}
		}
	}

	private void initFoodBlocks() {
		Food[] food = new Food[Constants.foodSupply];
		// loop rest via block array
		for (iBlock iFoodBlock : food) {
			mBlockGrid.setRandomBlock(iFoodBlock);
			mAllBlocks.add(iFoodBlock);
		}
	}

	public iBlock getBlock(Pos pos) {
		return mBlockGrid.getBlock(pos);
	}

	public void refreshBlocks() {
		mBlockGrid.clearBlocks();
		for (iBlock iBlock : mAllBlocks) {
			mBlockGrid.setBlock(iBlock.getPos(), iBlock);
		}
		for (iBlock iBlock : mIdvm.getUsedBlocks()) {
			mBlockGrid.setBlock(iBlock.getPos(), iBlock);
		}
	}
}
