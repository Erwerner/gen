package core.soup.idvm;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import core.datatypes.Pos;
import core.soup.block.BlockType;
import core.soup.block.IdvmCell;

public class IdvmCellTest {
	IdvmCell cut;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void mutates() {
		initInvalidCut();
		cut.mutate();
		checkCutMutatedValid();
	}

	private void checkCutMutatedValid() {
		BlockType[] lCellTypes = { BlockType.LIFE, BlockType.DEFENCE, BlockType.MOVE, BlockType.SENSOR };
		assertTrue(Arrays.asList(lCellTypes).contains(cut.getBlockType()));

		assertTrue(cut.getPosOnIdvm().x < 3);
		assertTrue(cut.getPosOnIdvm().y < 3);
		assertTrue(cut.getPosOnIdvm().x >= -1);
		assertTrue(cut.getPosOnIdvm().y >= -1);
	}

	private void initInvalidCut() {
		cut = new IdvmCell(BlockType.NULL, new Pos(10, 10));
	}

}
