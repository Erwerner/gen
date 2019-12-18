package core.soup.idvm;

import java.util.ArrayList;

import core.datatypes.Pos;
import core.soup.block.BlockType;
import core.soup.block.IdvmCell;
import core.soup.block.iBlock;

public class IdvmCellGrid {
	public IdvmCell[][] mGrid = new IdvmCell[4][4];

	public void removeCell(Pos pKillPos) {
		mGrid[pKillPos.x][pKillPos.y] = null;
	}

	public void refreshCellPosOnGrid(int pCellX, int pCellY, Pos pIdvmPosOnGrid) {
		IdvmCell lCell = mGrid[pCellX][pCellY];
		if (lCell != null) {
			Pos lNewPos = new Pos(pIdvmPosOnGrid.x - 1 + pCellX, pIdvmPosOnGrid.y - 1 + pCellY);
			lCell.setPosition(lNewPos);
		}
	}

	//TODO REF remove IdvmPos
	public void appendCell(IdvmCell lCell, Pos pIdvmPos) {
		Pos lPos = lCell.getPosOnIdvm();
		mGrid[lPos.x + 1][lPos.y + 1] = lCell;
		refreshCellPosOnGrid(lPos.x + 1, lPos.y + 1, pIdvmPos);
	}

	public ArrayList<iBlock> getGridBlocks() {
		ArrayList<iBlock> lBlocks = new ArrayList<iBlock>();
		for (IdvmCell[] iRow : mGrid) {
			for (iBlock iCell : iRow) {
				if (iCell != null) {
					lBlocks.add(iCell);
				}
			}
		}
		return lBlocks;
	}
	public ArrayList<iBlock> getGridBlocksOfType(BlockType pBlockType) {
		ArrayList<iBlock> lBlocks = new ArrayList<iBlock>();
		for (iBlock iBlock : getGridBlocks())
			if (iBlock.getBlockType() == pBlockType)
				lBlocks.add(iBlock);
		return lBlocks;
	}

	public void refreshAllCells(Pos pPos) {
		for (int x = 0; x <= 3; x++) {
			for (int y = 0; y <= 3; y++) {
				refreshCellPosOnGrid(x, y, pPos);
			}
		}
	}

}
