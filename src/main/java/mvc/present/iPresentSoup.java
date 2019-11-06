package mvc.present;

import soup.block.iBlock;
import datatypes.Position;

public interface iPresentSoup {
	public Position getIdvmPosition();
	public iBlock getBlock(Position pos);
	boolean isIdvmAlive();
}
