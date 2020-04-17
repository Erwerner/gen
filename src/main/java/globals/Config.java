package globals;

public class Config {
	public static final int cInitialPopulationMultiplikator = 1;
	public static final int cPopulation = 1024 * 3 * cInitialPopulationMultiplikator;
	public static final Double cSoupFctor = 1.0;
	public static final int cSoupSize = (int) (150 * cSoupFctor);
	// TODO 0 test mutation rate
	public static final Double cMutationRate = 0.025;

	public static final int cEnemySupply = (int) (30 * cSoupFctor * cSoupFctor);
	public static final int cFoodSupply = (int) (150 * cSoupFctor * cSoupFctor);
	public static int cPartnerSupply = cFoodSupply / 3; // 6; // 3

	public static final int cMaxSequence = 4 + 25;
	public static final int cLifeEnergyCost = 2;
	public static final int cMoveEnergyCost = 0;

	public static final int cFoodEnergy = (cLifeEnergyCost + cMoveEnergyCost) * 180;
	public static int cPairingCost = cFoodEnergy * 2;
	public static int cGrowCost = cFoodEnergy * 0;
	public static final int cMaxEnergy = cFoodEnergy * 10;
	public static int cInitialEnergy = cFoodEnergy * 2 + cGrowCost * (4 + 3);
	public static int cMaxHunger = cMaxEnergy;

	public static final int cSensorRange = 4;

	public static final Double cChanceCopyPreviousDirections = 0.5;
	public static final Double cChanceResetDirections = 0.7;
}
