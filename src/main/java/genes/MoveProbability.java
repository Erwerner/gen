package genes;

import globals.Global;

import java.util.ArrayList;
import java.util.Random;

import datatypes.Direction;

public class MoveProbability implements iGene {
	// TODO REF make private
	public ArrayList<Direction> mPossibleDirection = new ArrayList<Direction>();

	public MoveProbability() {

	}

	public MoveProbability setDirection(Direction pDirection, int pProbability) {
		for (int i = 0; i < pProbability; i++)
			mPossibleDirection.add(pDirection);
		return this;
	}

	public Direction getDirection() {
		int lRnd = Global.rndInt(mPossibleDirection.size()-1);
		// TODO FIX not empty
		return mPossibleDirection.get(lRnd );
	}

	public void mutate(Double pMutationRate) {
		if (!Global.checkChance(pMutationRate))
			return;
		if (!Global.checkChance(0.6)) {
			mPossibleDirection = null;
			return;
		}
		if (mPossibleDirection == null)
			mPossibleDirection = new ArrayList<Direction>();
		// Remove random Direction
		for (int i = 0; i < 1; i++) {
			Direction lRndDirection = (Direction) Global
					.rndArrayEntry(Direction.values());
			while (mPossibleDirection.contains(lRndDirection)) {
				mPossibleDirection.remove(lRndDirection);
			}
		}
		// Add random direction
		// TODO Test
		for (int i = 0; i < 1; i++) {
			Direction lRndDirection = (Direction) Global
					.rndArrayEntry(Direction.values());
			int lDirectionProbability = Global.rndIntRange(1, 9);
			for (int j = 1; j <= lDirectionProbability; j++)
				mPossibleDirection.add(lRndDirection);
		}
	}
}
