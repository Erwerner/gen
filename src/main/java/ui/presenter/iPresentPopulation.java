package ui.presenter;

import java.util.List;

import core.soup.GenomePool;
import core.soup.exception.PopulationEmpty;
import core.soup.idvm.Idvm;

public interface iPresentPopulation {

	List<Idvm> getIdvmList();

	GenomePool getGenomePool() throws PopulationEmpty;

}
