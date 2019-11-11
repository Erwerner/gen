package ui.console;

import mvc.Model;
import mvc.View;
import mvc.present.iPresentSoup;

public class ViewConsoleGrid extends View {
	iPresentSoup mSoup;
	public ViewConsoleGrid(Model pModel) {
		super(pModel); 
		iPresentSoup mSoup = (iPresentSoup) pModel; 
	}
	public void update() {
	}

}
