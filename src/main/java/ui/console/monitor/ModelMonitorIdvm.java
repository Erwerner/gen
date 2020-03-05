package ui.console.monitor;

import java.util.ArrayList;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.genes.Genome;
import core.genes.MoveDecisionsProbability;
import core.soup.EnvironmentConfig;
import core.soup.Soup;
import core.soup.iSoup;
import core.soup.block.BlockType;
import core.soup.block.IdvmCell;
import core.soup.idvm.Idvm;
import core.soup.idvm.IdvmState;
import globals.Helpers;
import ui.controller.iControllRunSoup;
import ui.mvc.Model;
import ui.presenter.WrongPresenterType;
import ui.presenter.iPresentDevIdvmStats;
import ui.presenter.iPresentIdvm;
import ui.presenter.iPresentSoup;

public class ModelMonitorIdvm extends Model implements iControllRunSoup {

	Idvm mIdvm;
	iSoup mSoup;

	public void run() {
		Genome mGenome = initializeGenome();

		runGenome((Genome) mGenome);
	}

	public static Genome initializeGenome() {
		Genome lGenome = new Genome().forceMutation();
		IdvmCell[] mCellGrow = new IdvmCell[7];
		mCellGrow[0] = new IdvmCell(BlockType.LIFE, new Pos(0, 0));
		mCellGrow[1] = new IdvmCell(BlockType.SENSOR, new Pos(1, 0));
		mCellGrow[2] = new IdvmCell(BlockType.SENSOR, new Pos(0, 1));
		mCellGrow[3] = new IdvmCell(BlockType.MOVE, new Pos(1, 1));
		mCellGrow[4] = new IdvmCell(BlockType.SENSOR, new Pos(2, 2));
		mCellGrow[5] = new IdvmCell(BlockType.SENSOR, new Pos(-1, -1));
		mCellGrow[6] = new IdvmCell(BlockType.SENSOR, new Pos(-1, 1));
		for (IdvmCell iCell : mCellGrow) {
			lGenome.cellGrow.add(new IdvmCell(iCell.getBlockType(), iCell.getPosOnIdvm()));
		}
		ArrayList<MoveDecisionsProbability> lIdlelMoveProbability = new ArrayList<MoveDecisionsProbability>();
		lIdlelMoveProbability
				.add(new MoveDecisionsProbability().appendDecision(Decisions.DOWN, 1).appendDecision(Decisions.RIGHT, 5)
						.appendDecision(Decisions.CURRENT, 10).appendDecision(Decisions.CURRENT_OPPOSITE, 1));
		lIdlelMoveProbability
				.add(new MoveDecisionsProbability().appendDecision(Decisions.UP, 4).appendDecision(Decisions.LEFT, 2)
						.appendDecision(Decisions.RIGHT, 1).appendDecision(Decisions.NOTHING, 1));
		lIdlelMoveProbability.add(new MoveDecisionsProbability().appendDecision(Decisions.DOWN, 1)
				.appendDecision(Decisions.RIGHT, 1).appendDecision(Decisions.NOTHING, 1));
		lIdlelMoveProbability.add(new MoveDecisionsProbability().appendDecision(Decisions.UP, 1)
				.appendDecision(Decisions.RIGHT, 1).appendDecision(Decisions.NOTHING, 1));
		lIdlelMoveProbability.add(new MoveDecisionsProbability().appendDecision(Decisions.DOWN, 1)
				.appendDecision(Decisions.LEFT, 2).appendDecision(Decisions.NOTHING, 1));
		lGenome.moveSequencesForState.put(IdvmState.IDLE, lIdlelMoveProbability);
		ArrayList<MoveDecisionsProbability> lFoodMoveProbability = new ArrayList<MoveDecisionsProbability>();
		lFoodMoveProbability.add(new MoveDecisionsProbability().appendDecision(Decisions.TARGET, 1));
		lFoodMoveProbability.add(new MoveDecisionsProbability().appendDecision(Decisions.TARGET, 1));
		lFoodMoveProbability.add(new MoveDecisionsProbability().appendDecision(Decisions.TARGET, 1));
		lFoodMoveProbability.add(new MoveDecisionsProbability().appendDecision(Decisions.TARGET, 1));
		lFoodMoveProbability.add(new MoveDecisionsProbability().appendDecision(Decisions.TARGET, 1));
		lGenome.moveSequencesForState.put(IdvmState.FOOD, lFoodMoveProbability);
		ArrayList<MoveDecisionsProbability> lEnemyMoveProbability = new ArrayList<MoveDecisionsProbability>();
		lEnemyMoveProbability.add(new MoveDecisionsProbability().appendDecision(Decisions.TARGET_OPPOSITE, 1));
		lEnemyMoveProbability.add(new MoveDecisionsProbability().appendDecision(Decisions.TARGET_OPPOSITE, 1));
		lEnemyMoveProbability.add(new MoveDecisionsProbability().appendDecision(Decisions.TARGET_OPPOSITE, 1));
		lEnemyMoveProbability.add(new MoveDecisionsProbability().appendDecision(Decisions.TARGET_OPPOSITE, 1));
		lEnemyMoveProbability.add(new MoveDecisionsProbability().appendDecision(Decisions.TARGET_OPPOSITE, 1));
		lGenome.moveSequencesForState.put(IdvmState.ENEMY, lEnemyMoveProbability);

		lGenome.setHunger(50);

		return lGenome;
	}

	public void runGenome(Genome mGenome) {
		mIdvm = new Idvm(mGenome);
		mSoup = new Soup(mIdvm, new EnvironmentConfig());
		new ViewConsoleMonitorIdvm(this);

		System.out.println("start");
		notifyViews();
		while (mIdvm.isAlive() && new Helpers().isFlagTrue("monitor")) {
			try {
				Thread.sleep(200);
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
		if (pType == iPresentDevIdvmStats.class)
			return mIdvm;
		throw new WrongPresenterType();
	}
}
