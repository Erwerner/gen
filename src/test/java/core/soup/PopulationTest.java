package core.soup;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import core.genes.Genome;
import core.soup.idvm.Idvm;

public class PopulationTest {
	@Test
	public void initialPopulationHasNoIdvm() {
		Population cut = new Population();
		int act = cut.getIdvmCount();
		assertEquals(0, act);
	}

	@Test
	public void addingIdvmIncreasesCounter() {
		Population cut = new Population();
		cut.appendIdvm(new Idvm(new Genome().forceMutation()));
		int act = cut.getIdvmCount();
		assertEquals(1, act);
	}
}
