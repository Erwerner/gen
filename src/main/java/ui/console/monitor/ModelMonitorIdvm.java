package ui.console.monitor;

import java.util.ArrayList;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.genes.Genome;
import core.genes.MoveProbability;
import core.soup.Soup;
import core.soup.iSoup;
import core.soup.block.BlockType;
import core.soup.block.IdvmCell;
import core.soup.idvm.Idvm;
import core.soup.idvm.IdvmState;
import core.soup.idvm.iIdvm;
import ui.controller.iControllRunSoup;
import ui.mvc.Model;
import ui.presenter.WrongPresenterType;
import ui.presenter.iPresentIdvm;
import ui.presenter.iPresentSoup;

public class ModelMonitorIdvm extends Model implements iControllRunSoup {

	iIdvm mIdvm;
	iSoup mSoup;

	public void run() {
		Genome mGenome = initializeGenome();
		
			runGenome((Genome) mGenome);
	}

	public static Genome initializeGenome() {
		Genome lGenome = new Genome();
		IdvmCell[] mCellGrow = new IdvmCell[7];
		mCellGrow[0] = new IdvmCell(BlockType.LIFE, new Pos(0, 0));
		mCellGrow[1] = new IdvmCell(BlockType.SENSOR, new Pos(1, 0));
		mCellGrow[2] = new IdvmCell(BlockType.SENSOR, new Pos(0, 1));
		mCellGrow[3] = new IdvmCell(BlockType.MOVE, new Pos(1, 1));
		mCellGrow[4] = new IdvmCell(BlockType.SENSOR, new Pos(2, 2));
		mCellGrow[5] = new IdvmCell(BlockType.SENSOR, new Pos(-1, -1));
		mCellGrow[6] = new IdvmCell(BlockType.SENSOR, new Pos(-1, 1));
		for (IdvmCell iCell : mCellGrow) {
			lGenome.cellGrow.add(new IdvmCell(iCell.getBlockType(), iCell
					.getPosOnIdvm()));
		}
		ArrayList<MoveProbability> lIdlelMoveProbability = new ArrayList<MoveProbability>();
		lIdlelMoveProbability.add(new MoveProbability()
				.setDirection(Decisions.DOWN, 1)
				.setDirection(Decisions.RIGHT, 5)
				.setDirection(Decisions.CURRENT, 10)
				.setDirection(Decisions.CURRENT_OPPOSITE, 1));
		lIdlelMoveProbability.add(new MoveProbability()
				.setDirection(Decisions.UP, 4).setDirection(Decisions.LEFT, 2)
				.setDirection(Decisions.RIGHT, 1)
				.setDirection(Decisions.NOTHING, 1));
		lIdlelMoveProbability.add(new MoveProbability()
				.setDirection(Decisions.DOWN, 1)
				.setDirection(Decisions.RIGHT, 1)
				.setDirection(Decisions.NOTHING, 1));
		lIdlelMoveProbability.add(new MoveProbability()
				.setDirection(Decisions.UP, 1).setDirection(Decisions.RIGHT, 1)
				.setDirection(Decisions.NOTHING, 1));
		lIdlelMoveProbability.add(new MoveProbability()
				.setDirection(Decisions.DOWN, 1)
				.setDirection(Decisions.LEFT, 2)
				.setDirection(Decisions.NOTHING, 1));
		lGenome.movementSequences.put(IdvmState.IDLE, lIdlelMoveProbability);
		ArrayList<MoveProbability> lFoodMoveProbability = new ArrayList<MoveProbability>();
		lFoodMoveProbability.add(new MoveProbability().setDirection(
				Decisions.TARGET, 1));
		lFoodMoveProbability.add(new MoveProbability().setDirection(
				Decisions.TARGET, 1));
		lFoodMoveProbability.add(new MoveProbability().setDirection(
				Decisions.TARGET, 1));
		lFoodMoveProbability.add(new MoveProbability().setDirection(
				Decisions.TARGET, 1));
		lFoodMoveProbability.add(new MoveProbability().setDirection(
				Decisions.TARGET, 1));
		lGenome.movementSequences.put(IdvmState.FOOD, lFoodMoveProbability);
		ArrayList<MoveProbability> lEnemyMoveProbability = new ArrayList<MoveProbability>();
		lEnemyMoveProbability.add(new MoveProbability().setDirection(
				Decisions.TARGET_OPPOSITE, 1));
		lEnemyMoveProbability.add(new MoveProbability().setDirection(
				Decisions.TARGET_OPPOSITE, 1));
		lEnemyMoveProbability.add(new MoveProbability().setDirection(
				Decisions.TARGET_OPPOSITE, 1));
		lEnemyMoveProbability.add(new MoveProbability().setDirection(
				Decisions.TARGET_OPPOSITE, 1));
		lEnemyMoveProbability.add(new MoveProbability().setDirection(
				Decisions.TARGET_OPPOSITE, 1));
		lGenome.movementSequences.put(IdvmState.ENEMY, lEnemyMoveProbability);

		lGenome.setHunger(50);

		return lGenome;
	}

	public void runGenome(Genome mGenome) {
		mIdvm = new Idvm(mGenome);
		mSoup = new Soup(mIdvm);
		new ViewConsoleMonitorIdvm(this);

		System.out.println("start");
		notifyViews();
		while (mIdvm.isAlive()) {
			try {
				Thread.sleep(140);
				mSoup.step();
				notifyViews();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("stop");
	}

	@Override
	public Object getPresenter(Class pType) {
		if (pType == iPresentSoup.class)
			return mSoup;
		if (pType == iPresentIdvm.class)
			return mIdvm;
		throw new WrongPresenterType();
	}
}
