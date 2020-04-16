package core.soup;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import core.genes.Genome;
import core.population.GenomePool;
import core.population.Population;
import core.population.PopulationGene;
import core.soup.exception.PopulationEmpty;
import core.soup.idvm.Idvm;

public class PopulationTest {
	Population cut = new Population();

	@Test
	public void initialPopulationHasNoIdvm() {
		int act = cut.getIdvmCount();
		assertEquals(0, act);
	}

	@Test
	public void addingIdvmIncreasesCounter() {
		appendNewIdvmToCut();
		int act = cut.getIdvmCount();
		assertEquals(1, act);
	}

	public void appendNewIdvmToCut() {
		cut.appendIdvm(new Idvm(new Genome().forceMutation()));
	}

	@Test(expected = PopulationEmpty.class)
	public void getGenomePoolReturnsExceptionWhenPopulationHasNoIdvm() throws PopulationEmpty {
		cut.getGenomePool();
	}

	@Test
	public void havingOneIdvmReturnsBestGenomeWithOneHost() throws PopulationEmpty {
		appendNewIdvmToCut();

		GenomePool genomePool = cut.getGenomePool();
		List<PopulationGene> populationGenomeList = genomePool.getGenes();
		PopulationGene bestGenome = populationGenomeList.get(0);
		assertEquals(1, bestGenome.getHostCounter());
	}
}
