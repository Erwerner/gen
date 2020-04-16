package core.soup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import core.genes.Genome;
import core.genes.iGene;
import core.population.GenomePool;
import core.population.PopulationGene;
import core.soup.block.IdvmCell;
import core.soup.idvm.IdvmState;

public class GenomePoolTest {
	GenomePool cut;
	private Genome mGenome = new Genome().forceMutation();

	@Before
	public void setup() {
		cut = new GenomePool();
	}
	
	@Test
	public void appendingIdvmAddesGenesToPool() {

		cut.appenGenome(mGenome);

		List<iGene> actGenes = new ArrayList<iGene>();
		iGene exp = mGenome.cellGrow.get(0);
		List<PopulationGene> populationGenes = cut.getGenes();
		for (PopulationGene iPopulationGene : populationGenes) {
			actGenes.add(iPopulationGene.getOriginGene());
		}
		assertTrue(actGenes.contains(exp));
	}

	@Test
	public void GenomePoolHastTypeOfEachGene() {

		cut.appenGenome(mGenome);

		List<iGene> actGenes = new ArrayList<iGene>();
		List<PopulationGene> populationGenes = cut.getGenes();
		for (PopulationGene iPopulationGene : populationGenes) {
			actGenes.add(iPopulationGene.getOriginGene());
		}
		assertTrue(actGenes.contains(mGenome.getHunger()));
		assertTrue(actGenes.contains(mGenome.cellGrow.get(1)));
		assertTrue(actGenes.contains(mGenome.moveSequencesForState.get(IdvmState.FOOD).get(1)));
		assertTrue(actGenes.contains(mGenome.moveSequencesForState.get(IdvmState.ENEMY).get(1)));
		// assertTrue(actGenes.contains(mGenome.mTargetDetectionOrder.get(1)));
	}

	@Test
	public void appendingGene3TimesIncreasesCounterTo3() {
		Genome lGenome1 = new Genome().forceMutation();
		Genome lGenome2 = new Genome().forceMutation();
		Genome lGenome3 = new Genome().forceMutation();

		IdvmCell lCopiedGene = lGenome1.cellGrow.get(1);

		lGenome2.cellGrow.set(1, lCopiedGene);
		lGenome3.cellGrow.set(1, lCopiedGene);
		
		cut.appenGenome(lGenome1);
		cut.appenGenome(lGenome2);
		cut.appenGenome(lGenome3);

		List<PopulationGene> populationGenes = cut.getGenesSortedByRank();
		
		PopulationGene lTopGene = populationGenes.get(0);
		System.out.println(lTopGene.getOriginGene() + " at #" + lTopGene.mSequenceIndex + " and #" + lTopGene.getOriginGene().getSequendeIndex());
		PopulationGene lSecondGene = populationGenes.get(1);
		System.out.println(lSecondGene.getOriginGene() + " at #" + lSecondGene.mSequenceIndex + " and #" + lSecondGene.getOriginGene().getSequendeIndex());

		for(PopulationGene iGene : populationGenes) {
			if(iGene.getOriginGene().equals(lCopiedGene)) {
				assertEquals(3, iGene.getHostCounter());
				return;
			}
		}
		fail("");
	}

	@Test
	public void hostCountersAreIncreased() {
		Genome lGenome1 = new Genome().forceMutation();
		Genome lGenome2 = new Genome().forceMutation(); 

		IdvmCell copiedGene = lGenome1.cellGrow.get(1);

		lGenome2.cellGrow.set(1, copiedGene); 

		cut.appenGenome(lGenome1);
		cut.appenGenome(lGenome2); 

		List<PopulationGene> populationGenes = cut.getGenesSortedByRank();

		for(PopulationGene iGene : populationGenes) {
			if(iGene.getHostCounter() > 1) {
				assertTrue(true);
				return;
			}
		}
		fail("");
	}

	@Test
	public void appendingGeneOnDifferentPositionDoesntIncreaseCounter() {
		Genome lGenome1 = new Genome().forceMutation();
		Genome lGenome2 = new Genome().forceMutation();
		Genome lGenome3 = new Genome().forceMutation();

		IdvmCell copiedGene = lGenome1.cellGrow.get(1);

		lGenome2.cellGrow.set(2, copiedGene);
		lGenome3.cellGrow.set(3, copiedGene);
		
		cut.appenGenome(lGenome1);
		cut.appenGenome(lGenome2);
		cut.appenGenome(lGenome3);

		List<PopulationGene> populationGenes = cut.getGenesSortedByRank();
		for(PopulationGene iGene : populationGenes) {
			if(iGene.getOriginGene().equals(copiedGene)) {
				assertNotEquals(3, iGene.getHostCounter());
			}
		}
	}

	@Test
	public void genesAreSortedByRank() {
		Genome lGenome1 = new Genome().forceMutation();
		Genome lGenome2 = new Genome().forceMutation();
		
		cut.appenGenome(lGenome1);
		cut.appenGenome(lGenome2);

		List<PopulationGene> firstPopulationGenes = cut.getGenesSortedByRank();

		PopulationGene lExp = firstPopulationGenes.get(1);
		lExp.increaseHostCounter();
		lExp.increaseHostCounter();
		lExp.increaseHostCounter();

		List<PopulationGene> actPopulationGenes = cut.getGenesSortedByRank();

		assertEquals(lExp, actPopulationGenes.get(0));
	}
}
