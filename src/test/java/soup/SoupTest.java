package soup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import soup.block.BlockType;
import soup.block.iBlock;
import soup.idvm.iIdvm;
import utils.TestMock;
import datatypes.Direction;
import datatypes.Pos;
import globals.Constants;
import globals.exceptions.ExOutOfGrid;

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
		} catch (ExOutOfGrid e) {
			fail();
		}
	}

	@Test
	public void gridHasBlocks() throws ExOutOfGrid {
		int lCount = 0;
		for (int x = 0; x < Constants.soupSize; x++) {
			for (int y = 0; y < Constants.soupSize; y++) {
				iBlock lBlock = cut.getBlock(new Pos(x, y));
				if (lBlock != null)
					lCount++;
			}
		}
		assertEquals(Constants.foodSupply + Constants.enemySupply + 4, lCount);
	}

	@Test
	public void foodGetsNullAfterInteraction() throws ExOutOfGrid {
		for (Pos iPos : Pos.getAllGridPos()) {
			iBlock lBlock = cut.getBlock(iPos);
			if (lBlock == null || lBlock.getBlockType() != BlockType.FOOD)
				continue;
			mIdvm.setPosition(iPos);
			cut.step();
			mIdvm.setPosition(iPos.getPosFromDirection(Direction.RIGHT));
			cut.step();
			assertNull(cut.getBlock(iPos));
			return;
		}
		fail();
	}
}
