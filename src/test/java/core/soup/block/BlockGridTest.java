package soup.block;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import core.soup.block.BlockGrid;
import core.soup.block.BlockType;
import core.soup.block.Food;
import core.soup.block.iBlock;
import core.soup.idvm.iIdvm;
import utils.TestMock;

public class BlockGridTest {
	BlockGrid cut;

	@Before
	public void setUp() throws Exception {
		cut = new BlockGrid();
	}

	@Test
	public void initialGridHasNoBlocks() throws PosIsOutOfGrid {
		for (Pos iPos : Pos.getAllGridPos()) {
			if (cut.getBlock(iPos) != null)
				fail();
		}
	}

	@Test
	public void setRandomSetsBlock() throws PosIsOutOfGrid {
		cut.setRandomBlock(new Food());
		for (Pos iPos : Pos.getAllGridPos()) {
			if (cut.getBlock(iPos) != null)
				return;
		}
		fail();
	}

	@Test
	public void setRandomSetsTwoBlock() throws PosIsOutOfGrid {
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
	public void initIdvmSetsFourBlocks() throws PosIsOutOfGrid {
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
	public void idvmInitAtMidPos() throws PosIsOutOfGrid {
		iIdvm lIdvm = TestMock.getIdvmMock();
		cut.addInitialIdvm(lIdvm);
		Pos lMidPos = lIdvm.getPos();
		iBlock lMidBlock = cut.getBlock(lMidPos);
		assertEquals(BlockType.LIFE, lMidBlock.getBlockType());
	}
	
	@Test
	public void refreshBlockSetsBlockPosition() {
		Pos lExpPos = new Pos(10,10);
		Food lBlock = new Food();
		cut.setRandomBlock(lBlock);
		assertNotEquals(lExpPos, lBlock.getPos());
		lBlock.setPosition(lExpPos);
		assertEquals(lExpPos, lBlock.getPos());
	}
}
