package core.soup.idvm;

public enum IdvmState {
	IDLE, FOOD, ENEMY, BLIND, IDLE_HUNGER, FOOD_HUNGER, ENEMY_HUNGER, PARTNER, PARTNER_HUNGER;
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
