package soup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import core.soup.Soup;
import core.soup.block.BlockType;
import core.soup.block.iBlock;
import core.soup.idvm.iIdvm;
import utils.TestMock;
import globals.Config;

public class SoupTest {
	Soup cut;
	private iIdvm mIdvm;

	@Before
	public void setUp() throws Exception {
		mIdvm = TestMock.getIdvmMock();
		cut = new Soup(mIdvm);
	}

	@Test
	public void midBlockIsCell() {
		try {
			Pos lMidPos = mIdvm.getPos();
			iBlock lMidBlock;
			lMidBlock = cut.getBlock(lMidPos);
			assertNotNull(lMidBlock);
			assertEquals(BlockType.LIFE, lMidBlock.getBlockType());
		} catch (PosIsOutOfGrid e) {
			fail();
		}
	}

	@Test
	public void gridHasBlocks() throws PosIsOutOfGrid {
		int lCount = 0;
		for (int x = 0; x < Config.soupSize; x++) {
			for (int y = 0; y < Config.soupSize; y++) {
				iBlock lBlock = cut.getBlock(new Pos(x, y));
				if (lBlock != null)
					lCount++;
			}
		}
		assertEquals(Config.foodSupply + Config.enemySupply + 4, lCount);
	}

	@Test
	public void foodGetsNullAfterInteraction() throws PosIsOutOfGrid {
		for (Pos iPos : Pos.getAllGridPos()) {
			iBlock lBlock = cut.getBlock(iPos);
			if (lBlock == null || lBlock.getBlockType() != BlockType.FOOD)
				continue;
			mIdvm.setPosition(iPos);
			cut.step();
			mIdvm.setPosition(iPos.getPosFromDirection(Decisions.RIGHT));
			cut.step();
			assertNull(cut.getBlock(iPos));
			return;
		}
		fail();
	}
}
