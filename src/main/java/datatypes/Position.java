package datatypes;

public class Position {
	public int x;
	public int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Position getRandomSoupPosition() {
		int x = (int) (Math.random() * Constants.soupSize);
		int y = (int) (Math.random() * Constants.soupSize);
		return new Position(x, y);
	}

}
