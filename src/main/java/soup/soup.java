package soup;

import soup.block.BlockGrid;
import soup.block.Food;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import datatypes.Constants;
import datatypes.Position;
import mvc.Model;
import mvc.present.iPresentSoup;

public class soup extends Model implements iPresentSoup{
	private iIdvm idvm;
	private iBlockGrid blockGrid;
	
	public soup(){
		initIdvm();
		initFoodBlocks();
	}

	private void initIdvm() {
		// TODO Auto-generated method stub
	}

	private void initFoodBlocks() {
		Food[] food = new Food[Constants.foodSupply];
		for(iBlock foodBlock: food){
			blockGrid.setRandomBlock(foodBlock);			
		}
	}

	public Position getIdvmPosition() {
		return idvm.getPosition();
	}

	public iBlock getBlock(Position pos) {
		return blockGrid.getBlock(pos);
	}

	public boolean isIdvmAlive() {
		return idvm.isAlive();
	}

}
