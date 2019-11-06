package ui.console;

import mvc.Model;
import mvc.View;
import mvc.present.iPresentSoup;

public class ViewConsoleGrid extends View {
	iPresentSoup soup;
	public ViewConsoleGrid(Model pModel) {
		super(pModel); 
		iPresentSoup soup = (iPresentSoup) pModel; 
	}
	public void update() {
	}

}
