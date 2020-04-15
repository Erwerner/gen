package core.genes;

import java.io.Serializable;
import java.util.ArrayList;

import core.datatypes.Decisions;
import core.soup.block.IdvmCell;
import core.soup.idvm.IdvmState;
import globals.Config;
import globals.Helpers;

public class MoveDecisionsProbability implements Serializable, iGene {

	private static final long serialVersionUID = 1L;
	// TODO 9 REF make private
	public ArrayList<Decisions> mPossibleDecisions = new ArrayList<Decisions>();
	private int mSequenceindex;
	private IdvmState mForState; 
	
	public MoveDecisionsProbability(IdvmState pForState) {
		super();
		mForState = pForState;
	}

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
		MoveDecisionsProbability lNewMoveProbability = new MoveDecisionsProbability(mForState);
		lNewMoveProbability.mPossibleDecisions.addAll((ArrayList<Decisions>) mPossibleDecisions.clone());
		return lNewMoveProbability;
	}
	@Override
	public boolean equals(Object o) {
		if (o == null || !this.getClass().isAssignableFrom(o.getClass()))
			return false;
		
		MoveDecisionsProbability other = (MoveDecisionsProbability) o;

		if(!(mForState.equals(other.mForState)))
			return false;
		
		ArrayList<Decisions> thisPossibleDecisions = (ArrayList<Decisions>) mPossibleDecisions.clone();		
		
		for(Decisions others : other.mPossibleDecisions) {
			if(!others.equals(other.mPossibleDecisions.get(0))) {
				return mPossibleDecisions.equals(other.mPossibleDecisions);
			}
			for(Decisions thisDecision : thisPossibleDecisions) {
				if(!(thisDecision.equals(thisPossibleDecisions.get(0)))){
					return mPossibleDecisions.equals(other.mPossibleDecisions);
				}
			}
			return other.mPossibleDecisions.get(0).equals(thisPossibleDecisions.get(0));
		}
		throw new RuntimeException();
		//return mPossibleDecisions.equals(other.mPossibleDecisions) && mForState.equals(other.mForState);
	}

	public void setSequendeIndex(int pSequenceIndex) {
		mSequenceindex = pSequenceIndex;
	}

	public int getSequendeIndex() {
		return mSequenceindex;
	}

	public IdvmState getForState() {
		return mForState;
	}
	
	@Override
	public String toString() {
		return mForState + " -> " + mPossibleDecisions;
	}
}
