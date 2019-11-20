package globals;

import java.util.ArrayList;
import java.util.Random;

public class Helpers {

	private static Random mRandom = new Random();

	public static boolean checkChance(Double pChance) {
		double lRnd = mRandom.nextDouble();
		return pChance >= lRnd;
	}

	public static Object rndArrayEntry(Object[] pArray) {
		int lRnd = mRandom.nextInt(pArray.length);
		return pArray[lRnd];
	}

	public static int rndIntRange(int pMin, int pMax) {
		return mRandom.nextInt(pMax - pMin + 1) + pMin;
	}

	public static int rndInt(int pMax) {
		return mRandom.nextInt(pMax + 1);
	}
}
