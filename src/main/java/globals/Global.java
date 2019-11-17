package globals;

import java.util.Random;

public class Global {

	public static boolean checkChance(Double pChance) {
		double lRnd = new Random().nextDouble();
		return pChance >= lRnd;
	}

}
