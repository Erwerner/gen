package core.soup;

import java.util.ArrayList;
import java.util.List;

import core.soup.exception.PopulationEmpty;
import core.soup.idvm.Idvm;

public class Population {
	public List<Idvm> mIdvmList = new ArrayList<Idvm>();

	public int getIdvmCount() {
		return mIdvmList.size();
	}

	public void appendIdvm(Idvm pIdvm) {
		mIdvmList.add(pIdvm);
	}

	public GenomePool getGenomePool() throws PopulationEmpty {
		if (getIdvmCount() == 0)
			throw new PopulationEmpty();

		GenomePool lGenomePool = new GenomePool();
		for (Idvm iIdvm : mIdvmList)
			lGenomePool.appenGenome(iIdvm.getGenomeOrigin());
		return lGenomePool;
	}

}
