package ui.mvc;

import java.util.ArrayList;
import java.util.List;

public abstract class Model {
	List<View> mViews = new ArrayList<View>();

	public void registerView(View pView) {
		mViews.add(pView);
	}

	protected void notifyViews() {
		for (View iView : mViews)
			iView.update();
	}

	public abstract <T> Object getPresenter(Class pType);
}
