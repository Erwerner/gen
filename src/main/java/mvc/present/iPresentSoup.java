package mvc.present;

import soup.block.iBlock;
import datatypes.Pos;

public interface iPresentSoup {
	iPresentSoup getPresenter();
	iBlock getBlock(Pos pos); 
}
