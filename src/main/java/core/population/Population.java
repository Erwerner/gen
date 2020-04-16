package core.population;

import java.util.ArrayList;
import java.util.List;

import core.soup.exception.PopulationEmpty;
import core.soup.idvm.Idvm;
import ui.presenter.iPresentPopulation;

public class Population implements iPresentPopulation{
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

}
