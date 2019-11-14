package soup;

import soup.block.BlockGrid;
import soup.block.Food;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import soup.idvm.IdvmState;
import soup.idvm.Sensor;
import soup.idvm.iIdvm;

import java.util.HashMap;

import datatypes.Constants;
import datatypes.Direction;
import datatypes.Pos;
import mvc.Model;
import mvc.controller.iControllRunSoup;
import mvc.present.iPresentIdvm;
import mvc.present.iPresentSoup;

public class Soup extends Model implements iPresentSoup, iPresentIdvm, iControllRunSoup {
	private iIdvm mIdvm;
	private iBlockGrid mBlockGrid;

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
		for (iBlock foodBlock : food) {
			mBlockGrid.setRandomBlock(foodBlock);
		}
	}

	public iBlock getBlock(Pos pos) {
		return mBlockGrid.getBlock(pos);
	}

	public void refreshBlocks() {
		// #TODO abc;
		// all Nothing
		// loop rest via block array
	}

	public HashMap<Pos, Sensor> getDetectedPos() {
		iPresentIdvm lPresentIdvm = (iPresentIdvm) mIdvm;
		return lPresentIdvm.getDetectedPos();
	}

	public Direction getTargetDirection() {
		iPresentIdvm lPresentIdvm = (iPresentIdvm) mIdvm;
		return lPresentIdvm.getTargetDirection();
	}

	public int getStepCount() {
		iPresentIdvm lPresentIdvm = (iPresentIdvm) mIdvm;
		return lPresentIdvm.getStepCount();
	}

	public boolean isAlive() {
		iPresentIdvm lPresentIdvm = (iPresentIdvm) mIdvm;
		return lPresentIdvm.isAlive();
	}

	public Boolean isHungry() {
		iPresentIdvm lPresentIdvm = (iPresentIdvm) mIdvm;
		return lPresentIdvm.isHungry();
	}

	public IdvmState getState() {
		iPresentIdvm lPresentIdvm = (iPresentIdvm) mIdvm;
		return lPresentIdvm.getState();
	}

	public Pos getPosition() {
		iPresentIdvm lPresentIdvm = (iPresentIdvm) mIdvm;
		return lPresentIdvm.getPosition();
	}

	public void run() {
		mIdvm.step();
		notifyViews();
		mIdvm.step();
		notifyViews();
	}
}
