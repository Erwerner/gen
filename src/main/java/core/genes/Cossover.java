package core.genes;

import globals.Helpers;

public class Cossover {
	Genome[] mGenomes = new Genome[2];
	public Genome crossover() {
		Genome lNewGenome = new Genome();
		lNewGenome.setHunger(choseRndGenome().getHunger().getValue());
		return lNewGenome;
	}

	private Genome choseRndGenome() {
		return mGenomes[Helpers.rndInt(1)];
	}
}
