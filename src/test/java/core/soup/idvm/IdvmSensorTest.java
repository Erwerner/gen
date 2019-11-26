package core.soup.idvm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import core.datatypes.Direction;
import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import core.soup.block.BlockGrid;
import core.soup.block.BlockType;
import core.soup.block.Enemy;
import core.soup.block.Food;
import core.soup.block.IdvmCell;
import core.soup.block.iBlock;

public class IdvmSensorTest {
	IdvmSensor cut;
	private BlockGrid mBlockGrid;
	private ArrayList<iBlock> mSensorBlocks;

	@Before
	public void setUp() throws Exception {
		mSensorBlocks = new ArrayList<iBlock>();
		mBlockGrid = new BlockGrid();
		cut = new IdvmSensor(mBlockGrid);

		IdvmCell lSensor = new IdvmCell(BlockType.SENSOR, new Pos(0, 0));
		lSensor.setPosition(new Pos(10, 10));
		mSensorBlocks.add(lSensor);
	}

	@Test
	public void returnsNoTargetInStateIdle() {
		Direction lAct = cut.getTargetDirection(IdvmState.IDLE, mSensorBlocks);
		assertNull(lAct);
	}

	@Test
	public void returnsFoodTarget() throws PosIsOutOfGrid {
		Food lFood = new Food();
		lFood.setPosition(new Pos(10, 11));
		mBlockGrid.setBlock(new Pos(10, 11), lFood);
		Direction lAct = cut.getTargetDirection(IdvmState.FOOD, mSensorBlocks);
		Direction lExp = Direction.SOUTH;
		assertEquals(lExp, lAct);
	}

	@Test
	public void returnsTargetForEnemyState() throws PosIsOutOfGrid {
		Food lFood = new Food();
		lFood.setPosition(new Pos(10, 11));
		mBlockGrid.setBlock(new Pos(10, 11), lFood);
		Enemy lEnemy = new Enemy(mBlockGrid);
		lEnemy.setPosition(new Pos(10, 9));
		mBlockGrid.setBlock(new Pos(10, 9), lEnemy);
		Direction lAct = cut.getTargetDirection(IdvmState.ENEMY, mSensorBlocks);
		Direction lExp = Direction.NORTH;
		assertEquals(lExp, lAct);
	}

	@Test
	public void detectsSurroundingEnemy() throws PosIsOutOfGrid {
		Enemy lEnemy = new Enemy(mBlockGrid);
		lEnemy.setPosition(new Pos(10, 9));
		mBlockGrid.setBlock(new Pos(10, 9), lEnemy);
		HashMap<Pos, Sensor> lSensors = new HashMap<Pos, Sensor>();
		lSensors.put(new Pos(10, 9), new Sensor());
		assertTrue(cut.detectSurroundingBlockType(BlockType.ENEMY, lSensors));
	}

	@Test
	public void detectsCorrectBlockType() throws PosIsOutOfGrid {
		Enemy lEnemy = new Enemy(mBlockGrid);
		lEnemy.setPosition(new Pos(10, 9));
		mBlockGrid.setBlock(new Pos(10, 9), lEnemy);
		HashMap<Pos, Sensor> lSensors = new HashMap<Pos, Sensor>();
		lSensors.put(new Pos(10, 9), new Sensor());
		assertTrue(cut.detectSurroundingBlockType(BlockType.ENEMY, lSensors));
		assertFalse(cut.detectSurroundingBlockType(BlockType.FOOD, lSensors));
	}

}
