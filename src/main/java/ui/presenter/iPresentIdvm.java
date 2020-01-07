package ui.presenter;

import java.util.HashMap;

import core.datatypes.Decisions;
import core.datatypes.Direction;
import core.datatypes.Pos;
import core.soup.idvm.IdvmState;
import core.soup.idvm.Sensor;

public interface iPresentIdvm {

	HashMap<Pos, Sensor> getDetectedPos();

	Direction getTargetDirection();

	int getStepCount();

	boolean isAlive();

	Boolean isHungry();

	IdvmState getState();

	Pos getPos();

	int getEnergyCount();

	int getPartnerCount();

	Decisions getCalculatedDirection();
}
