package core.genes;

import java.util.ArrayList;

import core.datatypes.Decisions;
import globals.Config;
import globals.Helpers;

public class MoveDecisionsProbability implements iGene {
	// TODO 9 REF make private
	public ArrayList<Decisions> mPossibleDecisions = new ArrayList<Decisions>();

	public MoveDecisionsProbability appendDecision(Decisions pDecision, int pProbability) {
		for (int i = 0; i < pProbability; i++)
			mPossibleDecisions.add(pDecision);
		return this;
	}

	public Decisions getDecision() {
		int lRnd = Helpers.rndInt(mPossibleDecisions.size() - 1);
		return mPossibleDecisions.get(lRnd);
	}

	public void mutate() {
		if (!Helpers.checkChance(Config.cChanceCopyDirections)) {
			mPossibleDecisions = null;
			return;
		}
		if (!Helpers.checkChance(Config.cChanceResetDirections)) {
			mPossibleDecisions = null;
		}
		if (mPossibleDecisions == null)
			mPossibleDecisions = new ArrayList<Decisions>();
		// Remove random decision
		for (int i = 0; i < 1; i++) {
			Decisions lRndDecision = (Decisions) Helpers.rndArrayEntry(Decisions.values());
			while (mPossibleDecisions.contains(lRndDecision)) {
				mPossibleDecisions.remove(lRndDecision);
			}
		}
		// Add random decision
		Decisions lRndDecision = (Decisions) Helpers.rndArrayEntry(Decisions.values());
		for (int j = 1; j <= Helpers.rndIntRange(1, 9); j++)
			mPossibleDecisions.add(lRndDecision);
	}

	@Override
	public iGene clone() throws CloneNotSupportedException {
		MoveDecisionsProbability lNewMoveProbability = new MoveDecisionsProbability();
		lNewMoveProbability.mPossibleDecisions.addAll((ArrayList<Decisions>) mPossibleDecisions.clone());
		return lNewMoveProbability;
	}
}
