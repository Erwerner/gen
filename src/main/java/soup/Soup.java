package soup;

import java.util.ArrayList;
import java.util.HashMap;

import mvc.present.iPresentIdvm;
import soup.block.BlockGrid;
import soup.block.Enemy;
import soup.block.Food;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import soup.idvm.IdvmState;
import soup.idvm.Sensor;
import soup.idvm.iIdvm;
import datatypes.Constants;
import datatypes.Direction;
import datatypes.Pos;

public class Soup implements iSoup {
	private iIdvm mIdvm;
	private iBlockGrid mBlockGrid;
	private ArrayList<iBlock> mAllBlocks = new ArrayList<iBlock>();

	public Soup(iIdvm pIdvm) {
		mBlockGrid = new BlockGrid();
		mIdvm = pIdvm;
		mIdvm.setBlockGrid(mBlockGrid);
		mBlockGrid.addInitialIdvm(mIdvm);
		initFoodBlocks();
		initEnemyBlocks();
		finishSoup();
	}

	private void initEnemyBlocks() {
		Enemy[] lEnemyList = new Enemy[Constants.enemySupply];
		for (iBlock iEnemyBlock : lEnemyList) {
			mBlockGrid.setRandomBlock(iEnemyBlock);
			mAllBlocks.add(iEnemyBlock);
		}
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
		Food[] lFoodBlocks = new Food[Constants.foodSupply];
		for (int i = 0; i < Constants.foodSupply; i++)
			lFoodBlocks[i] = new Food();
		for (iBlock iFoodBlock : lFoodBlocks) {
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
		for (iBlock iBlock : mIdvm.getUsedBlocks())
			mBlockGrid.setBlock(iBlock.getPos(), iBlock);
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
		return lPresentIdvm.getPos();
	}

	public void step() {
		try {
			mIdvm.step();
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
