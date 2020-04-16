package ui.mvc;

public abstract class View implements iObserver {

	public View(Model pModel) {
		pModel.registerView(this);
	}

}
