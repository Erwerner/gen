package core.soup.idvm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import core.datatypes.Decisions;
import core.datatypes.Direction;
import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import core.genes.MoveDecisionsProbability;
import core.soup.block.BlockGrid;
import core.soup.block.BlockType;
import core.soup.block.Enemy;
import core.soup.block.Food;
import core.soup.block.IdvmCell;
import core.soup.block.iBlock;
import core.soup.idvm.IdvmMoveCalculation;
import core.soup.idvm.IdvmState;
import core.soup.idvm.iIdvm;
import globals.Config;
import utils.TestMock;

public class MoveCalculationTest {
	IdvmMoveCalculation cut;
	BlockGrid mBlockGrid = new BlockGrid();
	private ArrayList<iBlock> mIdvmBlocks = new ArrayList<iBlock>();

	@Before
	public void setUp() throws Exception {
		mIdvmBlocks.add(new IdvmCell(BlockType.LIFE, new Pos(0, 0)));
		mIdvmBlocks.add(new IdvmCell(BlockType.LIFE, new Pos(0, 1)));
		mIdvmBlocks.add(new IdvmCell(BlockType.LIFE, new Pos(1, 0)));
		mIdvmBlocks.add(new IdvmCell(BlockType.LIFE, new Pos(1, 1)));

		cut = new IdvmMoveCalculation(mBlockGrid);
	}

	@Test
	public void returnsMovePos() throws PosIsOutOfGrid {
		Pos lMovePos = cut.calcPosFromDirection(Direction.NORTH, getIdvmPos(Config.soupSize / 2, Config.soupSize / 2),
				mIdvmBlocks);
		assertNotNull(lMovePos);
	}

	@Test
	public void returnsPosUP() throws PosIsOutOfGrid {
		Pos lMovePos = cut.calcPosFromDirection(Direction.NORTH, getIdvmPos(Config.soupSize / 2, Config.soupSize / 2),
				mIdvmBlocks);
		assertEquals(new Pos(Config.soupSize / 2, Config.soupSize / 2 - 1), lMovePos);
	}

	private Pos getIdvmPos(int pX, int pY) {
		Pos lNewPos = new Pos(pX, pY);
		for (iBlock iBlock : mIdvmBlocks) {
			IdvmCell lCell = (IdvmCell) iBlock;
			lCell.setPosition(lNewPos);
		}
		return lNewPos;
	}

	@Test(expected = PosIsOutOfGrid.class)
	public void noMovementAtTopBorder() throws PosIsOutOfGrid {
		Pos lStartPos = getIdvmPos(Config.soupSize / 2, 0);
		cut.calcPosFromDirection(Direction.NORTH, lStartPos, mIdvmBlocks);
	}

	@Test(expected = PosIsOutOfGrid.class)
	public void noMovementAtBottomBorder() throws PosIsOutOfGrid {
		Pos lStartPos = getIdvmPos(Config.soupSize / 2, Config.soupSize - 1);
		cut.calcPosFromDirection(Direction.SOUTH, lStartPos, mIdvmBlocks);
	}

	@Test
	public void calculateDirectionCurrent() {
		HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> lMovementSequences = new HashMap<IdvmState, ArrayList<MoveDecisionsProbability>>();
		ArrayList<MoveDecisionsProbability> lMovementList = new ArrayList<MoveDecisionsProbability>();
		lMovementList.add(new MoveDecisionsProbability().appendDecision(Decisions.CURRENT, 1));
		lMovementSequences.put(IdvmState.IDLE, lMovementList);
		Direction lAct = cut.calcMovingDirection(lMovementSequences, IdvmState.IDLE, Direction.EAST);
		assertEquals(Direction.NORTH, lAct);
	}

	@Test
	public void calculateDirectionTarget() {
		HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> lMovementSequences = new HashMap<IdvmState, ArrayList<MoveDecisionsProbability>>();
		ArrayList<MoveDecisionsProbability> lMovementList = new ArrayList<MoveDecisionsProbability>();
		lMovementList.add(new MoveDecisionsProbability().appendDecision(Decisions.TARGET, 1));
		lMovementSequences.put(IdvmState.IDLE, lMovementList);
		Direction lAct = cut.calcMovingDirection(lMovementSequences, IdvmState.IDLE, Direction.EAST);
		assertEquals(Direction.EAST, lAct);
	}

	@Test
	public void calculateDirectionTargetOpposite() {
		HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> lMovementSequences = new HashMap<IdvmState, ArrayList<MoveDecisionsProbability>>();
		ArrayList<MoveDecisionsProbability> lMovementList = new ArrayList<MoveDecisionsProbability>();
		lMovementList.add(new MoveDecisionsProbability().appendDecision(Decisions.TARGET_OPPOSITE, 1));
		lMovementSequences.put(IdvmState.IDLE, lMovementList);
		Direction lAct = cut.calcMovingDirection(lMovementSequences, IdvmState.IDLE, Direction.EAST);
		assertEquals(Direction.WEST, lAct);
	}

	@Test
	public void calculateDirectionTargetSite2() {
		HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> lMovementSequences = new HashMap<IdvmState, ArrayList<MoveDecisionsProbability>>();
		ArrayList<MoveDecisionsProbability> lMovementList = new ArrayList<MoveDecisionsProbability>();
		lMovementList.add(new MoveDecisionsProbability().appendDecision(Decisions.TARGET_SITE2, 1));
		lMovementSequences.put(IdvmState.IDLE, lMovementList);
		Direction lAct = cut.calcMovingDirection(lMovementSequences, IdvmState.IDLE, Direction.EAST);
		assertEquals(Direction.SOUTH, lAct);
	}

	@Test
	public void calculateDirectionCurrentSite1() {
		HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> lMovementSequences = new HashMap<IdvmState, ArrayList<MoveDecisionsProbability>>();
		ArrayList<MoveDecisionsProbability> lMovementList = new ArrayList<MoveDecisionsProbability>();
		lMovementList.add(new MoveDecisionsProbability().appendDecision(Decisions.CURRENT_SITE1, 1));
		lMovementSequences.put(IdvmState.IDLE, lMovementList);
		Direction lAct = cut.calcMovingDirection(lMovementSequences, IdvmState.IDLE, Direction.EAST);
		assertEquals(Direction.WEST, lAct);
	}

	@Test
	public void idvmMovesToFood() throws PosIsOutOfGrid {
		Pos lFoodPos = new Pos(10, 10);
		mBlockGrid.setBlock(lFoodPos, new Food());

		HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> lMovementSequences = new HashMap<IdvmState, ArrayList<MoveDecisionsProbability>>();
		ArrayList<MoveDecisionsProbability> lMoveList = new ArrayList<MoveDecisionsProbability>();
		lMoveList.add(new MoveDecisionsProbability().appendDecision(Decisions.TARGET, 1));
		lMovementSequences.put(IdvmState.FOOD, lMoveList);
		iIdvm lIdvm = TestMock.getIdvmMock();

		lIdvm.setBlockGrid(mBlockGrid);
		Pos lIdvmPos = lFoodPos.getPosFromDirection(Direction.WEST).getPosFromDirection(Direction.WEST);
		lIdvm.setPosition(lIdvmPos);

		Pos lAct = cut.getMovingPosition(lIdvm, lMovementSequences, Direction.EAST);

		assertEquals(lIdvmPos.getPosFromDirection(Direction.EAST), lAct);
	}

	@Test
	public void idvmMovesAwayFromEnemy() throws PosIsOutOfGrid {
		Pos lEnemyPos = new Pos(10, 10);
		mBlockGrid.setBlock(lEnemyPos, new Enemy(mBlockGrid));

		HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> lMovementSequences = new HashMap<IdvmState, ArrayList<MoveDecisionsProbability>>();
		ArrayList<MoveDecisionsProbability> lMoveList = new ArrayList<MoveDecisionsProbability>();
		lMoveList.add(new MoveDecisionsProbability().appendDecision(Decisions.TARGET_OPPOSITE, 1));
		lMovementSequences.put(IdvmState.ENEMY, lMoveList);
		iIdvm lIdvm = TestMock.getIdvmMock();

		lIdvm.setBlockGrid(mBlockGrid);
		Pos lIdvmPos = lEnemyPos.getPosFromDirection(Direction.WEST).getPosFromDirection(Direction.WEST);
		lIdvm.setPosition(lIdvmPos);

		Pos lAct = cut.getMovingPosition(lIdvm, lMovementSequences, Direction.EAST);

		assertEquals(lIdvmPos.getPosFromDirection(Direction.WEST), lAct);
	}
	
	@Test
	public void returnsLastCalculatedDecision() {
		HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> lMovementSequences = new HashMap<IdvmState, ArrayList<MoveDecisionsProbability>>();
		ArrayList<MoveDecisionsProbability> lMoveList = new ArrayList<MoveDecisionsProbability>();
		lMoveList.add(new MoveDecisionsProbability().appendDecision(Decisions.TARGET, 1));
		lMovementSequences.put(IdvmState.FOOD, lMoveList);
		cut.calcMovingDirection(lMovementSequences, IdvmState.FOOD, Direction.WEST);
		Decisions lAct = cut.getCalculatedDecision();
		assertEquals(Decisions.TARGET, lAct);
	}
}
