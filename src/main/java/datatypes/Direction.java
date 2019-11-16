package datatypes;

import exceptions.ExWrongDirection;

public enum Direction {
	UP, DOWN, LEFT, RIGHT, SAME, NOTHING, CURRENT, TARGET, CURRENT_OPPOSITE, TARGET_OPPOSITE;

	public Direction opposite() {
		switch (this) {
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;
		default:
			throw new ExWrongDirection();
		}
	}
}
