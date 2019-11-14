package genes;

import java.util.ArrayList;
import java.util.Random;

import datatypes.Direction;

public class MoveProbability {
	private int mUp;
	private int mDown;
	private int mLeft;
	private int mRight;
	public MoveProbability(int pUp, int pDown, int pLeft, int pRight) {
		super();
		mUp = pUp;
		mDown = pDown;
		mLeft = pLeft;
		mRight = pRight;
	}
	public Direction getDirection(){
		ArrayList<Direction> lPossibleDirection = new ArrayList<Direction>();
		for(int i=0;i<mUp;i++)lPossibleDirection.add(Direction.UP);
		for(int i=0;i<mDown;i++)lPossibleDirection.add(Direction.DOWN);
		for(int i=0;i<mLeft;i++)lPossibleDirection.add(Direction.LEFT);
		for (int i = 0; i < mRight; i++)
			lPossibleDirection.add(Direction.RIGHT);
		int rnd = new Random().nextInt(lPossibleDirection.size());
		return lPossibleDirection.get(rnd);
	}
}
