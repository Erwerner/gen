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
			mNewGenome.forceMutation(); // TODO PERFORMANCE remove
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
			// TODO PERFORMANCE remove
			lSecondGenome = choseRndGenome();
		int lCrossoverNumber = Helpers.rndIntRange(1, Config.cMaxSequence - 2);
		for (int iSequenceNumber = 0; iSequenceNumber < Config.cMaxSequence; iSequenceNumber++) {
			Genome lCurrentGenome = lFirstGenome;
			if (iSequenceNumber >= lCrossoverNumber)
				lCurrentGenome = lSecondGenome;

			iGene lClonedCell = lCurrentGenome.cellGrow.get(iSequenceNumber)
					.clone();
			mNewGenome.cellGrow.set(iSequenceNumber, (IdvmCell) lClonedCell);
		}
	}

	private void setSequences() throws CloneNotSupportedException {

		for (IdvmState iState : IdvmState.values()) {
			Genome lFirstGenome = choseRndGenome();
			Genome lSecondGenome = choseRndGenome();
			while (lFirstGenome == lSecondGenome)
				//TODO PERFORMANCE remove 
				lSecondGenome = choseRndGenome();
			int lCrossoverNumber = Helpers.rndIntRange(1,
					Config.cMaxSequence - 2);
			for (int iSequenceNumber = 0; iSequenceNumber < Config.cMaxSequence; iSequenceNumber++) {
				Genome lCurrentGenome = lFirstGenome;
				if (iSequenceNumber >= lCrossoverNumber)
					lCurrentGenome = lSecondGenome;

				MoveDecisionsProbability lOriginMovement = lCurrentGenome.moveSequencesForState
						.get(iState).get(iSequenceNumber);
				mNewGenome.moveSequencesForState.get(iState).set(
						iSequenceNumber, (MoveDecisionsProbability) lOriginMovement.clone());
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
