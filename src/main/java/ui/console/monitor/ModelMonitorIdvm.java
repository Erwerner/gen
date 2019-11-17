package ui.console.monitor;

import genes.Genome;
import genes.MoveProbability;

import java.util.ArrayList;

import mvc.Model;
import mvc.controller.iControllRunSoup;
import mvc.present.iPresentIdvm;
import mvc.present.iPresentSoup;
import soup.Soup;
import soup.iSoup;
import soup.block.BlockType;
import soup.idvm.Idvm;
import soup.idvm.IdvmCell;
import soup.idvm.IdvmState;
import soup.idvm.iIdvm;
import datatypes.Direction;
import datatypes.Pos;
import exceptions.ExWrontPresenterType;

public class ModelMonitorIdvm extends Model implements iControllRunSoup {

	iIdvm mIdvm;
	iSoup mSoup;

	public void run() {
		IdvmCell[] mCellGrow = new IdvmCell[7];
		mCellGrow[0] = new IdvmCell(BlockType.LIFE, new Pos(0, 0));
		mCellGrow[1] = new IdvmCell(BlockType.SENSOR, new Pos(1, 0));
		mCellGrow[2] = new IdvmCell(BlockType.SENSOR, new Pos(0, 1));
		mCellGrow[3] = new IdvmCell(BlockType.MOVE, new Pos(1, 1));
		mCellGrow[4] = new IdvmCell(BlockType.MOVE, new Pos(2, 2));
		mCellGrow[5] = new IdvmCell(BlockType.SENSOR, new Pos(0, -1));
		mCellGrow[6] = new IdvmCell(BlockType.SENSOR, new Pos(-1, 0));
		Genome mGenome = new Genome();
		for (IdvmCell iCell : mCellGrow) {
			mGenome.cellGrow.add(new IdvmCell(iCell.getBlockType(), iCell
					.getPosOnIdvm()));
		}
		ArrayList<MoveProbability> lIdlelMoveProbability = new ArrayList<MoveProbability>();
		lIdlelMoveProbability.add(new MoveProbability(1, 0, 0, 5, 0, 0)
				.setDirection(Direction.DOWN, 1)
				.setDirection(Direction.RIGHT, 5)
				.setDirection(Direction.CURRENT, 10)
				.setDirection(Direction.CURRENT_OPPOSITE, 1));
		lIdlelMoveProbability.add(new MoveProbability(4, 0, 2, 1, 1, 0)
				.setDirection(Direction.UP, 4).setDirection(Direction.LEFT, 2)
				.setDirection(Direction.RIGHT, 1)
				.setDirection(Direction.NOTHING, 1));
		lIdlelMoveProbability.add(new MoveProbability(0, 1, 0, 1, 1, 0));
		lIdlelMoveProbability.add(new MoveProbability(1, 0, 0, 1, 1, 0));
		lIdlelMoveProbability.add(new MoveProbability(0, 2, 1, 0, 1, 0));
		mGenome.movementSequences.put(IdvmState.IDLE, lIdlelMoveProbability);
		ArrayList<MoveProbability> lFoodMoveProbability = new ArrayList<MoveProbability>();
		lFoodMoveProbability.add(new MoveProbability(0, 0, 0, 0, 0, 0)
				.setDirection(Direction.TARGET, 1));
		lFoodMoveProbability.add(new MoveProbability(0, 0, 0, 0, 0, 0)
				.setDirection(Direction.TARGET, 1));
		lFoodMoveProbability.add(new MoveProbability(0, 0, 0, 0, 0, 0)
				.setDirection(Direction.TARGET, 1));
		lFoodMoveProbability.add(new MoveProbability(0, 0, 0, 0, 0, 0)
				.setDirection(Direction.TARGET, 1));
		lFoodMoveProbability.add(new MoveProbability(0, 0, 0, 0, 0, 0)
				.setDirection(Direction.TARGET, 1));
		mGenome.movementSequences.put(IdvmState.FOOD, lFoodMoveProbability);
		ArrayList<MoveProbability> lEnemyMoveProbability = new ArrayList<MoveProbability>();
		lEnemyMoveProbability.add(new MoveProbability(0, 0, 0, 0, 0, 0)
				.setDirection(Direction.TARGET_OPPOSITE, 1));
		lEnemyMoveProbability.add(new MoveProbability(0, 0, 0, 0, 0, 0)
				.setDirection(Direction.TARGET_OPPOSITE, 1));
		lEnemyMoveProbability.add(new MoveProbability(0, 0, 0, 0, 0, 0)
				.setDirection(Direction.TARGET_OPPOSITE, 1));
		lEnemyMoveProbability.add(new MoveProbability(0, 0, 0, 0, 0, 0)
				.setDirection(Direction.TARGET_OPPOSITE, 1));
		lEnemyMoveProbability.add(new MoveProbability(0, 0, 0, 0, 0, 0)
				.setDirection(Direction.TARGET_OPPOSITE, 1));
		mGenome.movementSequences.put(IdvmState.ENEMY, lEnemyMoveProbability);

		mGenome.setHunger(50);
		runGenome(mGenome);

	}

	public void runGenome(Genome mGenome) {
		mGenome.forceMutation();
		mIdvm = new Idvm(mGenome);
		mSoup = new Soup(mIdvm);
		new ViewConsoleMonitorIdvm(this);

		System.out.println("start");
		notifyViews();
		while (mIdvm.isAlive()) {
			try {
				Thread.sleep(70);
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
		throw new ExWrontPresenterType();
	}
}
