package genes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GenomeTest {
	Genome cut = new Genome();
	@Before
	public void setUp() throws Exception {
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void mutateSetsNotMoreThanInitialCells() {
		cut.mutate();
		cut.cellGrow.get(4);
	}

}
