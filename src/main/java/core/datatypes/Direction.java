package core.datatypes;

import core.exceptions.WrongDirection;

public enum Direction {
	NORTH, EAST, SOUTH, WEST, NOTHING;

	public Direction opposite() {
		switch (this) {
		case NOTHING:
			return NOTHING;
		case NORTH:
			return SOUTH;
		case SOUTH:
			return NORTH;
		case WEST:
			return EAST;
		case EAST:
			return WEST;
		default:
			throw new WrongDirection();
		}
	}

	public Direction side2() {
		switch (this) {
		case NOTHING:
			return NOTHING;
		case NORTH:
			return EAST;
		case SOUTH:
			return WEST;
		case WEST:
			return NORTH;
		case EAST:
			return SOUTH;
		default:
			throw new WrongDirection();
		}
	}

	public Direction side1() {
		switch (this) {
		case NOTHING:
			return NOTHING;
		case NORTH:
			return WEST;
		case SOUTH:
			return EAST;
		case WEST:
			return SOUTH;
		case EAST:
			return NORTH;
		default:
			throw new WrongDirection();
		}
	}
}
