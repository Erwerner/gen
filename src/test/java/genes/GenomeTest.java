package genes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datatypes.Pos;

public class GenomeTest {
	Genome cut = new Genome();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void initalGenomeIsEmpty(){
		assertEquals(0,cut.cellGrow.size());
	}
	@Test
	public void forecedMutationSets4InitialCells(){
		assertEquals(0,cut.cellGrow.size());
		cut.forceMutation();
		assertEquals(new Pos(0,0), cut.cellGrow.get(0).getPosOnIdvm());
		assertEquals(new Pos(0,1), cut.cellGrow.get(1).getPosOnIdvm());
		assertEquals(new Pos(1,0), cut.cellGrow.get(2).getPosOnIdvm());
		assertEquals(new Pos(1,1), cut.cellGrow.get(3).getPosOnIdvm());
	}
}
