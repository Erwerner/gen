package soup;

import java.util.ArrayList;

import soup.block.BlockGrid;
import soup.block.Enemy;
import soup.block.Food;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import soup.idvm.iIdvm;
import datatypes.Constants;
import datatypes.Pos;
import exceptions.ExWrongBlockType;

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

	public void step() {
		try {
			mIdvm.step();
			for (iBlock iBlock : mAllBlocks)
				iBlock.step();
			refreshBlocks();
			detectCollisions();
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void detectCollisions() {
		iBlock lInteractedBlock;
		ArrayList<Pos> lIdvmPos = new ArrayList<Pos>();
		for (iBlock iBlock : mIdvm.getUsedBlocks()) {
			lIdvmPos.add(iBlock.getPos());
		}
		for (Pos iPos : lIdvmPos) {
			for (iBlock iBlock : mAllBlocks) {
				if (iBlock.getPos() == iPos) {
					switch (iBlock.getBlockType()) {
					case FOOD:
						lInteractedBlock = mIdvm
								.interactWithFood((Food) iBlock);
						break;
					case ENEMY:
						lInteractedBlock = mIdvm
								.interactWithEnemy((Enemy) iBlock);
						break;
					default:
						throw new ExWrongBlockType();
					}
					mBlockGrid.setBlock(iPos, lInteractedBlock);
				}
			}
		}
	}
}
