package core.soup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import core.genes.Genome;
import core.genes.iGene;

public class GenomePool {

	private List<PopulationGene> mGenomeList = new ArrayList<PopulationGene>();

	public void appenGenome(Genome pGenome) {
		for (iGene iGene : pGenome.getGeneCollection()) {
			PopulationGene lNewPopulationGene = new PopulationGene(iGene);
			addOrIncrease(lNewPopulationGene);
		}
	}

	public void addOrIncrease(PopulationGene lNewPopulationGene) {
		for (PopulationGene iPopGene : mGenomeList) {
			if (iPopGene.equals(lNewPopulationGene)) {
				iPopGene.increaseHostCounter();
				return;
			}
		}
		mGenomeList.add(lNewPopulationGene);
	}

	public List<PopulationGene> getGenes() {
		return mGenomeList;
	}

	public List<PopulationGene> getGenesSortedByRank() {
		mGenomeList.sort(new Comparator<PopulationGene>() {
			public int compare(PopulationGene pO1, PopulationGene pO2) {
				return pO2.getHostCounter() - pO1.getHostCounter();
			}
		});
		return mGenomeList;
	}

}
