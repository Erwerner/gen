package datatypes;

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
	
	public boolean equals(Object o){
		Pos other = (Pos) o;
		return this.x == other.x && this.y == other.y;
	}
}
