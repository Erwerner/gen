package devutils;

import globals.Config;

public class Debug {
	public static boolean debugOn = true;
	public static String currentChange = "ChanceResetDiriection: " + Config.cChanceResetDirections + "\ndies partner"
			+ "/nEnemySupply: " + Config.cEnemySupply
			+ "\nPop: " + Config.cPopulation + "\nmaxhunger: " + Config.cMaxHunger / Config.cFoodEnergy
			+ "\ninitial Energy: " + Config.cInitialEnergy / Config.cFoodEnergy + "\nPairing Cost: "
			+ Config.cPairingCost / Config.cFoodEnergy + "\nFoodSupply: " + Config.cFoodSupply + "\nPartnerSupply: "
			+ Config.cFoodSupply / Config.cPartnerSupply;

	public static void printCurrentChange() {
		if (debugOn)
			if (currentChange != "")
				System.out.println(currentChange);
	}
}
