package genes;

import java.util.ArrayList;
import java.util.Random;

import datatypes.Direction;

//TODO 1 IMPL Target Movement
public class MoveProbability {
	private int mUp;
	private int mDown;
	private int mLeft;
	private int mRight;
	private int mNothing;

	public MoveProbability(int pUp, int pDown, int pLeft, int pRight, int pNothing) {
		super();
		// TODO IMPL moreDirections;
		mUp = pUp;
		mDown = pDown;
		mLeft = pLeft;
		mRight = pRight;
		mNothing = pNothing;
	}

	public Direction getDirection() {
		ArrayList<Direction> lPossibleDirection = new ArrayList<Direction>();
		// TODO REF dynamic
		for (int i = 0; i < mUp; i++)
			lPossibleDirection.add(Direction.UP);
		for (int i = 0; i < mDown; i++)
			lPossibleDirection.add(Direction.DOWN);
		for (int i = 0; i < mLeft; i++)
			lPossibleDirection.add(Direction.LEFT);
		for (int i = 0; i < mRight; i++)
			lPossibleDirection.add(Direction.RIGHT);
		for (int i = 0; i < mNothing; i++)
			lPossibleDirection.add(Direction.NOTHING);
		int rnd = new Random().nextInt(lPossibleDirection.size());
		return lPossibleDirection.get(rnd);
	}
}
