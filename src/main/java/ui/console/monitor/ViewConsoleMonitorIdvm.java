package ui.console.monitor;

import java.io.IOException;

import datatypes.Pos;
import exceptions.ExOutOfGrid;
import mvc.Model;
import mvc.View;
import mvc.present.iPresentIdvm;
import mvc.present.iPresentSoup;
import soup.block.iBlock;

public class ViewConsoleMonitorIdvm extends View {

	private static final int cViewSize = 8;
	private iPresentIdvm mIdvm;
	private iPresentSoup mSoup;

	public ViewConsoleMonitorIdvm(Model pModel) {
		super(pModel);
		mSoup = (iPresentSoup) pModel.getPresenter(iPresentSoup.class);
		mIdvm = (iPresentIdvm) pModel.getPresenter(iPresentIdvm.class);
	}

	public void update() {
		clearScreen();
		printStats();
		printGrid();
	}

	private void printGrid() {
		Pos lIdvMidPos = mIdvm.getPos();
		for (int y = lIdvMidPos.y - cViewSize; y < lIdvMidPos.y + cViewSize; y++) {
			String lGridLine = "";
			for (int x = lIdvMidPos.x - cViewSize; x < lIdvMidPos.x + cViewSize; x++) {
				try {
					new Pos(x, y).isInGrid();
					String lBlockChar = getCharForBlock(y, x);
					lGridLine = lGridLine + lBlockChar;
				} catch (ExOutOfGrid e) {
				}
			}
			System.out.println(lGridLine);
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getCharForBlock(int y, int x) {
		String lBlockChar;
		iBlock lBlock = mSoup.getBlock(new Pos(x, y));
		if (lBlock != null) {
			lBlockChar = getPixel(lBlock);
		} else {
			lBlockChar = " ";
		}
		return lBlockChar;
	}

	private String getPixel(iBlock pBlock) {
		switch (pBlock.getBlockType()) {
		case FOOD:
			return "O";
		case ENEMY:
			return "#";
		case LIFE:
			return "L";
		case SENSOR:
			return "S";
		case MOVE:
			return "M";
		case DEFENCE:
			return "D";
		default:
			return "?";
		}
	}

	private void printStats() {
		System.out.print("Steps: " + mIdvm.getStepCount());
		System.out.print(" Alive: " + mIdvm.isAlive());
		System.out.print(" State: " + mIdvm.getState());
		System.out.print(" Pos: " + mIdvm.getPos());
		System.out.println();
	}

	private void clearScreen() {
		try {
			Runtime.getRuntime().exec("clear");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
