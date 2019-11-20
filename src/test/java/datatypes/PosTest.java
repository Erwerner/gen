package datatypes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import globals.Config;

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
	public void returnsUpPos() throws PosIsOutOfGrid {
		Pos lActPos = cut.getPosFromDirection(Decisions.UP);
		assertEquals(cStartX, lActPos.x);
		assertEquals(cStartY-1, lActPos.y);
	}
	@Test
	public void returnsDownPos() throws PosIsOutOfGrid {
		Pos lActPos = cut.getPosFromDirection(Decisions.DOWN);
		assertEquals(cStartX, lActPos.x);
		assertEquals(cStartY+1, lActPos.y);
	}
	@Test
	public void returnsLeftPos() throws PosIsOutOfGrid {
		Pos lActPos = cut.getPosFromDirection(Decisions.LEFT);
		assertEquals(cStartX-1, lActPos.x);
		assertEquals(cStartY, lActPos.y);
	}
	@Test
	public void returnsRightPos() throws PosIsOutOfGrid {
		Pos lActPos = cut.getPosFromDirection(Decisions.RIGHT);
		assertEquals(cStartX+1, lActPos.x);
		assertEquals(cStartY, lActPos.y);
	}
	@Test(expected = PosIsOutOfGrid.class)
	public void stopsAtBorderLeft() throws PosIsOutOfGrid{
		cut = new Pos(0,cStartY);
		cut.getPosFromDirection(Decisions.LEFT);
	}
	@Test(expected = PosIsOutOfGrid.class)
	public void stopsAtBorderRight() throws PosIsOutOfGrid{
		cut = new Pos(Config.soupSize-1,cStartY);
		Pos lActPos = cut.getPosFromDirection(Decisions.RIGHT);
		assertEquals(cut.x, lActPos.x);
		assertEquals(cut.y, lActPos.y);
	}
	@Test(expected = PosIsOutOfGrid.class)
	public void stopsAtBorderUp() throws PosIsOutOfGrid{
		cut = new Pos(cStartX,0);
		Pos lActPos = cut.getPosFromDirection(Decisions.UP);
		assertEquals(cut.x, lActPos.x);
		assertEquals(cut.y, lActPos.y);
	}
	@Test(expected = PosIsOutOfGrid.class)
	public void stopsAtBorderDown() throws PosIsOutOfGrid{
		cut = new Pos(cStartX,Config.soupSize-1);
		Pos lActPos = cut.getPosFromDirection(Decisions.DOWN);
		assertEquals(cut.x, lActPos.x);
		assertEquals(cut.y, lActPos.y);
	}
}
