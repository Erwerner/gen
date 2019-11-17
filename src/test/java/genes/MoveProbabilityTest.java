package genes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoveProbabilityTest {
MoveProbability cut = new MoveProbability();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void mutationInitsArray() {
		cut.mutate(1.0);
		assertNotNull(cut.getDirection());
	}
}
