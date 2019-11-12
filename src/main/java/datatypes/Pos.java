package datatypes;

import soup.idvm.ExWrongDirection;

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

	public Pos getPosFromDirection(Directions pDirection) {
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

		default:
			throw new ExWrongDirection();
		}

		if(lNewPos.x<0)lNewPos.x=0;
		if(lNewPos.x>Constants.soupSize-1)lNewPos.x=Constants.soupSize-1;
		if(lNewPos.y<0)lNewPos.y=0;
		if(lNewPos.y>Constants.soupSize-1)lNewPos.y=Constants.soupSize-1;
		return lNewPos;
	}
}
