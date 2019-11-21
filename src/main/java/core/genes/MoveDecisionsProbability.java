package core.genes;

import globals.Helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import core.datatypes.Decisions;

public class MoveDecisionsProbability implements iGene {
	// TODO REF make private
	public ArrayList<Decisions> mPossibleDecisions = new ArrayList<Decisions>();

	public MoveDecisionsProbability() {

	}

	public MoveDecisionsProbability appendDecision(Decisions pDecision, int pProbability) {
		for (int i = 0; i < pProbability; i++)
			mPossibleDecisions.add(pDecision);
		return this;
	}

	public Decisions getDecision() {
		int lRnd = Helpers.rndInt(mPossibleDecisions.size() - 1);
		// TODO FIX not empty
		return mPossibleDecisions.get(lRnd);
	}

	public void mutate(Double pMutationRate) {
		if (!Helpers.checkChance(pMutationRate))
			return;
		if (!Helpers.checkChance(0.6)) {
			mPossibleDecisions = null;
			return;
		}
		if (mPossibleDecisions == null)
			mPossibleDecisions = new ArrayList<Decisions>();
		// Remove random decision
		for (int i = 0; i < 1; i++) {
			Decisions lRndDirection = (Decisions) Helpers.rndArrayEntry(Decisions.values());
			while (mPossibleDecisions.contains(lRndDirection)) {
				mPossibleDecisions.remove(lRndDirection);
			}
		}
		// Add random decision
		for (int i = 0; i < 1; i++) {
			Decisions lRndDecision = (Decisions) Helpers.rndArrayEntry(Decisions.values());
			for (int j = 1; j <= Helpers.rndIntRange(1, 9); j++)
				mPossibleDecisions.add(lRndDecision);
		}
	}

	@Override
	public iGene clone() throws CloneNotSupportedException {
		MoveDecisionsProbability lNewMoveProbability = new MoveDecisionsProbability();
		lNewMoveProbability.mPossibleDecisions.addAll((ArrayList<Decisions>) mPossibleDecisions.clone());
		return lNewMoveProbability;
	}
}
