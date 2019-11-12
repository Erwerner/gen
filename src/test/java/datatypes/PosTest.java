package datatypes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
	public void returnsUpPos() {
		Pos lActPos = cut.getPosFromDirection(Directions.UP);
		assertEquals(cStartX, lActPos.x);
		assertEquals(cStartY-1, lActPos.y);
	}
	@Test
	public void returnsDownPos() {
		Pos lActPos = cut.getPosFromDirection(Directions.DOWN);
		assertEquals(cStartX, lActPos.x);
		assertEquals(cStartY+1, lActPos.y);
	}
	@Test
	public void returnsLeftPos() {
		Pos lActPos = cut.getPosFromDirection(Directions.LEFT);
		assertEquals(cStartX-1, lActPos.x);
		assertEquals(cStartY, lActPos.y);
	}
	@Test
	public void returnsRightPos() {
		Pos lActPos = cut.getPosFromDirection(Directions.RIGHT);
		assertEquals(cStartX+1, lActPos.x);
		assertEquals(cStartY, lActPos.y);
	}
	@Test
	public void stopsAtBorderLeft(){
		cut = new Pos(0,cStartY);
		Pos lActPos = cut.getPosFromDirection(Directions.LEFT);
		assertEquals(cut.x, lActPos.x);
		assertEquals(cut.y, lActPos.y);
	}
	@Test
	public void stopsAtBorderRight(){
		cut = new Pos(Constants.soupSize-1,cStartY);
		Pos lActPos = cut.getPosFromDirection(Directions.RIGHT);
		assertEquals(cut.x, lActPos.x);
		assertEquals(cut.y, lActPos.y);
	}
	@Test
	public void stopsAtBorderUp(){
		cut = new Pos(cStartX,0);
		Pos lActPos = cut.getPosFromDirection(Directions.UP);
		assertEquals(cut.x, lActPos.x);
		assertEquals(cut.y, lActPos.y);
	}
	@Test
	public void stopsAtBorderDown(){
		cut = new Pos(cStartX,Constants.soupSize-1);
		Pos lActPos = cut.getPosFromDirection(Directions.DOWN);
		assertEquals(cut.x, lActPos.x);
		assertEquals(cut.y, lActPos.y);
	}
}
