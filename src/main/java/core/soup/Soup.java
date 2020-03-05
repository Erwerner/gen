package core.soup;

import java.util.ArrayList;

import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import core.soup.block.BlockGrid;
import core.soup.block.BlockType;
import core.soup.block.Enemy;
import core.soup.block.Food;
import core.soup.block.Partner;
import core.soup.block.iBlock;
import core.soup.idvm.Idvm;
import core.soup.idvm.iLiving;
import ui.presenter.iPresentSoup;

public class Soup implements iSoup {
	private Idvm mIndividuum;
	private BlockGrid mBlockGrid;
	private ArrayList<iBlock> mAllBlocks = new ArrayList<iBlock>();
	private ArrayList<iLiving> mAllLivings = new ArrayList<iLiving>();

	public Soup(Idvm pIdvm, EnvironmentConfig pEnvironmentConfig) {
		mBlockGrid = new BlockGrid();
		mIndividuum = pIdvm;
		mIndividuum.setBlockGrid(mBlockGrid);
		initFoodBlocks(pEnvironmentConfig);
		initEnemyBlocks(pEnvironmentConfig);
		initPartnerBlocks(pEnvironmentConfig);
		clearSurroundingsOfIdvm(pEnvironmentConfig);
		mBlockGrid.addInitialIdvm(mIndividuum);
		mAllLivings.add(mIndividuum);
	}

	private void initPartnerBlocks(EnvironmentConfig pEnvironmentConfig) {
		Partner[] lPartner = new Partner[pEnvironmentConfig.cPartnerSupply];
		for (int i = 0; i < pEnvironmentConfig.cPartnerSupply; i++)
			lPartner[i] = new Partner();
		for (iBlock iPartner : lPartner) {
			mBlockGrid.setRandomBlock(iPartner);
			mAllBlocks.add(iPartner);
		}
	}

	private void initEnemyBlocks(EnvironmentConfig pEnvironmentConfig) {
		Enemy[] lEnemyList = new Enemy[pEnvironmentConfig.cEnemySupply];
		for (int i = 0; i < pEnvironmentConfig.cEnemySupply; i++)
			lEnemyList[i] = new Enemy(mBlockGrid);
		for (iBlock iEnemyBlock : lEnemyList) {
			mBlockGrid.setRandomBlock(iEnemyBlock);
			mAllBlocks.add(iEnemyBlock);
			mAllLivings.add((iLiving) iEnemyBlock);
		}
	}

	private void clearSurroundingsOfIdvm(EnvironmentConfig pEnvironmentConfig) {
		ArrayList<Pos> lIdvmPosList = new ArrayList<Pos>();

		for (iBlock iBlock : mIndividuum.getUsedBlocks()) {
			lIdvmPosList.add(iBlock.getPos());
		}

		for (int x = pEnvironmentConfig.cSoupSize / 2 - 2; x <= pEnvironmentConfig.cSoupSize / 2 + 3; x++) {
			for (int y = pEnvironmentConfig.cSoupSize / 2 - 2; y <= pEnvironmentConfig.cSoupSize / 2 + 3; y++) {
				try {
					mBlockGrid.setBlock(new Pos(x, y), null);
				} catch (PosIsOutOfGrid e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void initFoodBlocks(EnvironmentConfig pEnvironmentConfig) {
		Food[] lFoodBlocks = new Food[pEnvironmentConfig.cFoodSupply];
		for (int i = 0; i < pEnvironmentConfig.cFoodSupply; i++)
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
		// REF move to BlockGrid
		mBlockGrid.detectCollisions(mIndividuum);

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
		for (iLiving iLiving : mAllLivings) {
			iLiving.step();
			refreshBlocks(); // TODO performance
		}
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
