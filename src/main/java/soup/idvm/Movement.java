package soup.idvm;

import java.util.ArrayList;
import java.util.Random;

import datatypes.Directions;

public class Movement {
	private int mUp;
	private int mDown;
	private int mLeft;
	private int mRight;
	public Movement(int pUp, int pDown, int pLeft, int pRight) {
		super();
		mUp = pUp;
		mDown = pDown;
		mLeft = pLeft;
		mRight = pRight;
	}
	public Directions getDirection(){
		ArrayList<Directions> lPossibleDirection = new ArrayList<Directions>();
		for(int i=0;i<mUp;i++)lPossibleDirection.add(Directions.UP);
		for(int i=0;i<mDown;i++)lPossibleDirection.add(Directions.DOWN);
		for(int i=0;i<mLeft;i++)lPossibleDirection.add(Directions.LEFT);
		for(int i=0;i<mRight;i++)lPossibleDirection.add(Directions.RIGHT);
	    int rnd = new Random().nextInt(lPossibleDirection.size());
	    return lPossibleDirection.get(rnd);
	}
}
