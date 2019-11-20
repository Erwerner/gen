package devutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Measure {
	private static ArrayList<String> mEntries = new ArrayList<String>();
	private static Long mLastMeasure = new Long(0);

	public static void startTimeMeasuring() {
		if (!Debug.debugOn)
			return;
		mLastMeasure = System.nanoTime();
	}

	public static void finishTimeMeasureEntry(String pStep) {
		if (!Debug.debugOn)
			return;
		mLastMeasure = System.nanoTime();
		if (pStep != "!")
			mEntries.add((System.nanoTime() - mLastMeasure) + " - " + pStep);
	}

	public static void showTimeMeasuring() {
		if (!Debug.debugOn)
			return;
		for (String iEntry : mEntries)
			System.out.println(iEntry);
	}
}
