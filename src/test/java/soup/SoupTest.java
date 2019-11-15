package soup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import datatypes.Constants;
import datatypes.Pos;
import soup.block.BlockType;
import soup.block.iBlock;
import soup.idvm.iIdvm;
import utils.TestMock;

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
		Pos lMidPos = mIdvm.getPos();
		System.out.println(lMidPos);
		iBlock lMidBlock = cut.getBlock(lMidPos);
		assertNotNull(lMidBlock);
		assertEquals(BlockType.LIFE, lMidBlock.getBlockType());
	}

	@Test
	public void gridHasBlocks() {
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

}
