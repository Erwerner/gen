package core.soup;

import java.util.ArrayList;

import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import core.soup.block.BlockGrid;
import core.soup.block.BlockType;
import core.soup.block.Enemy;
import core.soup.block.Food;
import core.soup.block.iBlock;
import core.soup.block.iBlockGrid;
import core.soup.idvm.iIdvm;
import globals.Config;
import ui.presenter.iPresentSoup;

public class Soup implements iSoup {
	private iIdvm mIndividuum;
	private iBlockGrid mBlockGrid;
	private ArrayList<iBlock> mAllBlocks = new ArrayList<iBlock>();

	public Soup(iIdvm pIdvm) {
		mBlockGrid = new BlockGrid();
		mIndividuum = pIdvm;
		mIndividuum.setBlockGrid(mBlockGrid);
		initFoodBlocks();
		initEnemyBlocks();
		clearSurroundingsOfIdvm();
		mBlockGrid.addInitialIdvm(mIndividuum);
	}

	private void initEnemyBlocks() {
		Enemy[] lEnemyList = new Enemy[Config.enemySupply];
		for (int i = 0; i < Config.enemySupply; i++)
			lEnemyList[i] = new Enemy(mBlockGrid);
		for (iBlock iEnemyBlock : lEnemyList) {
			mBlockGrid.setRandomBlock(iEnemyBlock);
			mAllBlocks.add(iEnemyBlock);
		}
	}

	private void clearSurroundingsOfIdvm() {
		ArrayList<Pos> lIdvmPosList = new ArrayList<Pos>();

		for (iBlock iBlock : mIndividuum.getUsedBlocks()) {
			lIdvmPosList.add(iBlock.getPos());
		}

		for (int x = Config.soupSize / 2 - 2; x <= Config.soupSize / 2 + 3; x++) {
			for (int y = Config.soupSize / 2 - 2; y <= Config.soupSize / 2 + 3; y++) {
				try {
					mBlockGrid.setBlock(new Pos(x, y), null);
				} catch (PosIsOutOfGrid e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void initFoodBlocks() {
		Food[] lFoodBlocks = new Food[Config.foodSupply];
		for (int i = 0; i < Config.foodSupply; i++)
			lFoodBlocks[i] = new Food();
		for (iBlock iFoodBlock : lFoodBlocks) {
			mBlockGrid.setRandomBlock(iFoodBlock);
			mAllBlocks.add(iFoodBlock);
		}
	}

	public iBlock getBlock(Pos pos) throws PosIsOutOfGrid {
		return mBlockGrid.getBlock(pos);
	}

	public void refreshBlocks() {
		mBlockGrid.clearBlocks();
		for (iBlock iBlock : mAllBlocks) {
			try {
				mBlockGrid.setBlock(iBlock.getPos(), iBlock);
			} catch (PosIsOutOfGrid e) {
			}
		}
		mIndividuum.detectCollisions();

		for (iBlock iBlock : mAllBlocks) {
			try {
				if (iBlock.getBlockType() == BlockType.NULL) {
					mBlockGrid.setBlock(iBlock.getPos(), null);
				}
			} catch (PosIsOutOfGrid e) {
			}
		}
		for (iBlock iBlock : mIndividuum.getUsedBlocks()) {
			try {
				mBlockGrid.setBlock(iBlock.getPos(), iBlock);
			} catch (PosIsOutOfGrid e) {
			}
		}
	}

	public void step() {
		for (iBlock iBlock : mAllBlocks)
			iBlock.step();
		mIndividuum.step();
		refreshBlocks();
	}

	public iPresentSoup getPresenter() {
		return this;
	}

	public void executeIdvm() {
		while (mIndividuum.isAlive())
			step();
	}
}
