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
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	private static final int cViewSize = 20;
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
				String lBlockChar;
				try {
					new Pos(x, y).isInGrid();
					lBlockChar = getCharForBlock(y, x);
				} catch (ExOutOfGrid e) {
					lBlockChar = "+";
				}
				lGridLine = lGridLine + lBlockChar;
			}
			System.out.println(lGridLine);
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String getCharForBlock(int y, int x) throws ExOutOfGrid {
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
