package soup;

import soup.block.Food;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import soup.idvm.Idvm;
import soup.idvm.iIdvm;
import datatypes.Constants;
import datatypes.Pos;
import mvc.Model;
import mvc.present.iPresentSoup;

public class soup extends Model implements iPresentSoup{
	private iIdvm mIdvm;
	private iBlockGrid mBlockGrid;
	private int mStepCount;
	
	public soup(Genome pGenome){
		initIdvm(pGenome);
		initFoodBlocks();
		finishSoup();
	}

	private void finishSoup() {
		// TODO delete surrounding blocks of idvm
		
	}

	private void initIdvm(Genome pGenome) {
		mIdvm = new Idvm(pGenome);
		mBlockGrid.addInitialIdvm(mIdvm);
	}

	private void initFoodBlocks() {
		Food[] food = new Food[Constants.foodSupply];
		for(iBlock foodBlock: food){
			mBlockGrid.setRandomBlock(foodBlock);			
		}
	}

	public Pos getIdvmPosition() {
		return mIdvm.getPosition();
	}

	public iBlock getBlock(Pos pos) {
		return mBlockGrid.getBlock(pos);
	}

	public boolean isIdvmAlive() {
		return mIdvm.isAlive();
	}

	public int getStepCount() {
		return mStepCount;
	}

	public Boolean isHungry() {
		return mIdvm.isHungry();
	}

}
