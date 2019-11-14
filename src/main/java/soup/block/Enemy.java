package soup.block;

import java.util.Random;

import datatypes.Direction;
import datatypes.Pos;

public class Enemy extends Block {
	Direction mCurrentDirection;

	public Enemy() {
		super(BlockType.ENEMY);
		setDirection();
	}

	public void step() {
		setDirection();
		move();
	}

	private void move() {
		Pos lNewPos = mPos.getPosFromDirection(mCurrentDirection);
		setPosition(lNewPos);
	}

	private void setDirection() {
		if (new Random().nextInt(15) == 0)
			setRandomDirection();
		mCurrentDirection = Direction.LEFT;
	}

	private void setRandomDirection() {
		Direction[] lDirections = Direction.values();
		int rnd = new Random().nextInt(lDirections.length);
		mCurrentDirection = lDirections[rnd];
	}

}
