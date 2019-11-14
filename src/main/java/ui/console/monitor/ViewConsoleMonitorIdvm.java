package ui.console.monitor;

import mvc.Model;
import mvc.View;
import mvc.present.iPresentIdvm;

public class ViewConsoleMonitorIdvm extends View{

	private iPresentIdvm mPresentIdvm;

	public ViewConsoleMonitorIdvm(Model pModel) {
		super(pModel);
		mPresentIdvm = (iPresentIdvm) pModel;
	}

	public void update() {
		System.out.flush();
		System.out.println(mPresentIdvm.getState().toString());
	}

}
