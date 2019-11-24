package soup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import core.datatypes.Direction;
import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import core.soup.Soup;
import core.soup.block.BlockType;
import core.soup.block.iBlock;
import core.soup.idvm.Idvm;
import utils.TestMock;

public class SoupTest {
	Soup cut;
	private Idvm mIdvm;

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
	public void foodGetsNullAfterInteraction() throws PosIsOutOfGrid {
		for (Pos iPos : Pos.getAllGridPos()) {
			iBlock lBlock = cut.getBlock(iPos);
			if (lBlock == null || lBlock.getBlockType() != BlockType.FOOD)
				continue;
			mIdvm.setPosition(iPos);
			cut.step();
			mIdvm.setPosition(iPos.getPosFromDirection(Direction.EAST));
			cut.step();
			mIdvm.setPosition(iPos.getPosFromDirection(Direction.EAST));
			cut.step();
			assertNull(cut.getBlock(iPos));
			return;
		}
		fail();
	}
}
