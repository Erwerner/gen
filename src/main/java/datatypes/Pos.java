package datatypes;

import java.util.ArrayList;

import globals.Constants;
import globals.exceptions.ExOutOfGrid;
import globals.exceptions.ExWrongDirection;

public class Pos {
	public int x;
	public int y;

	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Pos getRandomSoupPosition() {
		int x = (int) (Math.random() * Constants.soupSize);
		int y = (int) (Math.random() * Constants.soupSize);
		return new Pos(x, y);
	}

	public boolean equals(Object o) {
		Pos other = (Pos) o;
		return this.x == other.x && this.y == other.y;
	}

	public Pos getPosFromDirection(Direction pDirection) throws ExOutOfGrid {
		Pos lNewPos;
		switch (pDirection) {
		case UP:
			lNewPos = new Pos(x, y - 1);
			break;
		case DOWN:
			lNewPos = new Pos(x, y + 1);
			break;
		case LEFT:
			lNewPos = new Pos(x - 1, y);
			break;
		case RIGHT:
			lNewPos = new Pos(x + 1, y);
			break;
		case NOTHING:
			lNewPos = new Pos(x, y);
			break;
		default:
			throw new ExWrongDirection(pDirection);
		}

		if (lNewPos.x < 0)
			throw new ExOutOfGrid();
		if (lNewPos.x > Constants.soupSize - 1)
			throw new ExOutOfGrid();
		if (lNewPos.y < 0)
			throw new ExOutOfGrid();
		if (lNewPos.y > Constants.soupSize - 1)
			throw new ExOutOfGrid();
		return lNewPos;
	}

	public Direction getDircetionTo(Pos pTargetPos) {
		if (pTargetPos.y < this.y)
			return Direction.UP;
		if (pTargetPos.y > this.y)
			return Direction.DOWN;
		if (pTargetPos.x < this.x)
			return Direction.LEFT;
		if (pTargetPos.x > this.x)
			return Direction.RIGHT;
		return Direction.NOTHING;
	}

	public static ArrayList<Pos> getAllGridPos() {
		ArrayList<Pos> lAllPos = new ArrayList<Pos>();
		for (int x = 0; x < Constants.soupSize; x++) {
			for (int y = 0; y < Constants.soupSize; y++) {
				lAllPos.add(new Pos(x, y));
			}
		}
		return lAllPos;
	}

	@Override
	public String toString() {
		return x + ", " + y;
	}

	public void isInGrid() throws ExOutOfGrid {
		if (!(x >= 0 && y >= 0 && x < Constants.soupSize && y < Constants.soupSize))
			throw new ExOutOfGrid();
	}
}
