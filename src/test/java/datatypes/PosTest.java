package datatypes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.ExOutOfGrid;
import globals.Constants;

public class PosTest {
	private static final int cStartY = 20;
	private static final int cStartX = 10;
	Pos cut = new Pos(cStartX,cStartY);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void returnsUpPos() throws ExOutOfGrid {
		Pos lActPos = cut.getPosFromDirection(Direction.UP);
		assertEquals(cStartX, lActPos.x);
		assertEquals(cStartY-1, lActPos.y);
	}
	@Test
	public void returnsDownPos() throws ExOutOfGrid {
		Pos lActPos = cut.getPosFromDirection(Direction.DOWN);
		assertEquals(cStartX, lActPos.x);
		assertEquals(cStartY+1, lActPos.y);
	}
	@Test
	public void returnsLeftPos() throws ExOutOfGrid {
		Pos lActPos = cut.getPosFromDirection(Direction.LEFT);
		assertEquals(cStartX-1, lActPos.x);
		assertEquals(cStartY, lActPos.y);
	}
	@Test
	public void returnsRightPos() throws ExOutOfGrid {
		Pos lActPos = cut.getPosFromDirection(Direction.RIGHT);
		assertEquals(cStartX+1, lActPos.x);
		assertEquals(cStartY, lActPos.y);
	}
	@Test(expected = ExOutOfGrid.class)
	public void stopsAtBorderLeft() throws ExOutOfGrid{
		cut = new Pos(0,cStartY);
		cut.getPosFromDirection(Direction.LEFT);
	}
	@Test(expected = ExOutOfGrid.class)
	public void stopsAtBorderRight() throws ExOutOfGrid{
		cut = new Pos(Constants.soupSize-1,cStartY);
		Pos lActPos = cut.getPosFromDirection(Direction.RIGHT);
		assertEquals(cut.x, lActPos.x);
		assertEquals(cut.y, lActPos.y);
	}
	@Test(expected = ExOutOfGrid.class)
	public void stopsAtBorderUp() throws ExOutOfGrid{
		cut = new Pos(cStartX,0);
		Pos lActPos = cut.getPosFromDirection(Direction.UP);
		assertEquals(cut.x, lActPos.x);
		assertEquals(cut.y, lActPos.y);
	}
	@Test(expected = ExOutOfGrid.class)
	public void stopsAtBorderDown() throws ExOutOfGrid{
		cut = new Pos(cStartX,Constants.soupSize-1);
		Pos lActPos = cut.getPosFromDirection(Direction.DOWN);
		assertEquals(cut.x, lActPos.x);
		assertEquals(cut.y, lActPos.y);
	}
}
