package globals;

public class Config {
	private static final int cSoupFctor = 1;
	public static final int soupSize = 100 * cSoupFctor;
	public static final int cMaxSequence = 4 + 16;
	// TODO 7 test mutation rate
	public static final Double cMutationRate = 0.025;

	public static final int enemySupply = 20 * cSoupFctor * cSoupFctor;
	public static final int foodSupply = 30 * cSoupFctor * cSoupFctor;
	public static int cPartnerSupply = foodSupply / 4;

	public static int cPairingCost = 1;
	public static final int cLifeEnergyCost = 1;
	public static final int cMoveEnergyCost = 1;

	public static final int cFoodEnergy = (cLifeEnergyCost + cMoveEnergyCost) * 120;
	public static final int cMaxEnergy = cFoodEnergy * 6;

	public static final int cSensorRange = 4;

	public static final Double cChanceCopyDirections = 0.5;
	public static final Double cChanceResetDirections = 0.7;
}
