package core.soup.idvm;

public enum IdvmState {
	//TODO 1 Hunger
	IDLE, FOOD, ENEMY, BLIND, PARTNER, IDLE_HUNGER, FOOD_HUNGER, ENEMY_HUNGER, PARTNER_HUNGER;
	
	 public IdvmState getStateAsHunger() {
		switch (this) {
		case IDLE:
			return IDLE_HUNGER;
		case FOOD:
			return FOOD_HUNGER;
		case ENEMY:
			return ENEMY_HUNGER;
		case BLIND:
			return BLIND;
		case PARTNER:
			return PARTNER_HUNGER;
		default:
			throw new RuntimeException();
		}
	}
	 
}
