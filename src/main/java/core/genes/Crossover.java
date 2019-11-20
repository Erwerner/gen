package core.genes;

import core.soup.block.IdvmCell;
import core.soup.idvm.IdvmState;
import globals.Config;
import globals.Helpers;

public class Crossover {
	Genome[] mGenomes = new Genome[2];
	private Genome mNewGenome;

	public Crossover(Genome[] pGenomes) {
		mGenomes = pGenomes;
	}

	public Genome crossover() {
		try {
			mNewGenome = new Genome();
			mNewGenome.forceMutation();
			setPrimitiveGenes();
			setSequences();
			setGrowCells();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return mNewGenome;
	}

	private void setGrowCells() throws CloneNotSupportedException {
		Genome lFirstGenome = choseRndGenome();
		Genome lSecondGenome = choseRndGenome();
		while (lFirstGenome == lSecondGenome)
			lSecondGenome = choseRndGenome();
		int lCrossoverNumber = Helpers.rndInt(Config.cMaxSequence - 2);
		for (int iSequenceNumber = 0; iSequenceNumber < Config.cMaxSequence; iSequenceNumber++) {
			Genome lCurrentGenome = lFirstGenome;
			if (iSequenceNumber >= lCrossoverNumber)
				lCurrentGenome = lSecondGenome;

			mNewGenome.cellGrow.set(iSequenceNumber,(IdvmCell) lCurrentGenome.cellGrow.get(
					iSequenceNumber).clone());
		}
	}

	private void setSequences() throws CloneNotSupportedException {

		for (IdvmState iState : IdvmState.values()) {
			Genome lFirstGenome = choseRndGenome();
			Genome lSecondGenome = choseRndGenome();
			while (lFirstGenome == lSecondGenome)
				lSecondGenome = choseRndGenome();
			int lCrossoverNumber = Helpers.rndInt(Config.cMaxSequence - 2);
			for (int iSequenceNumber = 0; iSequenceNumber < Config.cMaxSequence; iSequenceNumber++) {
				Genome lCurrentGenome = lFirstGenome;
				if (iSequenceNumber >= lCrossoverNumber)
					lCurrentGenome = lSecondGenome;

				MoveProbability lOriginMovement = (MoveProbability) lCurrentGenome.movementSequences
						.get(iState).get(iSequenceNumber).clone();
				mNewGenome.movementSequences.get(iState).set(iSequenceNumber,lOriginMovement);
			}
		}
	}

	private void setPrimitiveGenes() {
		mNewGenome.setHunger(choseRndGenome().getHunger().getValue());
	}

	private Genome choseRndGenome() {
		return mGenomes[Helpers.rndInt(1)];
	}
}
