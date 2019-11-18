package soup.idvm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import genes.Genome;
import genes.MoveProbability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import soup.block.BlockGrid;
import soup.block.BlockType;
import soup.block.Enemy;
import soup.block.Food;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import datatypes.Direction;
import datatypes.Pos;
import exceptions.ExOutOfGrid;
import exceptions.ExWrongState;

public class IdvmTest {
	private static final int cStartPosX = 50;
	private static final int cStartPosY = 60;
	iIdvm cut;
	Genome mGenome;
	private IdvmCell[] mCellGrow;
	private iBlockGrid mBlockGrid;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mBlockGrid = new BlockGrid();
		mCellGrow = new IdvmCell[6];
		mCellGrow[0] = new IdvmCell(BlockType.LIFE, new Pos(0, 0));
		mCellGrow[1] = new IdvmCell(BlockType.SENSOR, new Pos(1, 0));
		mCellGrow[2] = new IdvmCell(BlockType.MOVE, new Pos(0, 1));
		mCellGrow[3] = new IdvmCell(BlockType.DEFENCE, new Pos(1, 1));
		mCellGrow[4] = new IdvmCell(BlockType.SENSOR, new Pos(0, 2));
		mCellGrow[5] = new IdvmCell(BlockType.SENSOR, new Pos(1, 1));

		mGenome = new Genome();
		for (IdvmCell iCell : mCellGrow) {
			mGenome.cellGrow.add(new IdvmCell(iCell.getBlockType(), iCell
					.getPosOnIdvm()));
		}
		ArrayList<MoveProbability> lIdlelMoveProbability = new ArrayList<MoveProbability>();
		lIdlelMoveProbability.add(new MoveProbability(0, 0, 1, 0, 0, 0));
		lIdlelMoveProbability.add(new MoveProbability(0, 1, 0, 0, 0, 0));
		mGenome.movementSequences.put(IdvmState.IDLE, lIdlelMoveProbability);
		mGenome.movementSequences.put(IdvmState.FOOD, lIdlelMoveProbability);

		mGenome.setHunger(50);

		cut = new Idvm(mGenome);
		cut.setBlockGrid(mBlockGrid);
		cut.setPosition(new Pos(cStartPosX, cStartPosY));
	}

	@Test
	public void initalCellsAreCorrect() {
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
		assertFalse(lUsedBlocks.contains(lC02));
		assertHasGrowCellOnPosDiff(false, 4, 0, 0);
	}

	@Test
	public void growCellsAreCorrect() {
		assertHasGrowCellOnPosDiff(false, 4, 0, 0);
		Food lFood = new Food();
		lFood.setPosition(new Pos(20, 20));
		cut.interactWithFood(lFood);

		ArrayList<iBlock> lUsedBlocks = cut.getUsedBlocks();
		assertEquals(5, lUsedBlocks.size());
		assertHasGrowCellOnPosDiff(true, 4, 0, 0);
	}

	@Test
	public void growOverwritesInitialCell() {
		ArrayList<iBlock> lUsedBlocks = cut.getUsedBlocks();
		assertEquals(4, lUsedBlocks.size());
		assertHasGrowCellOnPosDiff(true, 3, 0, 0);

		Food lFood = new Food();
		lFood.setPosition(new Pos(20, 20));
		cut.interactWithFood(lFood);
		cut.interactWithFood(lFood);

		lUsedBlocks = cut.getUsedBlocks();
		assertEquals(5, lUsedBlocks.size());
		assertHasGrowCellOnPosDiff(true, 4, 0, 0);
	}

	@Test
	public void moveChangesCellPos() {
		cut.setPosition(new Pos(60, 60));

		assertHasCell(true, BlockType.LIFE, 0, 0, 60, 60);
	}

	@Test
	public void killNothingsCell() {
		try {
			assertHasGrowCellOnPosDiff(true, 0, 0, 0);

			cut.killCell(new Pos(cStartPosX, cStartPosY));

			assertHasGrowCellOnPosDiff(false, 0, 0, 0);
			assertNull(mBlockGrid.getBlock(new Pos(cStartPosX, cStartPosY)));
		} catch (ExOutOfGrid e) {
			fail();
		}
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

		cut.killCell(getSoupPosFromGrowCell(0));

		for (iBlock iCell : cut.getUsedBlocks()) {
			if (iCell.getBlockType() == BlockType.LIFE) {
				fail();
			}
		}

		assertFalse(cut.isAlive());
	}

	@Test
	public void getsHungry() {
		/*
		mGenome.setHunger(Idvm.cMaxEnergy - 10);
		cut = new Idvm(mGenome);
		cut.setBlockGrid(mBlockGrid);
		cut.setPosition(new Pos(cStartPosX, cStartPosY));
		assertFalse(cut.isHungry());
		for (int i = 0; i <= Idvm.cMaxEnergy - mGenome.getHunger() - 1; i = i + 1) {
			cut.step();
			cut.step();
		}
		assertFalse(cut.isHungry());
		cut.step();
		cut.step();
		assertTrue(cut.isHungry());
		*/
	}

	@Test
	public void aferEatNoHunger() {
		/*
		mGenome.setHunger(Idvm.cMaxEnergy - 10);
		cut = new Idvm(mGenome);
		cut.setBlockGrid(mBlockGrid);
		cut.setPosition(new Pos(cStartPosX, cStartPosY));
		assertFalse(cut.isHungry());
		for (int i = 0; i <= Idvm.cMaxEnergy - mGenome.getHunger() - 1; i = i + 1) {
			cut.step();
			cut.step();
		}
		assertFalse(cut.isHungry());
		cut.step();
		cut.step();
		assertTrue(cut.isHungry());

		Food lFood = new Food();
		lFood.setPosition(new Pos(20, 20));
		cut.interactWithFood(lFood);
		assertFalse(cut.isHungry());
		*/
	}

	@Test
	public void movesCorrect() {
		Pos lStartPos = cut.getPos();

		assertEquals(cStartPosX, lStartPos.x);
		assertEquals(cStartPosY, lStartPos.y);
		assertHasGrowCellOnPosDiff(true, 3, 0, 0);

		cut.step();

		Pos lActPos = cut.getPos();
		assertPosition(cStartPosX - 1, cStartPosY, lActPos);
		assertHasGrowCellOnPosDiff(false, 3, 0, 0);
		assertHasGrowCellOnPosDiff(true, 3, -1, 0);
	}

	@Test
	public void movesNotAtBorder() {
		cut.setPosition(new Pos(0, cStartPosY));
		Pos lStartPos = cut.getPos();

		assertEquals(0, lStartPos.x);
		assertEquals(cStartPosY, lStartPos.y);
		assertHasCell(true, BlockType.LIFE, 0, 0, 0, cStartPosY);

		cut.step();

		Pos lActPos = cut.getPos();
		assertEquals(0, lActPos.x);
		assertEquals(cStartPosY, lActPos.y);
		assertHasCell(true, BlockType.LIFE, 0, 0, 0, cStartPosY);
		assertHasCell(false, BlockType.LIFE, 0, 0, -1, cStartPosY);
	}

	@Test
	public void enemyKillsCell() {
		try {
			int lIdxKillCell = 2;
			assertHasGrowCellOnPosDiff(true, lIdxKillCell, 0, 0);
			Enemy lEnemy = new Enemy();
			Pos lPos = getSoupPosFromGrowCell(lIdxKillCell);
			lEnemy.setPosition(lPos);
			cut.interactWithEnemy(lEnemy);

			assertHasGrowCellOnPosDiff(false, lIdxKillCell, 0, 0);
			// IdvmCell lGrowCell = mCellGrow[lIdxKillCell];
			Pos lSoupPos = getSoupPosFromGrowCell(lIdxKillCell);
			assertNull(mBlockGrid.getBlock(new Pos(lSoupPos.x, lSoupPos.y)));
		} catch (ExOutOfGrid e) {
			fail();
		}
	}

	private Pos getSoupPosFromGrowCell(int pIdx) {
		IdvmCell lCell = mCellGrow[pIdx];

		Pos lPos = new Pos(lCell.getPosOnIdvm().x + cStartPosX,
				lCell.getPosOnIdvm().y + cStartPosY);
		return lPos;
	}

	@Test
	public void stepMovesCorrect() {
		assertPosition(cStartPosX, cStartPosY, cut.getPos());
		cut.step();
		assertPosition(cStartPosX - 1, cStartPosY, cut.getPos());
	}

	private void assertPosition(int pExpX, int pExpY, Pos pPos) {
		assertEquals(pExpX, pPos.x);
		assertEquals(pExpY, pPos.y);
	}

	private void assertHasCell(Boolean pAssert, BlockType pType, int pCellX,
			int pCellY, int pPosX, int pPosY) {
		IdvmCell lCell = new IdvmCell(pType, new Pos(pCellX, pCellY));
		lCell.setPosition(new Pos(pPosX, pPosY));
		ArrayList<iBlock> lUsedBlocks = cut.getUsedBlocks();
		try {
			if (pAssert) {
				if (!lUsedBlocks.contains(lCell))
					throw new Exception();
			} else {
				if (lUsedBlocks.contains(lCell))
					throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Searched " + pAssert.toString() + " " + lCell
					+ " in:");
			System.out.println(lUsedBlocks);
			fail();
		}
	}

	@Test
	public void eatChangesDirection() {
		assertPosition(cStartPosX, cStartPosY, cut.getPos());
		cut.step();
		cut.step();
		assertPosition(cStartPosX - 1, cStartPosY, cut.getPos());
		cut.interactWithFood(new Food());
		cut.step();
		cut.step();
		assertPosition(cStartPosX - 1, cStartPosY + 1, cut.getPos());
	}

	@Test
	public void poppingNeverChangesGenome() {
		assertPosition(cStartPosX, cStartPosY, cut.getPos());
		cut.step();
		cut.step();
		assertPosition(cStartPosX - 1, cStartPosY, cut.getPos());
		cut.interactWithFood(new Food());
		cut.step();
		cut.step();
		assertPosition(cStartPosX - 1, cStartPosY + 1, cut.getPos());
		assertEquals(2, mGenome.movementSequences.get(IdvmState.IDLE).size());
	}

	@Test
	public void returnsDetectedBlocksForSensor() {
		assertHasCell(true, BlockType.SENSOR, 1, 0, cStartPosX + 1, cStartPosY);
		HashMap<Pos, Sensor> lActDetectPos = cut.getDetectedPos();
		assertNotEquals(0, lActDetectPos.size());
		ArrayList<Pos> lActPos = new ArrayList<Pos>();
		for (Entry<Pos, Sensor> iPos : lActDetectPos.entrySet()) {
			lActPos.add(iPos.getKey());
		}
		assertTrue(lActPos.contains(new Pos(cStartPosX + 1 - 1, cStartPosY)));
		assertTrue(lActPos.contains(new Pos(cStartPosX + 1 + 0, cStartPosY)));
		assertTrue(lActPos.contains(new Pos(cStartPosX + 1 + 1, cStartPosY)));
		assertTrue(lActPos.contains(new Pos(cStartPosX + 1, cStartPosY - 1)));
		assertTrue(lActPos.contains(new Pos(cStartPosX + 1, cStartPosY + 0)));
		assertTrue(lActPos.contains(new Pos(cStartPosX + 1, cStartPosY + 1)));
	}

	@Test
	public void stateIsIDLE() {
		assertEquals(IdvmState.IDLE, cut.getState());
	}

	@Test
	public void stateIsFOODWhenFoodIsDetected() throws ExOutOfGrid {
		assertHasCell(true, BlockType.SENSOR, 1, 0, cStartPosX + 1, cStartPosY);
		mBlockGrid
				.setBlock(new Pos(cStartPosX + 2, cStartPosY - 1), new Food());
		assertEquals(IdvmState.FOOD, cut.getState());
	}

	@Test(expected = ExWrongState.class)
	public void targetIsNull() {
		assertNull(cut.getTargetDirection());
	}

	@Test
	public void targetIsRight() throws ExOutOfGrid {
		assertHasCell(true, BlockType.SENSOR, 1, 0, cStartPosX + 1, cStartPosY);
		mBlockGrid.setBlock(new Pos(cStartPosX + 2, cStartPosY), new Food());
		assertEquals(IdvmState.FOOD, cut.getState());
		assertEquals(Direction.RIGHT, cut.getTargetDirection());
	}

	@Test
	public void eatNeverThrows() {
		for (int i = 0; i < 100; i++)
			cut.interactWithFood(new Food());
	}

	@Test
	public void initialPosIsCorrect() {
		Pos lPos = cut.getPos();
		assertEquals(new Pos(cStartPosX, cStartPosY), lPos);
	}
}
