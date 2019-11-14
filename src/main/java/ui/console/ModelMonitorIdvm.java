package ui.console;

import java.util.HashMap;

import soup.block.iBlock;
import soup.idvm.IdvmState;
import soup.idvm.Sensor;
import datatypes.Direction;
import datatypes.Pos;
import mvc.present.iPresentIdvm;
import mvc.present.iPresentSoup;

public class ModelMonitorIdvm implements iPresentIdvm, iPresentSoup {

	iPresentIdvm mIdvm;
	iPresentSoup mSoup;
	public iBlock getBlock(Pos pPos) {
		return mSoup.getBlock(pPos);
	}

	public HashMap<Pos, Sensor> getDetectedPos() {
		return mIdvm.getDetectedPos();
	}

	public Direction getTargetDirection() {
		return mIdvm.getTargetDirection();
	}

	public int getStepCount() {
		return mIdvm.getStepCount();
	}

	public boolean isAlive() {
		return mIdvm.isAlive();
	}

	public Boolean isHungry() {
		return mIdvm.isHungry();
	}

	public IdvmState getState() {
		return mIdvm.getState();
	}

	public Pos getPos() {
		return mIdvm.getPos();
	}

}
