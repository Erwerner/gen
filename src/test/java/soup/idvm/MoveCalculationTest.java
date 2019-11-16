package soup.idvm;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import soup.block.BlockGrid;
import soup.block.BlockType;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import datatypes.Constants;
import datatypes.Direction;
import datatypes.Pos;
import exceptions.ExOutOfGrid;

public class MoveCalculationTest {
	iIdvmMoveCalculation cut;
	iBlockGrid mBlockGrid = new BlockGrid();
	private ArrayList<iBlock> mIdvmBlocks = new ArrayList<iBlock>();

	@Before
	public void setUp() throws Exception {
		mIdvmBlocks.add(new IdvmCell(BlockType.LIFE, new Pos(0, 0)));
		mIdvmBlocks.add(new IdvmCell(BlockType.LIFE, new Pos(0, 1)));
		mIdvmBlocks.add(new IdvmCell(BlockType.LIFE, new Pos(1, 0)));
		mIdvmBlocks.add(new IdvmCell(BlockType.LIFE, new Pos(1, 1)));

		cut = new MoveCalculation(mBlockGrid);
	}

	@Test
	public void returnsMovePos() throws ExOutOfGrid {
		Pos lMovePos = cut.calcPosFromDirection(Direction.UP,
				getIdvmPos(Constants.soupSize / 2, Constants.soupSize / 2),
				mIdvmBlocks);
		assertNotNull(lMovePos);
	}

	@Test
	public void returnsPosUP() throws ExOutOfGrid {
		Pos lMovePos = cut.calcPosFromDirection(Direction.UP,
				getIdvmPos(Constants.soupSize / 2, Constants.soupSize / 2),
				mIdvmBlocks);
		assertEquals(
				new Pos(Constants.soupSize / 2, Constants.soupSize / 2 - 1),
				lMovePos);
	}

	private Pos getIdvmPos(int pX, int pY) {
		Pos lNewPos = new Pos(pX, pY);
		for (iBlock iBlock : mIdvmBlocks) {
			IdvmCell lCell = (IdvmCell) iBlock;
			lCell.setPosition(lNewPos);
		}
		return lNewPos;
	}

	@Test(expected = ExOutOfGrid.class)
	public void noMovementAtTopBorder() throws ExOutOfGrid {
		Pos lStartPos = getIdvmPos(Constants.soupSize / 2, 0);
		Pos lMovePos = cut.calcPosFromDirection(Direction.UP, lStartPos,
				mIdvmBlocks);
	}

}
