package mvc.present;

import java.util.HashMap;

import datatypes.Direction;
import datatypes.Pos;

import soup.idvm.IdvmState;
import soup.idvm.Sensor;

public interface iPresentIdvm {

	HashMap<Pos, Sensor> getDetectedPos();

	Direction getTargetDirection();

	int getStepCount();

	boolean isAlive();

	Boolean isHungry();

	IdvmState getState();

	Pos getPos();
}
