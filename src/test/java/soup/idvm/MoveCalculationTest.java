package soup.idvm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import soup.block.BlockGrid;
import soup.block.BlockType;
import soup.block.Enemy;
import soup.block.Food;
import soup.block.iBlock;
import soup.block.iBlockGrid;
import utils.TestMock;
import datatypes.Direction;
import datatypes.Pos;
import exceptions.ExOutOfGrid;
import genes.MoveProbability;
import globals.Constants;

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
		cut.calcPosFromDirection(Direction.UP, lStartPos, mIdvmBlocks);
	}

	@Test(expected = ExOutOfGrid.class)
	public void noMovementAtBottomBorder() throws ExOutOfGrid {
		Pos lStartPos = getIdvmPos(Constants.soupSize / 2,
				Constants.soupSize - 1);
		cut.calcPosFromDirection(Direction.DOWN, lStartPos, mIdvmBlocks);
	}

	@Test
	public void calculateDirectionCurrent() {
		HashMap<IdvmState, ArrayList<MoveProbability>> lMovementSequences = new HashMap<IdvmState, ArrayList<MoveProbability>>();
		ArrayList<MoveProbability> lMovementList = new ArrayList<MoveProbability>();
		ArrayList<Direction> lMovements = new ArrayList<Direction>();
		lMovements.add(Direction.CURRENT);
		lMovementList.add(new MoveProbability(lMovements));
		lMovementSequences.put(IdvmState.IDLE, lMovementList);
		Direction lAct = cut.calcMovingDirection(lMovementSequences,
				IdvmState.IDLE, Direction.RIGHT);
		assertEquals(Direction.UP, lAct);
	}

	@Test
	public void calculateDirectionTarget() {
		HashMap<IdvmState, ArrayList<MoveProbability>> lMovementSequences = new HashMap<IdvmState, ArrayList<MoveProbability>>();
		ArrayList<MoveProbability> lMovementList = new ArrayList<MoveProbability>();
		ArrayList<Direction> lMovements = new ArrayList<Direction>();
		lMovements.add(Direction.TARGET);
		lMovementList.add(new MoveProbability(lMovements));
		lMovementSequences.put(IdvmState.IDLE, lMovementList);
		Direction lAct = cut.calcMovingDirection(lMovementSequences,
				IdvmState.IDLE, Direction.RIGHT);
		assertEquals(Direction.RIGHT, lAct);
	}

	@Test
	public void calculateDirectionTargetOpposite() {
		HashMap<IdvmState, ArrayList<MoveProbability>> lMovementSequences = new HashMap<IdvmState, ArrayList<MoveProbability>>();
		ArrayList<MoveProbability> lMovementList = new ArrayList<MoveProbability>();
		ArrayList<Direction> lMovements = new ArrayList<Direction>();
		lMovements.add(Direction.TARGET_OPPOSITE);
		lMovementList.add(new MoveProbability(lMovements));
		lMovementSequences.put(IdvmState.IDLE, lMovementList);
		Direction lAct = cut.calcMovingDirection(lMovementSequences,
				IdvmState.IDLE, Direction.RIGHT);
		assertEquals(Direction.LEFT, lAct);
	}

	@Test
	public void calculateDirectionTargetSite2() {
		HashMap<IdvmState, ArrayList<MoveProbability>> lMovementSequences = new HashMap<IdvmState, ArrayList<MoveProbability>>();
		ArrayList<MoveProbability> lMovementList = new ArrayList<MoveProbability>();
		ArrayList<Direction> lMovements = new ArrayList<Direction>();
		lMovements.add(Direction.TARGET_SITE2);
		lMovementList.add(new MoveProbability(lMovements));
		lMovementSequences.put(IdvmState.IDLE, lMovementList);
		Direction lAct = cut.calcMovingDirection(lMovementSequences,
				IdvmState.IDLE, Direction.RIGHT);
		assertEquals(Direction.DOWN, lAct);
	}

	@Test
	public void calculateDirectionCurrentSite1() {
		HashMap<IdvmState, ArrayList<MoveProbability>> lMovementSequences = new HashMap<IdvmState, ArrayList<MoveProbability>>();
		ArrayList<MoveProbability> lMovementList = new ArrayList<MoveProbability>();
		ArrayList<Direction> lMovements = new ArrayList<Direction>();
		lMovements.add(Direction.CURRENT_SITE1);
		lMovementList.add(new MoveProbability(lMovements));
		lMovementSequences.put(IdvmState.IDLE, lMovementList);
		Direction lAct = cut.calcMovingDirection(lMovementSequences,
				IdvmState.IDLE, Direction.RIGHT);
		assertEquals(Direction.LEFT, lAct);
	}

	@Test
	public void idvmMovesToFood() throws ExOutOfGrid {
		Pos lFoodPos = new Pos(10, 10);
		mBlockGrid.setBlock(lFoodPos, new Food());

		HashMap<IdvmState, ArrayList<MoveProbability>> lMovementSequences = new HashMap<IdvmState, ArrayList<MoveProbability>>();
		ArrayList<MoveProbability> lMoveList = new ArrayList<MoveProbability>();
		lMoveList.add(new MoveProbability(0, 0, 0, 0, 0, 0).setDirection(
				Direction.TARGET, 1));
		lMovementSequences.put(IdvmState.FOOD, lMoveList);
		iIdvm lIdvm = TestMock.getIdvmMock();

		lIdvm.setBlockGrid(mBlockGrid);
		Pos lIdvmPos = lFoodPos.getPosFromDirection(Direction.LEFT)
				.getPosFromDirection(Direction.LEFT);
		lIdvm.setPosition(lIdvmPos);

		Pos lAct = cut.getMovingPosition(lIdvm, lMovementSequences);

		assertEquals(lIdvmPos.getPosFromDirection(Direction.RIGHT), lAct);
	}

	@Test
	public void idvmMovesAwayFromEnemy() throws ExOutOfGrid {
		Pos lEnemyPos = new Pos(10, 10);
		mBlockGrid.setBlock(lEnemyPos, new Enemy());

		HashMap<IdvmState, ArrayList<MoveProbability>> lMovementSequences = new HashMap<IdvmState, ArrayList<MoveProbability>>();
		ArrayList<MoveProbability> lMoveList = new ArrayList<MoveProbability>();
		lMoveList.add(new MoveProbability(0, 0, 0, 0, 0, 0).setDirection(
				Direction.TARGET_OPPOSITE, 1));
		lMovementSequences.put(IdvmState.ENEMY, lMoveList);
		iIdvm lIdvm = TestMock.getIdvmMock();

		lIdvm.setBlockGrid(mBlockGrid);
		Pos lIdvmPos = lEnemyPos.getPosFromDirection(Direction.LEFT)
				.getPosFromDirection(Direction.LEFT);
		lIdvm.setPosition(lIdvmPos);

		Pos lAct = cut.getMovingPosition(lIdvm, lMovementSequences);

		assertEquals(lIdvmPos.getPosFromDirection(Direction.LEFT), lAct);
	}
}
