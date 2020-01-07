package globals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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

	public static Double rndDouble(Double pMin, Double pMax) {
		return pMin + (pMax - pMin) * mRandom.nextDouble();
	}

	public void waitForConfigFlag(String pFilename) {
		while (!isFlagTrue(pFilename))
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	public Boolean isFlagTrue(String pFilename) {
		try {
			Boolean lExit = false;
			ClassLoader classLoader = getClass().getClassLoader();

			URL resource = classLoader.getResource(pFilename);
			File file = new File(resource.getFile());
			FileReader reader;
			reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.equals("X")) {
					lExit = true;
				}
			}
			reader.close();
			br.close();
			return lExit;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
