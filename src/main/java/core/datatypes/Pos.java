package core.datatypes;

import java.io.Serializable;
import java.util.ArrayList;

import core.exceptions.PosIsOutOfGrid;
import core.exceptions.WrongDirection;
import globals.Config;

public class Pos implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x;
	public int y;

	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Pos getRandomSoupPosition() {
		int x = (int) (Math.random() * Config.cSoupSize);
		int y = (int) (Math.random() * Config.cSoupSize);
		return new Pos(x, y);
	}

	public boolean equals(Object o) {
		Pos other = (Pos) o;
		return this.x == other.x && this.y == other.y;
	}

	public Pos getPosFromDirection(Direction pDirection) throws PosIsOutOfGrid {
		Pos lNewPos;
		switch (pDirection) {
		case NORTH:
			lNewPos = new Pos(x, y - 1);
			break;
		case SOUTH:
			lNewPos = new Pos(x, y + 1);
			break;
		case WEST:
			lNewPos = new Pos(x - 1, y);
			break;
		case EAST:
			lNewPos = new Pos(x + 1, y);
			break;
		case NOTHING:
			lNewPos = new Pos(x, y);
			break;
		default:
			throw new WrongDirection();
		}

		lNewPos.isInGrid();
		return lNewPos;
	}

	public Direction getDircetionTo(Pos pTargetPos) {
		if (pTargetPos.y < this.y)
			return Direction.NORTH;
		if (pTargetPos.y > this.y)
			return Direction.SOUTH;
		if (pTargetPos.x < this.x)
			return Direction.WEST;
		if (pTargetPos.x > this.x)
			return Direction.EAST;
		return Direction.NOTHING;
	}

	public static ArrayList<Pos> getAllGridPos() {
		ArrayList<Pos> lAllPos = new ArrayList<Pos>();
		for (int x = 0; x < Config.cSoupSize; x++) {
			for (int y = 0; y < Config.cSoupSize; y++) {
				lAllPos.add(new Pos(x, y));
			}
		}
		return lAllPos;
	}

	@Override
	public String toString() {
		return x + ", " + y;
	}

	public void isInGrid() throws PosIsOutOfGrid {
		if (!(x >= 0 && y >= 0 && x < Config.cSoupSize && y < Config.cSoupSize))
			throw new PosIsOutOfGrid();
	}
}
