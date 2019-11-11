package soup.idvm;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import datatypes.Pos;

import soup.Genome;
import soup.block.BlockType;
import soup.block.iBlock;

public class IdvmTest {
	Idvm cut;
	Genome mGenome;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mGenome = new Genome();
		mGenome.cell.add(new IdvmCell(BlockType.LIFE, new Pos(0, 0)));
		mGenome.cell.add(new IdvmCell(BlockType.SENSOR, new Pos(1, 0)));
		mGenome.cell.add(new IdvmCell(BlockType.MOVE, new Pos(0, 1)));
		mGenome.cell.add(new IdvmCell(BlockType.DEFENCE, new Pos(1, 1)));
		mGenome.cell.add(new IdvmCell(BlockType.MOVE, new Pos(0, 2)));
		mGenome.cell.add(new IdvmCell(BlockType.SENSOR, new Pos(1, 1)));

		cut = new Idvm(mGenome);
		cut.setPosition(new Pos(50, 50));
	}

	@Test
	public void initalCellsAreCorrect() {

		IdvmCell lC00 = new IdvmCell(BlockType.LIFE, new Pos(0, 0));
		IdvmCell lC10 = new IdvmCell(BlockType.SENSOR, new Pos(1, 0));
		IdvmCell lC01 = new IdvmCell(BlockType.MOVE, new Pos(0, 1));
		IdvmCell lC11 = new IdvmCell(BlockType.DEFENCE, new Pos(1, 1));
		IdvmCell lC02 = new IdvmCell(BlockType.MOVE, new Pos(0, 2));
		lC00.setPosition(new Pos(49,49));
		lC10.setPosition(new Pos(50,49));
		lC01.setPosition(new Pos(49,50));
		lC11.setPosition(new Pos(50,50));
		lC02.setPosition(new Pos(49,51));
		
		ArrayList<iBlock> lUsedBlocks = cut.getUsedBlocks();
		assertEquals(4, lUsedBlocks.size());
		
		assertTrue(lUsedBlocks.contains(lC00));
		assertTrue(lUsedBlocks.contains(lC10));
		assertTrue(lUsedBlocks.contains(lC01));
		assertTrue(lUsedBlocks.contains(lC11));
		assertFalse(lUsedBlocks.contains(lC02));
	}

	@Test
	public void growCellsAreCorrect() {
		cut.grow();
		IdvmCell lC02 = new IdvmCell(BlockType.MOVE, new Pos(0, 2));
		lC02.setPosition(new Pos(49,51));
		ArrayList<iBlock> lUsedBlocks = cut.getUsedBlocks();
		assertEquals(5, lUsedBlocks.size());
		assertTrue(lUsedBlocks.contains(lC02));
	}
	
	@Test
	public void growOverwritesInitialCell() {
		IdvmCell lC11 = new IdvmCell(BlockType.DEFENCE, new Pos(1, 1));
		lC11.setPosition(new Pos(50,50));
		ArrayList<iBlock> lUsedBlocks = cut.getUsedBlocks();
		assertEquals(4, lUsedBlocks.size());
		assertTrue(lUsedBlocks.contains(lC11));
		cut.grow();
		cut.grow();
		lC11 = new IdvmCell(BlockType.SENSOR, new Pos(1, 1));
		lC11.setPosition(new Pos(50,50));
		lUsedBlocks = cut.getUsedBlocks();
		assertEquals(5, lUsedBlocks.size());
		assertTrue(lUsedBlocks.contains(lC11));
	}

	@Test
	public void moveChangesCellPos() {
		cut.setPosition(new Pos(60,60));

		IdvmCell lC00 = new IdvmCell(BlockType.LIFE, new Pos(0, 0));
		lC00.setPosition(new Pos(59,59));
		
		ArrayList<iBlock> lUsedBlocks = cut.getUsedBlocks();
		assertTrue(lUsedBlocks.contains(lC00));
	}

	@Test
	public void killKills(){
		IdvmCell lC11 = new IdvmCell(BlockType.DEFENCE, new Pos(1, 1));
		lC11.setPosition(new Pos(50,50));
		ArrayList<iBlock> lUsedBlocks = cut.getUsedBlocks();
		assertTrue(lUsedBlocks.contains(lC11));
		
		cut.killCell(new Pos(50,50));

		assertFalse(lUsedBlocks.contains(lC11));
		lC11 = new IdvmCell(BlockType.NOTHING, new Pos(1, 1));
		lC11.setPosition(new Pos(50,50));
		lUsedBlocks = cut.getUsedBlocks();
		assertTrue(lUsedBlocks.contains(lC11));
	}
	
	@Test
	public void noLifeKills(){
		assertTrue(cut.isAlive());

		cut.killCell(new Pos(49,49));
		
		for(iBlock iCell : cut.getUsedBlocks()){
			if(iCell.getBlockType()==BlockType.LIFE){
				fail();
			}
		}
		
		assertFalse(cut.isAlive());
	}
}
