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
		// TODO delete surrounding blocks of idvm
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
			mBlockGrid.setBlock(iBlock.getPosition(), iBlock);
		}
		for (iBlock iBlock : mIdvm.getUsedBlocks()) {
			mBlockGrid.setBlock(iBlock.getPosition(), iBlock);
		}
	}
}
