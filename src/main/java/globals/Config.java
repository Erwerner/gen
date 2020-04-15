package globals;

public class Config {
	private static final Double cSoupFctor = 1.0;
	public static final int cSoupSize = (int) (100 * cSoupFctor);
	// TODO 0 test mutation rate
	public static final Double cMutationRate = 0.025;

	public static final int cEnemySupply = (int) (10 * cSoupFctor * cSoupFctor);
	public static final int cFoodSupply = (int) (100 * cSoupFctor * cSoupFctor);
	public static int cPartnerSupply = cFoodSupply / 2; // 6; // 3

	public static final int cMaxSequence = 4 + 30;
	public static final int cLifeEnergyCost = 2;
	public static final int cMoveEnergyCost = 0;

	public static final int cFoodEnergy = (cLifeEnergyCost + cMoveEnergyCost) * 180;
	public static int cPairingCost = cFoodEnergy * 2;
	public static int cGrowCost = cFoodEnergy * 0;
	public static final int cMaxEnergy = cFoodEnergy * 10;
	public static int cInitialEnergy = cFoodEnergy * 1 + cGrowCost * (4 + 3);
	public static int cMaxHunger = cMaxEnergy;

	public static final int cSensorRange = 4;

	public static final Double cChanceCopyDirections = 0.5;
	public static final Double cChanceResetDirections = 0.7;
}
