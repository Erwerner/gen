package globals;

public class Config {
	private static final int cSoupFctor = 1;
	public static final int soupSize = 100 * cSoupFctor;
	public static final int cMaxSequence = 4 + 16;
	//TODO 7 test mutation rate
	public static final Double cMutationRate = 0.025;
	
	public static final int enemySupply = 24 * cSoupFctor * cSoupFctor;
	public static final int foodSupply = 46 * cSoupFctor * cSoupFctor;
	public static final int cLifeEnergyCount = 2;
	public static final int cMoveEnergyCount = 1;
	public static final int cFoodEnergy = (cLifeEnergyCount + cMoveEnergyCount ) * 80;
	public static final int cMaxEnergy = cFoodEnergy *6;
	public static final int cSensorRange = 3;
	public static final Double cChanceCopyDirections = 0.5;
	public static final Double cChanceResetDirections = 0.7;
}
