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
import core.soup.block.Partner;
import core.soup.block.iBlock;

public class IdvmSensorTest {
	IdvmSensor cut;
	private BlockGrid mBlockGrid;
	private ArrayList<iBlock> mSensorBlocks;
	private HashMap<Pos, Sensor> mDetectedPositions = new HashMap<Pos, Sensor>();
	private Pos mPosFood = new Pos(20, 10);
	private Pos mPosEnemy = new Pos(21, 11);
	private Pos mPosPartner = new Pos(22, 12);

	@Before
	public void setUp() throws Exception {
		mSensorBlocks = new ArrayList<iBlock>();
		mBlockGrid = new BlockGrid();

		mDetectedPositions.put(mPosFood, new Sensor());
		mDetectedPositions.put(mPosEnemy, new Sensor());
		mDetectedPositions.put(mPosPartner, new Sensor());
		mBlockGrid.setBlock(mPosPartner, new Partner());
		mBlockGrid.setBlock(mPosEnemy, new Enemy(mBlockGrid));
		mBlockGrid.setBlock(mPosFood, new Food());

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

	@Test
	public void getStateWhenPartnerFirst() {
		ArrayList<BlockType> lTargetDetectionOrder = new ArrayList<BlockType>();
		lTargetDetectionOrder.add(BlockType.PARTNER);
		lTargetDetectionOrder.add(BlockType.ENEMY);
		lTargetDetectionOrder.add(BlockType.FOOD);
		IdvmState lState = cut.getState(true, false, mDetectedPositions, lTargetDetectionOrder);
		assertEquals(IdvmState.PARTNER, lState);
	}

	@Test
	public void getStateWhenEnemyFirst() {
		ArrayList<BlockType> lTargetDetectionOrder = new ArrayList<BlockType>();
		lTargetDetectionOrder.add(BlockType.ENEMY);
		lTargetDetectionOrder.add(BlockType.FOOD);
		lTargetDetectionOrder.add(BlockType.PARTNER);
		IdvmState lState = cut.getState(true, false, mDetectedPositions, lTargetDetectionOrder);
		assertEquals(IdvmState.ENEMY, lState);
	}
	@Test
	public void getStateWhenFoodFirst() {
		ArrayList<BlockType> lTargetDetectionOrder = new ArrayList<BlockType>();
		lTargetDetectionOrder.add(BlockType.FOOD);
		lTargetDetectionOrder.add(BlockType.ENEMY);
		lTargetDetectionOrder.add(BlockType.PARTNER);
		IdvmState lState = cut.getState(true, false, mDetectedPositions, lTargetDetectionOrder);
		assertEquals(IdvmState.FOOD, lState);
	}

	@Test
	public void getStateWhenFoodSecond() throws PosIsOutOfGrid {
		ArrayList<BlockType> lTargetDetectionOrder = new ArrayList<BlockType>();
		lTargetDetectionOrder.add(BlockType.ENEMY);
		lTargetDetectionOrder.add(BlockType.FOOD);
		lTargetDetectionOrder.add(BlockType.PARTNER);
		mBlockGrid.setBlock(mPosEnemy, null);
		IdvmState lState = cut.getState(true, false, mDetectedPositions, lTargetDetectionOrder);
		assertEquals(IdvmState.FOOD, lState);
	}

	@Test
	public void getStateWhenHunger() {
		ArrayList<BlockType> lTargetDetectionOrder = new ArrayList<BlockType>();
		lTargetDetectionOrder.add(BlockType.ENEMY);
		lTargetDetectionOrder.add(BlockType.PARTNER);
		lTargetDetectionOrder.add(BlockType.FOOD);
		IdvmState lState = cut.getState(true, true, mDetectedPositions, lTargetDetectionOrder); 
		assertEquals(IdvmState.ENEMY_HUNGER, lState);
	}

	@Test
	public void getStateWhenBlind() {
		ArrayList<BlockType> lTargetDetectionOrder = new ArrayList<BlockType>();
		lTargetDetectionOrder.add(BlockType.PARTNER);
		lTargetDetectionOrder.add(BlockType.ENEMY);
		lTargetDetectionOrder.add(BlockType.FOOD);
		IdvmState lState = cut.getState(false, true, mDetectedPositions, lTargetDetectionOrder);
		assertEquals(IdvmState.BLIND, lState);
	}
}
