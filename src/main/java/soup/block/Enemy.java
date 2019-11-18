package soup.block;

import java.util.Random;

import datatypes.Direction;
import datatypes.Pos;
import exceptions.ExOutOfGrid;

public class Enemy extends Block {
	Direction mCurrentDirection;

	public Enemy() {
		super(BlockType.ENEMY);
		setRandomDirection();
	}

	public void step() {
		setDirection();
		move();
	}

	private void move() {
		while (true)
			try {
				Pos lNewPos = mPos.getPosFromDirection(mCurrentDirection);
				setPosition(lNewPos);
				break;
			} catch (ExOutOfGrid e) {
				mCurrentDirection = mCurrentDirection.opposite();
				move();
			}
	}

	private void setDirection() {
		if (new Random().nextInt(15) == 1)
			setRandomDirection();
	}

	private void setRandomDirection() {
		Direction[] lDirections = { Direction.UP, Direction.DOWN,
				Direction.LEFT, Direction.RIGHT, };
		int rnd = new Random().nextInt(lDirections.length);
		mCurrentDirection = lDirections[rnd];
	}

}
