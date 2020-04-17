package core.population;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import core.genes.GeneInt;
import core.genes.Genome;
import core.genes.iGene;
import core.soup.block.IdvmCell;

public class GenomePool {

	private List<PopulationGene> mGenomeList = new ArrayList<PopulationGene>();

	public void appenGenome(Genome pGenome) {
		for (iGene iGene : pGenome.getGeneCollection()) {
			PopulationGene lNewPopulationGene = new PopulationGene(iGene);
			addOrIncrease(lNewPopulationGene);
		}
	}

	public void addOrIncrease(PopulationGene lNewPopulationGene) {
		// TODO 0 Fix
		if (lNewPopulationGene.mSequenceIndex > 16)
			return;
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

	public HashMap<Integer, Integer> getHungerValueList() {
		HashMap<Integer, Integer> lHungerValueList = new HashMap<Integer, Integer>();
		for (PopulationGene iGene : mGenomeList) {
			if (iGene.getOriginGene().getClass() == GeneInt.class) {
				GeneInt iHungerGene = (GeneInt) iGene.getOriginGene();
				if(lHungerValueList.containsKey(iHungerGene.getValue())) {
					Integer lCounter = lHungerValueList.get(iHungerGene.getValue());
					lCounter++;
				}
				else {
					lHungerValueList.put(iHungerGene.getValue(), 1);
				}
			}
		}
		return lHungerValueList;
	}

}
