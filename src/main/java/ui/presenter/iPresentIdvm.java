package ui.presenter;

import java.util.HashMap;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.soup.idvm.IdvmState;
import core.soup.idvm.Sensor;

public interface iPresentIdvm {

	HashMap<Pos, Sensor> getDetectedPos();

	Decisions getTargetDirection();

	int getStepCount();

	boolean isAlive();

	Boolean isHungry();

	IdvmState getState();

	Pos getPos();

	int getEnergyCount();

	Decisions getCalculatedDirection();
}
