package soup.idvm;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import datatypes.Pos;

import soup.block.BlockType;

public class IdvmCellTest {
	IdvmCell cut;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void mutates() {
		initInvalidCut();
		cut.mutate(1.0);
		checkCutMutatedValid();
	}
	@Test
	public void chanceZeroDoesntMutate() {
		initInvalidCut();
		cut.mutate(0.0);
		assertEquals(BlockType.NOTHING, cut.getBlockType());
		assertEquals(new Pos(10,10), cut.getPosOnIdvm());
	}

	private void checkCutMutatedValid() {
		BlockType[] lCellTypes = { BlockType.LIFE, BlockType.DEFENCE,
				BlockType.MOVE, BlockType.SENSOR };
		assertTrue(Arrays.asList(lCellTypes).contains(cut.getBlockType()));

		assertTrue(cut.getPosOnIdvm().x<5);
		assertTrue(cut.getPosOnIdvm().y<5);
		assertTrue(cut.getPosOnIdvm().x>=0);
		assertTrue(cut.getPosOnIdvm().y>=0);
	}

	private void initInvalidCut() {
		cut = new IdvmCell(BlockType.NOTHING, new Pos(10,10));
	}

}
