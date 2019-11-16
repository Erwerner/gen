package soup;

import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;

import datatypes.Constants;
import datatypes.Pos;
import exceptions.ExOutOfGrid;
import mvc.present.iPresentSoup;
import soup.block.BlockGrid;
import soup.block.BlockType;
import soup.block.Enemy;
import soup.block.Food;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import soup.idvm.iIdvm;

public class Soup implements iSoup {
	private iIdvm mIdvm;
	private iBlockGrid mBlockGrid;
	private ArrayList<iBlock> mAllBlocks = new ArrayList<iBlock>();

	public Soup(iIdvm pIdvm) {
		mBlockGrid = new BlockGrid();
		mIdvm = pIdvm;
		mIdvm.setBlockGrid(mBlockGrid);
		initFoodBlocks();
		initEnemyBlocks();
		mBlockGrid.addInitialIdvm(mIdvm);
		clearSurroundingsOfIdvm();
	}

	private void initEnemyBlocks() {
		Enemy[] lEnemyList = new Enemy[Constants.enemySupply];
		for (int i = 0; i < Constants.enemySupply; i++)
			lEnemyList[i] = new Enemy();
		for (iBlock iEnemyBlock : lEnemyList) {
			mBlockGrid.setRandomBlock(iEnemyBlock);
			mAllBlocks.add(iEnemyBlock);
		}
	}

	private void clearSurroundingsOfIdvm() {
		Pos lIdvmMidPos = mIdvm.getPos();
		ArrayList<Pos> lIdvmPosList = new ArrayList<Pos>();

		for (iBlock iBlock : mIdvm.getUsedBlocks()) {
			lIdvmPosList.add(iBlock.getPos());
		}

		for (int x = lIdvmMidPos.x - 1; x <= lIdvmMidPos.x + 2; x++) {
			for (int y = lIdvmMidPos.y - 1; y <= lIdvmMidPos.y + 2; y++) {
				if (!lIdvmPosList.contains(new Pos(x, y)))
					try {
						mBlockGrid.setBlock(new Pos(x, y), null);
					} catch (ExOutOfGrid e) {
						e.printStackTrace();
					}
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

	public iBlock getBlock(Pos pos) throws ExOutOfGrid {
		return mBlockGrid.getBlock(pos);
	}

	public void refreshBlocks() {
		mBlockGrid.clearBlocks();
		for (iBlock iBlock : mAllBlocks) {
			try {
				mBlockGrid.setBlock(iBlock.getPos(), iBlock);
			} catch (ExOutOfGrid e) {
				e.printStackTrace();
			}
		}
		mIdvm.detectCollisions();

		for (iBlock iBlock : mAllBlocks) {
			try {
				if(iBlock.getBlockType()==BlockType.NULL){
					mBlockGrid.setBlock(iBlock.getPos(), null);
				}
			} catch (ExOutOfGrid e) {
				e.printStackTrace();
			}
		}
		for (iBlock iBlock : mIdvm.getUsedBlocks()){
			try {
				mBlockGrid.setBlock(iBlock.getPos(), iBlock);
			} catch (ExOutOfGrid e) {
				e.printStackTrace();
			}
		}
	}

	public void step() {
		mIdvm.step();
		for (iBlock iBlock : mAllBlocks)
			iBlock.step();
		refreshBlocks();
	}

	public iPresentSoup getPresenter() {
		return this;
	}
}
