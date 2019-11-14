package ui.console.monitor;

import java.io.IOException;

import mvc.Model;
import mvc.View;
import mvc.present.iPresentIdvm;

public class ViewConsoleMonitorIdvm extends View {

	private iPresentIdvm mPresentIdvm;

	public ViewConsoleMonitorIdvm(Model pModel) {
		super(pModel);
		mPresentIdvm = (iPresentIdvm) pModel;
	}

	public void update() {
		clearScreen();
		System.out.println(mPresentIdvm.getStepCount());
		System.out.println(mPresentIdvm.isAlive());
		System.out.println(mPresentIdvm.getState());
	}

	private void clearScreen() {
		try {
			Runtime.getRuntime().exec("clear");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
