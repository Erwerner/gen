package datatypes;

import exceptions.ExWrongDirection;

public enum Direction {
	UP, DOWN, LEFT, RIGHT, SAME, NOTHING, CURRENT, TARGET, CURRENT_OPPOSITE, TARGET_OPPOSITE, TARGET_SITE2, CURRENT_SITE1, TARGET_SITE1, CURRENT_SITE2;

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

	public Direction site2() {
		switch (this) {
		case UP:
			return RIGHT;
		case DOWN:
			return LEFT;
		case LEFT:
			return UP;
		case RIGHT:
			return DOWN;
		default:
			throw new ExWrongDirection();
		}
	}
	public Direction site1() {
		switch (this) {
		case UP:
			return LEFT;
		case DOWN:
			return RIGHT;
		case LEFT:
			return DOWN;
		case RIGHT:
			return UP;
		default:
			throw new ExWrongDirection();
		}
	}
}
