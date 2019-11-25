package ui.console.monitor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import core.datatypes.Pos;
import core.exceptions.PosIsOutOfGrid;
import core.soup.block.iBlock;
import core.soup.idvm.IdvmState;
import core.soup.idvm.Sensor;
import ui.mvc.Model;
import ui.mvc.View;
import ui.presenter.iPresentDevIdvmStats;
import ui.presenter.iPresentIdvm;
import ui.presenter.iPresentSoup;

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

	private static final int cViewSize = 25;
	private iPresentIdvm mIdvm;
	private iPresentSoup mSoup;
	private iPresentDevIdvmStats mIdvStats;

	public ViewConsoleMonitorIdvm(Model pModel) {
		super(pModel);
		mSoup = (iPresentSoup) pModel.getPresenter(iPresentSoup.class);
		mIdvm = (iPresentIdvm) pModel.getPresenter(iPresentIdvm.class);
		mIdvStats = (iPresentDevIdvmStats) pModel.getPresenter(iPresentDevIdvmStats.class);
	}

	public void update() {
		clearScreen();
		printGrid();
		printStats();
	}

	private void printGrid() {
		Pos lIdvPos = mIdvm.getPos();
		HashMap<Pos, Sensor> lDetectedPos = mIdvm.getDetectedPos();
		for (int y = lIdvPos.y - cViewSize; y < lIdvPos.y + cViewSize; y++) {
			String lGridLine = "";
			for (int x = lIdvPos.x - cViewSize; x < lIdvPos.x + cViewSize * 2; x++) {
				String lBlockChar;
				try {
					Pos lCurrentPos = new Pos(x, y);
					lCurrentPos.isInGrid();
					lBlockChar = getCharForBlock(lCurrentPos, lDetectedPos);
				} catch (PosIsOutOfGrid e) {
					lBlockChar = "█";
				}
				lGridLine = lGridLine + lBlockChar;
			}
			System.out.println(lGridLine);
		}
	}

	private String getCharForBlock(Pos pPos, HashMap<Pos, Sensor> pDetectedPos) throws PosIsOutOfGrid {
		String lBlockChar = " ";
		iBlock lBlock = mSoup.getBlock(pPos);
		if (lBlock != null) {
			lBlockChar = getPixel(lBlock);
		} else {
			if (mIdvm.getStepCount() % 2 == 0)
				for (Entry<Pos, Sensor> iDetectedPos : pDetectedPos.entrySet())
					if (iDetectedPos.getKey().equals(pPos))
						return getSensorChar();
		}
		return lBlockChar;
	}

	private String getSensorChar() {
		String lSensorChar = ".";
		if (mIdvm.getState() == IdvmState.IDLE)
			return lSensorChar;
		switch (mIdvm.getCalculatedDirection()) {
		case TARGET:
			lSensorChar = "+";
			break;
		case TARGET_OPPOSITE:
			lSensorChar = "!";
			break;
		case TARGET_SITE1:
		case TARGET_SITE2:
			lSensorChar = "~";
			break;
		}
		return lSensorChar;
	}

	private String getPixel(iBlock pBlock) {
		switch (pBlock.getBlockType()) {
		case FOOD:
			return "©";
		case ENEMY:
			return "Z";
		case LIFE:
			return "❤";
		case SENSOR:
			return "Θ";
		case MOVE:
			return "≈";
		case DEFENCE:
			return "▒";
		default:
			return "?";
		}
	}

	private void printStats() {
		System.out.print("Steps: " + mIdvm.getStepCount());
		System.out.print(" Alive: " + mIdvm.isAlive());
		System.out.print(" State: " + mIdvm.getState());
		System.out.println();
		for (int x = 1; x <= mIdvm.getEnergyCount(); x++)
			System.out.print("*");
		System.out.println();
	}

	private void clearScreen() {
		try {
			Runtime.getRuntime().exec("clear");
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
