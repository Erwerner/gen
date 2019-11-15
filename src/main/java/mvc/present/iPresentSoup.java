package mvc.present;

import soup.block.iBlock;
import datatypes.Pos;
import exceptions.ExOutOfGrid;

public interface iPresentSoup {
	iPresentSoup getPresenter();
	iBlock getBlock(Pos pos) throws ExOutOfGrid; 
}
