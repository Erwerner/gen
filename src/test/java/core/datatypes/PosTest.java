package core.datatypes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import core.datatypes.Direction;
import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import globals.Config;

public class PosTest {
	private static final int cStartY = 20;
	private static final int cStartX = 10;
	Pos cut = new Pos(cStartX, cStartY);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void returnsUpPos() throws PosIsOutOfGrid {
		Pos lActPos = cut.getPosFromDirection(Direction.NORTH);
		assertEquals(cStartX, lActPos.x);
		assertEquals(cStartY - 1, lActPos.y);
	}

	@Test
	public void returnsDownPos() throws PosIsOutOfGrid {
		Pos lActPos = cut.getPosFromDirection(Direction.SOUTH);
		assertEquals(cStartX, lActPos.x);
		assertEquals(cStartY + 1, lActPos.y);
	}

	@Test
	public void returnsLeftPos() throws PosIsOutOfGrid {
		Pos lActPos = cut.getPosFromDirection(Direction.WEST);
		assertEquals(cStartX - 1, lActPos.x);
		assertEquals(cStartY, lActPos.y);
	}

	@Test
	public void returnsRightPos() throws PosIsOutOfGrid {
		Pos lActPos = cut.getPosFromDirection(Direction.EAST);
		assertEquals(cStartX + 1, lActPos.x);
		assertEquals(cStartY, lActPos.y);
	}

	@Test(expected = PosIsOutOfGrid.class)
	public void stopsAtBorderLeft() throws PosIsOutOfGrid {
		cut = new Pos(0, cStartY);
		cut.getPosFromDirection(Direction.WEST);
	}

	@Test(expected = PosIsOutOfGrid.class)
	public void stopsAtBorderRight() throws PosIsOutOfGrid {
		cut = new Pos(Config.cSoupSize - 1, cStartY);
		Pos lActPos = cut.getPosFromDirection(Direction.EAST);
		assertEquals(cut.x, lActPos.x);
		assertEquals(cut.y, lActPos.y);
	}

	@Test(expected = PosIsOutOfGrid.class)
	public void stopsAtBorderUp() throws PosIsOutOfGrid {
		cut = new Pos(cStartX, 0);
		Pos lActPos = cut.getPosFromDirection(Direction.NORTH);
		assertEquals(cut.x, lActPos.x);
		assertEquals(cut.y, lActPos.y);
	}

	@Test(expected = PosIsOutOfGrid.class)
	public void stopsAtBorderDown() throws PosIsOutOfGrid {
		cut = new Pos(cStartX, Config.cSoupSize - 1);
		Pos lActPos = cut.getPosFromDirection(Direction.SOUTH);
		assertEquals(cut.x, lActPos.x);
		assertEquals(cut.y, lActPos.y);
	}
}
