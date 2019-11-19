package datatypes;

import globals.exceptions.ExWrongDirection;

public enum Direction {
	UP, DOWN, LEFT, RIGHT, 
	NOTHING, 
	CURRENT,
	CURRENT_OPPOSITE, CURRENT_SITE1, CURRENT_SITE2,
	TARGET, TARGET_OPPOSITE, TARGET_SITE1, TARGET_SITE2;

	public Direction opposite() {
		switch (this) {
		case NOTHING:
			return NOTHING;
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;
		default:
			throw new ExWrongDirection(this);
		}
	}

	public Direction site2() {
		switch (this) {
		case NOTHING:
			return NOTHING;
		case UP:
			return RIGHT;
		case DOWN:
			return LEFT;
		case LEFT:
			return UP;
		case RIGHT:
			return DOWN;
		default:
			throw new ExWrongDirection(this);
		}
	}
	public Direction site1() {
		switch (this) {
		case NOTHING:
			return NOTHING;
		case UP:
			return LEFT;
		case DOWN:
			return RIGHT;
		case LEFT:
			return DOWN;
		case RIGHT:
			return UP;
		default:
			throw new ExWrongDirection(this);
		}
	}
}
