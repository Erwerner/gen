package core.genes;

import globals.Helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import core.datatypes.Decisions;

public class MoveProbability implements iGene {
	// TODO REF make private
	public ArrayList<Decisions> mPossibleDirection = new ArrayList<Decisions>();

	public MoveProbability() {

	}

	public MoveProbability setDirection(Decisions pDirection, int pProbability) {
		for (int i = 0; i < pProbability; i++)
			mPossibleDirection.add(pDirection);
		return this;
	}

	public Decisions getDirection() {
		int lRnd = Helpers.rndInt(mPossibleDirection.size() - 1);
		// TODO FIX not empty
		return mPossibleDirection.get(lRnd);
	}

	public void mutate(Double pMutationRate) {
		if (!Helpers.checkChance(pMutationRate))
			return;
		if (!Helpers.checkChance(0.6)) {
			mPossibleDirection = null;
			return;
		}
		if (mPossibleDirection == null)
			mPossibleDirection = new ArrayList<Decisions>();
		// Remove random Direction
		for (int i = 0; i < 1; i++) {
			Decisions lRndDirection = (Decisions) Helpers.rndArrayEntry(Decisions.values());
			while (mPossibleDirection.contains(lRndDirection)) {
				mPossibleDirection.remove(lRndDirection);
			}
		}
		// Add random direction
		for (int i = 0; i < 1; i++) {
			Decisions lRndDirection = (Decisions) Helpers.rndArrayEntry(Decisions.values());
			int lDirectionProbability = Helpers.rndIntRange(1, 9);
			for (int j = 1; j <= lDirectionProbability; j++)
				mPossibleDirection.add(lRndDirection);
		}
	}

	@Override
	public iGene clone() throws CloneNotSupportedException {
		MoveProbability lNewMoveProbability = new MoveProbability();
		lNewMoveProbability.mPossibleDirection.addAll((ArrayList<Decisions>) mPossibleDirection.clone());
		return lNewMoveProbability;
	}
}
