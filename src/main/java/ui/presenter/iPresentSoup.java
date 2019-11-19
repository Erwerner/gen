package ui.presenter;

import soup.block.iBlock;
import datatypes.Pos;
import globals.exceptions.ExOutOfGrid;

public interface iPresentSoup {
	iPresentSoup getPresenter();
	iBlock getBlock(Pos pos) throws ExOutOfGrid; 
}
