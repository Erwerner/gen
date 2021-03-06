package core.soup.block;

import globals.Config;
import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import core.soup.idvm.Idvm;
import core.soup.idvm.iIdvm;

public class BlockGrid {
	private iBlock[][] mGrid;

	public BlockGrid() {
		mGrid = new iBlock[Config.cSoupSize][Config.cSoupSize];
	}

	public iBlock getBlock(Pos pos) throws PosIsOutOfGrid {
		pos.isInGrid();
		return mGrid[pos.x][pos.y];
	}

	public void setBlock(Pos pos, iBlock block) throws PosIsOutOfGrid {
		pos.isInGrid();
		mGrid[pos.x][pos.y] = block;
	}

	public void setRandomBlock(iBlock pBlock) {
		while (true) {
			Pos lPos = Pos.getRandomSoupPosition();
			try {
				if (getBlock(lPos) == null) {
					setBlock(lPos, pBlock);
					pBlock.setPosition(lPos);
					return;
				}
			} catch (PosIsOutOfGrid e) {
			}
		}
	}

	public void addInitialIdvm(iIdvm pIdvm) {
		Pos lPos = new Pos(Config.cSoupSize / 2, Config.cSoupSize / 2);
		pIdvm.setPosition(lPos);
		for (iBlock iBlock : pIdvm.getUsedBlocks()) {
			refreshBlock(iBlock);
		}
	}

	private void refreshBlock(iBlock pBlock) {
		Pos lPos = pBlock.getPos();
		mGrid[lPos.x][lPos.y] = pBlock;
	}

	public void clearBlocks() {
		for (int x = 0; x < Config.cSoupSize; x++) {
			for (int y = 0; y < Config.cSoupSize; y++) {
				mGrid[x][y] = null;
			}
		}
	}

	// TOTO TEST with Mock
	public void detectCollisions(Idvm pIdvm) {
		for (iBlock iBlock : pIdvm.getUsedBlocks()) {
			Pos iPos = iBlock.getPos();
			iBlock lGridBlock;
			try {
				lGridBlock = getBlock(iPos);
				if (lGridBlock != null) {
					switch (lGridBlock.getBlockType()) {
					case FOOD:
						pIdvm.interactWithFood();
						lGridBlock.setNull();
						break;
					case ENEMY:
						pIdvm.interactWithEnemy((Enemy) lGridBlock);
						break;
					case PARTNER:
						pIdvm.interactWithPartner();
						setRandomBlock(lGridBlock);
						break;
					default:
						break;
					}
				}
			} catch (PosIsOutOfGrid e) {
			}
		}
	}
}
