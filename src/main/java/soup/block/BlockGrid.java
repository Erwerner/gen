package soup.block;

import datatypes.Constants;
import datatypes.Position;

public class BlockGrid implements iBlockGrid {
	private iBlock[][] grid;

	public BlockGrid() {
		grid = new iBlock[Constants.soupSize][Constants.soupSize];
	}

	public iBlock getBlock(Position pos) {
		return grid[pos.x][pos.y];
	}

	public void setBlock(Position pos, iBlock block) {
		grid[pos.x][pos.y] = block;
	}

	public void setRandomBlock(iBlock foodBlock) {
		while (true) {
			Position pos = Position.getRandomSoupPosition();
			if (getBlock(pos) == null) {
				setBlock(pos, foodBlock);
				return;
			}
		}
	}
}
