package core.datatypes;

import java.util.ArrayList;

import core.exceptions.PosIsOutOfGrid;
import core.exceptions.WrongDecision;
import globals.Config;

public class Pos {
	public int x;
	public int y;

	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Pos getRandomSoupPosition() {
		int x = (int) (Math.random() * Config.soupSize);
		int y = (int) (Math.random() * Config.soupSize);
		return new Pos(x, y);
	}

	public boolean equals(Object o) {
		Pos other = (Pos) o;
		return this.x == other.x && this.y == other.y;
	}

	//TODO Direction Type
	public Pos getPosFromDirection(Decisions pDirection) throws PosIsOutOfGrid {
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
			throw new WrongDecision(pDirection);
		}

		if (lNewPos.x < 0)
			throw new PosIsOutOfGrid();
		if (lNewPos.x > Config.soupSize - 1)
			throw new PosIsOutOfGrid();
		if (lNewPos.y < 0)
			throw new PosIsOutOfGrid();
		if (lNewPos.y > Config.soupSize - 1)
			throw new PosIsOutOfGrid();
		return lNewPos;
	}

	public Decisions getDircetionTo(Pos pTargetPos) {
		if (pTargetPos.y < this.y)
			return Decisions.UP;
		if (pTargetPos.y > this.y)
			return Decisions.DOWN;
		if (pTargetPos.x < this.x)
			return Decisions.LEFT;
		if (pTargetPos.x > this.x)
			return Decisions.RIGHT;
		return Decisions.NOTHING;
	}

	public static ArrayList<Pos> getAllGridPos() {
		ArrayList<Pos> lAllPos = new ArrayList<Pos>();
		for (int x = 0; x < Config.soupSize; x++) {
			for (int y = 0; y < Config.soupSize; y++) {
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
		if (!(x >= 0 && y >= 0 && x < Config.soupSize && y < Config.soupSize))
			throw new PosIsOutOfGrid();
	}
}
