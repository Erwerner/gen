package core.genes;

import globals.Config;
import globals.Helpers;

//TODO 2 IMPL Corossover
public class Cossover {
	Genome[] mGenomes = new Genome[2];
	private Genome mMNewGenome;

	public Cossover(Genome[] pGenomes) {
		mGenomes = pGenomes;
	}

	public Genome crossover() {
		mMNewGenome = new Genome();
		setPrimitiveGenes();
		setGrowSequence();
		return mMNewGenome;
	}

	private void setGrowSequence() {
		int lSequenceNumber = 1;
//		int lCrossoverNumber = Helpers
		Genome lOriginGenome = choseRndGenome();
		
	}

	private void setPrimitiveGenes() {
		mMNewGenome.setHunger(choseRndGenome().getHunger().getValue());
	}

	private Genome choseRndGenome() {
		return mGenomes[Helpers.rndInt(1)];
	}
}
