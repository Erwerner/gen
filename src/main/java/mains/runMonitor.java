package mains;

import ui.console.monitor.ModelMonitorIdvm;

public class runMonitor {

	public static void main(String[] args) {
		run();
	}

	public static void run() {
		ModelMonitorIdvm lMonitor = new ModelMonitorIdvm();
		lMonitor.run();
	}
}
