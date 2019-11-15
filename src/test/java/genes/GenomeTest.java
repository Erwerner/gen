package genes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GenomeTest {
	Genome cut = new Genome();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void mutateSetsInitialCells() {
		cut.mutate();
		assertNotNull(cut.cellGrow.get(0));
		assertNotNull(cut.cellGrow.get(1));
		assertNotNull(cut.cellGrow.get(2));
		assertNotNull(cut.cellGrow.get(3));
	}
	@Test(expected = IndexOutOfBoundsException.class)
	public void mutateSetsNotMoreThanInitialCells() {
		cut.mutate();
		cut.cellGrow.get(4);
	}

}
