package genes;

import java.util.ArrayList;
import java.util.Random;

import datatypes.Direction;

//TODO 1 IMPL Target Movement
public class MoveProbability {
	private ArrayList<Direction> mPossibleDirection;

	//TODO Delete Parameter
	public MoveProbability(int pUp, int pDown, int pLeft, int pRight, int pNothing, int pCurrent) {
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
	public MoveProbability(ArrayList<Direction> pPossibleDirection){
		mPossibleDirection=pPossibleDirection;
	}
	public MoveProbability setDirection(Direction pDirection, int pProbability) {
		for (int i = 0; i < pProbability; i++)
			mPossibleDirection.add(pDirection);
		return this;
	}

	public Direction getDirection() {
		//TODO FIX not empty
		int rnd = new Random().nextInt(mPossibleDirection.size());
		return mPossibleDirection.get(rnd);
	}
}
