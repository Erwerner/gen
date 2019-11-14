package ui.console.monitor;

import java.io.IOException;

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
		System.out.println(mPresentIdvm.getState());
		System.out.println(mPresentIdvm.getStepCount());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
