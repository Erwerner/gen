package ui.presenter;

import java.util.HashMap;
import java.util.List;

import core.population.GenomePool;
import core.population.PopulationGene;
import core.soup.exception.PopulationEmpty;
import core.soup.idvm.Idvm;

public interface iPresentPopulation {

	List<Idvm> getIdvmList();

	GenomePool getGenomePool() throws PopulationEmpty;

	int getPopulationSize();

	List<PopulationGene> getGenesSortedByRank() throws PopulationEmpty;

	HashMap<Integer, Integer> getHungerValueList() throws PopulationEmpty;

}
