package core.population;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.genes.Crossover;
import core.genes.Genome;
import core.soup.exception.PopulationEmpty;
import core.soup.idvm.Idvm;
import globals.Helpers;
import ui.presenter.iPresentPopulation;

public class Population implements iPresentPopulation {
	private List<Idvm> mIdvmList = new ArrayList<Idvm>();

	public Population(List<Idvm> pPopulationList) {
		setIdvmList(pPopulationList);
	}

	public Population() {
	}

	public int getIdvmCount() {
		return getIdvmList().size();
	}

	public void appendIdvm(Idvm pIdvm) {
		getIdvmList().add(pIdvm);
	}

	public GenomePool getGenomePool() throws PopulationEmpty {
		if (getIdvmCount() == 0)
			throw new PopulationEmpty();

		GenomePool lGenomePool = new GenomePool();
		for (Idvm iIdvm : getIdvmList())
			lGenomePool.appenGenome(iIdvm.getGenomeOrigin());
		return lGenomePool;
	}

	public List<Idvm> getIdvmList() {
		return mIdvmList;
	}

	public void setIdvmList(List<Idvm> pIdvmList) {
		mIdvmList = pIdvmList;
	}

	//TODO 5 REF Crossover Class
	public void makeNextGeneration() throws CloneNotSupportedException {
		ArrayList<Idvm> lOffsprings = new ArrayList<Idvm>();
		ArrayList<Idvm> lParents = new ArrayList<Idvm>();
		for (int iIdvmIdx = mIdvmList.size() - 1; iIdvmIdx >= 0; iIdvmIdx--) {
			lParents.add(mIdvmList.get(iIdvmIdx));
			lParents.add(mIdvmList.get(iIdvmIdx));
		}
		while (!lParents.isEmpty()) {
			Idvm lParent1 = lParents.get(0);
			boolean lDifferentParents = false;
			for (Idvm iIdvm : lParents) {
				if (iIdvm != lParent1)
					lDifferentParents = true;
				;
			}
			int lParent2Idx = Helpers.rndIntRange(1, lParents.size() - 1);
			Idvm lParent2 = lParents.get(lParent2Idx);
			if (lDifferentParents == true)
				if (lParent1 == lParent2)
					continue;
			Genome[] lGenomes = new Genome[2];
			lGenomes[0] = (Genome) lParent1.getGenomeOrigin().clone();
			lGenomes[1] = (Genome) lParent2.getGenomeOrigin().clone();
			Crossover lCrossover = new Crossover(lGenomes);
			Genome lOffspringGenome = lCrossover.crossover();
			lOffspringGenome.naturalMutation();
			lOffsprings.add(new Idvm(lOffspringGenome));

			lParents.remove(lParent2Idx);
			lParents.remove(0);
		}
		mIdvmList = lOffsprings;
	}

	public int getPopulationSize() {
		return mIdvmList.size();
	}

	public List<PopulationGene> getGenesSortedByRank() throws PopulationEmpty {
		return getGenomePool().getGenesSortedByRank();
	}

	public HashMap<Integer, Integer> getHungerValueList() throws PopulationEmpty {
		return getGenomePool().getHungerValueList();
	}

}
