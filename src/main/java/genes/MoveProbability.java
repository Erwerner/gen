package genes;

import globals.Global;

import java.util.ArrayList;
import java.util.Random;

import datatypes.Direction;

public class MoveProbability implements iGene {
	//TODO REF make private
	public ArrayList<Direction> mPossibleDirection = new ArrayList<Direction>();

	// TODO REF Delete Parameter
	public MoveProbability(int pUp, int pDown, int pLeft, int pRight,
			int pNothing, int pCurrent) {
		mPossibleDirection = new ArrayList<Direction>();
		for (int i = 0; i < pUp; i++)
			mPossibleDirection.add(Direction.UP);
		for (int i = 0; i < pDown; i++)
			mPossibleDirection.add(Direction.DOWN);
		for (int i = 0; i < pLeft; i++)
			mPossibleDirection.add(Direction.LEFT);
		for (int i = 0; i < pRight; i++)
			mPossibleDirection.add(Direction.RIGHT);
		for (int i = 0; i < pNothing; i++)
			mPossibleDirection.add(Direction.NOTHING);
	}

	public MoveProbability() {

	}

	// TODO REF delete
	public MoveProbability(ArrayList<Direction> pMovements) {
		mPossibleDirection = pMovements;
	}

	public MoveProbability setDirection(Direction pDirection, int pProbability) {
		for (int i = 0; i < pProbability; i++)
			mPossibleDirection.add(pDirection);
		return this;
	}

	public Direction getDirection() {
		// TODO FIX not empty
		int rnd = new Random().nextInt(mPossibleDirection.size());
		return mPossibleDirection.get(rnd);
	}

	public void mutate(Double pMutationRate) {
		if (!Global.checkChance(pMutationRate))
			return;
		//Remove two random Directions
		for (int i = 0; i < 2; i++) {
			// TODO REF move to Globals
			Random lRandom = new Random();
			int lRndIdx = lRandom.nextInt(Direction.values().length);
			Direction lRndDirection = Direction.values()[lRndIdx];
			while(mPossibleDirection.contains(lRndDirection)){
				mPossibleDirection.remove(lRndDirection);
			}
		}
		//Add two random directions
		for (int i = 0; i < 1; i++) {
			// TODO REF move to Globals
			Random lRandom = new Random();
			int lRndIdx = lRandom.nextInt(Direction.values().length);
			Direction lRndDirection = Direction.values()[lRndIdx];
			int lDirectionProbability = lRandom.nextInt(9) + 1;
			for (int j = 1; j <= lDirectionProbability; j++)
				mPossibleDirection.add(lRndDirection);
		}
	}
}
