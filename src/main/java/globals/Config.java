package globals;

public class Config {
	private static final Double cSoupFctor = 1.5;
	public static final int soupSize = (int) (100 * cSoupFctor);
	// TODO 1 test mutation rate
	public static final Double cMutationRate = 0.025;

	public static final int enemySupply = (int) (14 * cSoupFctor * cSoupFctor);
	public static final int foodSupply = (int) (82 * cSoupFctor * cSoupFctor);
	public static int cPartnerSupply = foodSupply / 3; // 3

	public static final int cMaxSequence = 4 + 30;
	public static final int cLifeEnergyCost = 1;
	public static final int cMoveEnergyCost = 1;

	public static final int cFoodEnergy = (cLifeEnergyCost + cMoveEnergyCost) * 180;
	public static int cPairingCost = cFoodEnergy * 2;
	public static int cGrowCost = cFoodEnergy * 1;
	public static int cInitialEnergy = cFoodEnergy + cGrowCost * (4 + 3);
	public static final int cMaxEnergy = cFoodEnergy * 10;

	public static final int cSensorRange = 4;

	public static final Double cChanceCopyDirections = 0.5;
	public static final Double cChanceResetDirections = 0.7;
}
