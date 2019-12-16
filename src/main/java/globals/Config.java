package globals;

public class Config {
	private static final int cSoupFctor = 1;
	public static final int soupSize = 100 * cSoupFctor;
	public static final int cMaxSequence = 100;
	public static final Double cMutationRate = 0.025;
	
	public static final int enemySupply = 20 * cSoupFctor * cSoupFctor;
	public static final int foodSupply = 48 * cSoupFctor * cSoupFctor;
	public static final int cFoodEnergy = 250;
	public static final int cMaxEnergy = cFoodEnergy *10;
	public static final int cLifeEnergyCount = 1;
	public static final int cMoveEnergyCount = 1;
	public static final int cSensorRange = 4;
	public static final Double cChanceCopyDirections = 0.5;
	public static final Double cChanceResetDirections = 0.7;
}
