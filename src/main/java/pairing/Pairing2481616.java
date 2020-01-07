package pairing;

import java.util.ArrayList;
import java.util.Collections;

import core.genes.Genome;
import core.soup.idvm.Idvm;

public class Pairing2481616 extends Pairing {

	public Pairing2481616(ArrayList<Idvm> pPopulation) {
		super(pPopulation);
	}

	public ArrayList<Genome> pair() {
		ArrayList<Genome> lGenePool = new ArrayList<Genome>();
		lGenePool.addAll(getTopNOfPopulation(mPopulationSorted, 2));
		lGenePool.addAll(getTopNOfPopulation(mPopulationSorted, 2));
		lGenePool.addAll(getTopNOfPopulation(mPopulationSorted, 4));
		lGenePool.addAll(getTopNOfPopulation(mPopulationSorted, 4));
		lGenePool.addAll(getTopNOfPopulation(mPopulationSorted, 8));
		lGenePool.addAll(getTopNOfPopulation(mPopulationSorted, 8));
		lGenePool.addAll(getTopNOfPopulation(mPopulationSorted, 16));
		lGenePool.addAll(getTopNOfPopulation(mPopulationSorted, 16));
		lGenePool.addAll(getTopNOfPopulation(mPopulationSorted, 16));
		lGenePool.addAll(getTopNOfPopulation(mPopulationSorted, 16));
		Collections.shuffle(lGenePool);
		return createOffsprings(lGenePool);
	}
}
