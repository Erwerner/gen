package ui.console;

import genes.Genome;
import genes.MoveProbability;

import java.util.ArrayList;
import java.util.HashMap;

import org.hamcrest.core.IsInstanceOf;

import mvc.Model;
import mvc.controller.iControllRunSoup;
import mvc.present.iPresentIdvm;
import mvc.present.iPresentSoup;
import soup.Soup;
import soup.iSoup;
import soup.block.BlockType;
import soup.block.iBlock;
import soup.idvm.Idvm;
import soup.idvm.IdvmCell;
import soup.idvm.IdvmState;
import soup.idvm.Sensor;
import soup.idvm.iIdvm;
import ui.console.monitor.ViewConsoleMonitorIdvm;
import datatypes.Direction;
import datatypes.Pos;
import exceptions.ExWrontPresenterType;

public class ModelMonitorIdvm extends Model implements iControllRunSoup {

	iIdvm mIdvm;
	iSoup mSoup;

	public void run() {
		IdvmCell[] mCellGrow = new IdvmCell[6];
		mCellGrow[0] = new IdvmCell(BlockType.LIFE, new Pos(0, 0));
		mCellGrow[1] = new IdvmCell(BlockType.SENSOR, new Pos(1, 0));
		mCellGrow[2] = new IdvmCell(BlockType.MOVE, new Pos(0, 1));
		mCellGrow[3] = new IdvmCell(BlockType.DEFENCE, new Pos(1, 1));
		mCellGrow[4] = new IdvmCell(BlockType.MOVE, new Pos(0, 2));
		mCellGrow[5] = new IdvmCell(BlockType.SENSOR, new Pos(-1, -1));
		mCellGrow[5] = new IdvmCell(BlockType.SENSOR, new Pos(2, 2));
		Genome mGenome = new Genome();
		for (IdvmCell iCell : mCellGrow) {
			mGenome.cellGrow.add(new IdvmCell(iCell.getBlockType(), iCell.getPosOnIdvm()));
		}
		ArrayList<MoveProbability> lIdlelMoveProbability = new ArrayList<MoveProbability>();
		lIdlelMoveProbability.add(new MoveProbability(4, 0, 2, 1));
		lIdlelMoveProbability.add(new MoveProbability(0, 1, 0, 1));
		lIdlelMoveProbability.add(new MoveProbability(1, 0, 0, 1));
		lIdlelMoveProbability.add(new MoveProbability(0, 2, 1, 0));
		mGenome.movementSequences.put(IdvmState.IDLE, lIdlelMoveProbability);
		mGenome.movementSequences.put(IdvmState.FOOD, lIdlelMoveProbability);

		mGenome.hunger = 50;

		mIdvm = new Idvm(mGenome);
		mSoup = new Soup(mIdvm);
		new ViewConsoleMonitorIdvm(this);

		System.out.println("start");
		notifyViews();
		while (mIdvm.isAlive()) {
			mSoup.step();
			notifyViews();
		}
		System.out.println("stop");

	}

	@Override
	public Object getPresenter(Class pType) {
		if (pType == iPresentSoup.class)
			return mSoup;
		if (pType == iPresentIdvm.class)
			return mIdvm;
		throw new ExWrontPresenterType();
	}
}
