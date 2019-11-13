package soup;

import soup.block.BlockGrid;
import soup.block.Food;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import soup.idvm.iIdvm;
import datatypes.Constants;
import datatypes.Pos;
import mvc.Model;
import mvc.present.iPresentSoup;

public class Soup extends Model implements iPresentSoup{
	private iIdvm mIdvm;
	private iBlockGrid mBlockGrid;
	
	public Soup(iIdvm pIdvm){
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
		for(iBlock foodBlock: food){
			mBlockGrid.setRandomBlock(foodBlock);			
		}
	}

	public iBlock getBlock(Pos pos) {
		return mBlockGrid.getBlock(pos);
	}
	public void refreshBlocks(){
		//#TODO abc;
		//all Nothing
		//loop rest via block array
	}
}
