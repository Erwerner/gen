package soup.idvm;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import datatypes.Directions;
import datatypes.Pos;

import soup.Genome;
import soup.block.BlockType;
import soup.block.Food;
import soup.block.iBlock;

public class IdvmTest {
	private static final int cStartPosX = 50;
	private static final int cStartPosY = 60;
	Idvm cut;
	Genome mGenome;
	private IdvmCell[] mCellGrow;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mCellGrow = new IdvmCell[6];
		mCellGrow[0] = new IdvmCell(BlockType.LIFE, new Pos(0, 0));
		mCellGrow[1] = new IdvmCell(BlockType.SENSOR, new Pos(1, 0));
		mCellGrow[2] = new IdvmCell(BlockType.MOVE, new Pos(0, 1));
		mCellGrow[3] = new IdvmCell(BlockType.DEFENCE, new Pos(1, 1));
		mCellGrow[4] = new IdvmCell(BlockType.MOVE, new Pos(0, 2));
		mCellGrow[5] = new IdvmCell(BlockType.SENSOR, new Pos(1, 1));

		mGenome = new Genome();
		for (IdvmCell iCell : mCellGrow) {
			mGenome.cell.add(iCell);
		}
		/*
		 * mGenome.cell.add(new IdvmCell(BlockType.LIFE, new Pos(0, 0)));
		 * mGenome.cell.add(new IdvmCell(BlockType.SENSOR, new Pos(1, 0)));
		 * mGenome.cell.add(new IdvmCell(BlockType.MOVE, new Pos(0, 1)));
		 * mGenome.cell.add(new IdvmCell(BlockType.DEFENCE, new Pos(1, 1)));
		 * mGenome.cell.add(new IdvmCell(BlockType.MOVE, new Pos(0, 2)));
		 * mGenome.cell.add(new IdvmCell(BlockType.SENSOR, new Pos(1, 1)));
		 */

		mGenome.hunger = 50;

		cut = new Idvm(mGenome);
		cut.setPosition(new Pos(cStartPosX, cStartPosY));
	}

	@Test
	public void initalCellsAreCorrect() {

		/*
		 * IdvmCell lC00 = new IdvmCell(BlockType.LIFE, new Pos(0, 0)); IdvmCell
		 * lC10 = new IdvmCell(BlockType.SENSOR, new Pos(1, 0)); IdvmCell lC01 =
		 * new IdvmCell(BlockType.MOVE, new Pos(0, 1)); IdvmCell lC11 = new
		 * IdvmCell(BlockType.DEFENCE, new Pos(1, 1)); lC00.setPosition(new
		 * Pos(cStartPosX-1, cStartPosY-1)); lC10.setPosition(new
		 * Pos(cStartPosX, cStartPosY-1)); lC01.setPosition(new
		 * Pos(cStartPosX-1, cStartPosY)); lC11.setPosition(new Pos(cStartPosX,
		 * cStartPosY));
		 */
		IdvmCell lC02 = new IdvmCell(BlockType.MOVE, new Pos(0, 2));
		lC02.setPosition(new Pos(cStartPosX - 1, cStartPosY + 1));

		ArrayList<iBlock> lUsedBlocks = cut.getUsedBlocks();
		assertEquals(4, lUsedBlocks.size());

		for (int i = 0; i <= 3; i++) {
			IdvmCell lCell = mCellGrow[i];
			Pos lPos = lCell.getPosOnIdvm();
			assertHasCell(true, lCell.getBlockType(), lPos.x, lPos.y, lPos.x
					+ cStartPosX, lPos.y + cStartPosY);
		}
		// assertIdvmHasCell(BlockType.LIFE, 0, 0, cStartPosX, cStartPosY);
		// assertIdvmHasCell(BlockType.SENSOR, 1, 0, cStartPosX+1, cStartPosY);
		// assertTrue(lUsedBlocks.contains(lC00));
		// assertTrue(lUsedBlocks.contains(lC10));
		// assertTrue(lUsedBlocks.contains(lC01));
		// assertTrue(lUsedBlocks.contains(lC11));
		assertFalse(lUsedBlocks.contains(lC02));
		assertHasGrowCellOnPosDiff(false, 4, 0, 0);
	}

	@Test
	public void growCellsAreCorrect() {
		assertHasGrowCellOnPosDiff(false, 4, 0, 0);
		Food lFood = new Food();
		lFood.setPosition(new Pos(20, 20));
		cut.eat(lFood);

		// IdvmCell lC02 = new IdvmCell(BlockType.MOVE, new Pos(0, 2));
		// lC02.setPosition(new Pos(cStartPosX - 1, cStartPosY + 1));
		ArrayList<iBlock> lUsedBlocks = cut.getUsedBlocks();
		assertEquals(5, lUsedBlocks.size());
		// assertTrue(lUsedBlocks.contains(lC02));
		assertHasGrowCellOnPosDiff(true, 4, 0, 0);
	}

	@Test
	public void growOverwritesInitialCell() {
		// IdvmCell lC11 = new IdvmCell(BlockType.DEFENCE, new Pos(1, 1));
		// lC11.setPosition(new Pos(cStartPosX, cStartPosY));
		ArrayList<iBlock> lUsedBlocks = cut.getUsedBlocks();
		assertEquals(4, lUsedBlocks.size());
		// assertTrue(lUsedBlocks.contains(lC11));
		assertHasGrowCellOnPosDiff(true, 3, 0, 0);

		Food lFood = new Food();
		lFood.setPosition(new Pos(20, 20));
		cut.eat(lFood);
		cut.eat(lFood);

		// lC11 = new IdvmCell(BlockType.SENSOR, new Pos(1, 1));
		// lC11.setPosition(new Pos(cStartPosX, cStartPosY));
		lUsedBlocks = cut.getUsedBlocks();
		assertEquals(5, lUsedBlocks.size());
		// assertTrue(lUsedBlocks.contains(lC11));
		assertHasGrowCellOnPosDiff(true, 4, 0, 0);
	}

	@Test
	public void moveChangesCellPos() {
		cut.setPosition(new Pos(60, 60));

		// IdvmCell lC00 = new IdvmCell(BlockType.LIFE, new Pos(0, 0));
		// lC00.setPosition(new Pos(59, 59));

		// ArrayList<iBlock> lUsedBlocks = cut.getUsedBlocks();
		// assertTrue(lUsedBlocks.contains(lC00));
		assertHasCell(true, BlockType.LIFE, 0, 0, 60, 60);
	}

	@Test
	public void killKills() {
		IdvmCell lC11 = new IdvmCell(BlockType.DEFENCE, new Pos(1, 1));
		// lC11.setPosition(new Pos(cStartPosX, cStartPosY));
		ArrayList<iBlock> lUsedBlocks = cut.getUsedBlocks();
		// assertTrue(lUsedBlocks.contains(lC11));

		assertHasGrowCellOnPosDiff(true, 0, 0, 0);

		cut.killCell(new Pos(cStartPosX, cStartPosY));

		assertFalse(lUsedBlocks.contains(lC11));
		lC11 = new IdvmCell(BlockType.NOTHING, new Pos(1, 1));
		lC11.setPosition(new Pos(cStartPosX + 1, cStartPosY + 1));
		lUsedBlocks = cut.getUsedBlocks();
		// assertTrue(lUsedBlocks.contains(lC11));
		assertHasCell(true, BlockType.NOTHING, 1, 1, cStartPosX + 1,
				cStartPosY + 1);
	}

	private void assertHasGrowCellOnPosDiff(Boolean pAssert, int lIdx,
			int lDiffX, int lDiffY) {
		assertHasCell(pAssert, mCellGrow[lIdx].getBlockType(),
				mCellGrow[lIdx].getPosOnIdvm().x,
				mCellGrow[lIdx].getPosOnIdvm().y,
				mCellGrow[lIdx].getPosOnIdvm().x + cStartPosX + lDiffX,
				mCellGrow[lIdx].getPosOnIdvm().y + cStartPosY + lDiffY);
	}

	@Test
	public void noLifeKills() {
		assertTrue(cut.isAlive());

		cut.killCell(new Pos(cStartPosX - 1, cStartPosY - 1));

		for (iBlock iCell : cut.getUsedBlocks()) {
			if (iCell.getBlockType() == BlockType.LIFE) {
				fail();
			}
		}

		assertFalse(cut.isAlive());
	}

	@Test
	public void getsHungry() {
		assertFalse(cut.isHungry());
		for (int i = 0; i <= mGenome.hunger - 1; i++) {
			cut.step();
		}
		assertFalse(cut.isHungry());
		cut.step();
		assertTrue(cut.isHungry());
	}

	@Test
	public void aferEatNoHunger() {
		assertFalse(cut.isHungry());
		for (int i = 0; i <= mGenome.hunger - 1; i++) {
			cut.step();
		}
		assertFalse(cut.isHungry());
		cut.step();
		assertTrue(cut.isHungry());

		Food lFood = new Food();
		lFood.setPosition(new Pos(20, 20));
		cut.eat(lFood);
		assertFalse(cut.isHungry());
	}

	@Test
	public void movesCorrect() {
		Pos lStartPos = cut.getPosition();

		assertEquals(cStartPosX, lStartPos.x);
		assertEquals(cStartPosY, lStartPos.y);
		assertHasGrowCellOnPosDiff(true, 3, 0, 0);

		cut.move(Directions.UP);

		Pos lActPos = cut.getPosition();
		assertEquals(cStartPosX, lActPos.x);
		assertEquals(cStartPosY - 1, lActPos.y);
		assertHasGrowCellOnPosDiff(false, 3, 0, 0);
		assertHasGrowCellOnPosDiff(true, 3, 0, -1);
	}

	@Test
	public void movesNotAtBorder() {
		cut.setPosition(new Pos(0, cStartPosY));
		Pos lStartPos = cut.getPosition();

		assertEquals(0, lStartPos.x);
		assertEquals(cStartPosY, lStartPos.y);
		assertHasCell(true, BlockType.LIFE, 0, 0, 0, cStartPosY);

		cut.move(Directions.LEFT);

		Pos lActPos = cut.getPosition();
		assertEquals(0, lActPos.x);
		assertEquals(cStartPosY, lActPos.y);
		assertHasCell(true, BlockType.LIFE, 0, 0, 0, cStartPosY);
		assertHasCell(false, BlockType.LIFE, 0, 0, -1, cStartPosY);
	}

	private void assertHasCell(Boolean pAssert, BlockType pType, int pCellX,
			int pCellY, int pPosX, int pPosY) {
		IdvmCell lCell = new IdvmCell(pType, new Pos(pCellX, pCellY));
		lCell.setPosition(new Pos(pPosX, pPosY));
		ArrayList<iBlock> lUsedBlocks = cut.getUsedBlocks();
		if (pAssert) {
			assertTrue(lUsedBlocks.contains(lCell));
		} else {
			assertFalse(lUsedBlocks.contains(lCell));
		}
	}
}
