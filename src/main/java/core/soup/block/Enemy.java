package core.soup.block;

import java.util.Random;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import globals.Helpers;

public class Enemy extends Block {
	Decisions mCurrentDirection;

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
			} catch (PosIsOutOfGrid e) {
				mCurrentDirection = mCurrentDirection.opposite();
				move();
			}
	}

	private void setDirection() {
		if (Helpers.rndInt(15) == 1)
			setRandomDirection();
	}

	private void setRandomDirection() {
		Decisions[] lDirections = { Decisions.UP, Decisions.DOWN,
				Decisions.LEFT, Decisions.RIGHT, };
		mCurrentDirection = (Decisions) Helpers.rndArrayEntry(lDirections);
	}

}
