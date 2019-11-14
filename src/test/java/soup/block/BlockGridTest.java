package soup.block;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import soup.idvm.iIdvm;
import utils.TestMock;

import datatypes.Pos;

public class BlockGridTest {
	BlockGrid cut;

	@Before
	public void setUp() throws Exception {
		cut = new BlockGrid();
	}

	@Test
	public void initialGridHasNoBlocks() {
		for (Pos iPos : Pos.getAllGridPos()) {
			if (cut.getBlock(iPos) != null)
				fail();
		}
	}

	@Test
	public void setRandomSetsBlock() {
		cut.setRandomBlock(new Food());
		for (Pos iPos : Pos.getAllGridPos()) {
			if (cut.getBlock(iPos) != null)
				return;
		}
		fail();
	}

	@Test
	public void setRandomSetsTwoBlock() {
		cut.setRandomBlock(new Food());
		cut.setRandomBlock(new Food());
		int lCount = 0;
		for (Pos iPos : Pos.getAllGridPos()) {
			if (cut.getBlock(iPos) != null)
				lCount++;
		}
		assertEquals(2, lCount);
	}

	@Test
	public void initIdvmSetsFourBlocks() {
		iIdvm lIdvm = TestMock.getIdvmMock();
		cut.addInitialIdvm(lIdvm);
		int lCount = 0;
		for (Pos iPos : Pos.getAllGridPos()) {
			if (cut.getBlock(iPos) != null)
				lCount++;
		}
		assertEquals(4, lCount);	
	}
	
	@Test
	public void idvmInitAtMidPos() {
		iIdvm lIdvm = TestMock.getIdvmMock();
		cut.addInitialIdvm(lIdvm);
		Pos lMidPos = lIdvm.getPos();
		iBlock lMidBlock = cut.getBlock(lMidPos);
		assertEquals(BlockType.LIFE,lMidBlock.getBlockType());
	}
}
