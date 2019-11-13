package genes;

import java.util.ArrayList;

import datatypes.Direction;

public class MovementSequence {

	private ArrayList<MoveProbability> mMovementList;
	private MoveProbability mMovement;

	public MovementSequence(ArrayList<MoveProbability> pMovementList) {
		mMovementList = pMovementList;
		pop();
	}

	public void pop() {
		try {
			MoveProbability lNewMovement = mMovementList.get(0);
			mMovementList.remove(0);
			if (lNewMovement != null) {
				mMovement = lNewMovement;
			}
		} catch (Exception e) {

		}
	}

	public Direction getDirection() {
		return mMovement.getDirection();
	}
}
