package pairing;

import java.util.ArrayList;
import java.util.Collections;

import core.genes.Crossover;
import core.genes.Genome;
import core.soup.idvm.Idvm;

public abstract class Pairing {
	protected ArrayList<Genome> mPopulationSorted = new ArrayList<Genome>();

	public Pairing(ArrayList<Idvm> pPopulation) {
		super();
		try {
			ArrayList<Integer> lStepCountSorted = new ArrayList<Integer>();
			for (Idvm iIdvm : pPopulation) {
				Integer lStepCount = iIdvm.getStepCount();
				if (!lStepCountSorted.contains(lStepCount))
					lStepCountSorted.add(lStepCount);
			}
			Collections.sort(lStepCountSorted);

			for (Integer iStepCount : lStepCountSorted)
				for (Idvm iIdvm : pPopulation)
					if (iIdvm.getStepCount() == iStepCount)
						mPopulationSorted.add((Genome) iIdvm.getGenomeOrigin()
								.clone());

		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	protected ArrayList<Genome> getTopNOfPopulation(
			ArrayList<Genome> pPopulation, int pTopN) {
		ArrayList<Genome> lTopNPopulation = new ArrayList<Genome>();
		int lPopulationSize = pPopulation.size();
		for (int i = lPopulationSize - 1; i >= lPopulationSize
				- (lPopulationSize / pTopN); i--) {
			lTopNPopulation.add(pPopulation.get(i));
		}
		return lTopNPopulation;
	}

	protected ArrayList<Genome> createOffsprings(ArrayList<Genome> pGenePool) {
		ArrayList<Genome> lOffsprings = new ArrayList<Genome>();
		try {
			for (int i = 0; i < pGenePool.size(); i = i + 2) {
				Genome[] lGenomes = new Genome[2];
				Genome lParent1 = pGenePool.get(i);
				Genome lParent2 = pGenePool.get(i + 1);
				lGenomes[0] = (Genome) lParent1.clone();
				lGenomes[1] = (Genome) lParent2.clone();
				Crossover lCrossover = new Crossover(lGenomes);
				Genome lOffspring = lCrossover.crossover();
				lOffsprings.add(lOffspring);
			}
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		return lOffsprings;
	}
}
