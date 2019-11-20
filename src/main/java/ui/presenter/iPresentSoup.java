package ui.presenter;

import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import core.soup.block.iBlock;

public interface iPresentSoup {
	iPresentSoup getPresenter();
	iBlock getBlock(Pos pos) throws PosIsOutOfGrid; 
}
