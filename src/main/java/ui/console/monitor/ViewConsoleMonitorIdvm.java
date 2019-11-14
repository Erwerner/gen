package ui.console.monitor;

import java.io.IOException;

import mvc.Model;
import mvc.View;
import mvc.present.iPresentIdvm;
import mvc.present.iPresentSoup;
import soup.block.iBlock;
import datatypes.Pos;

public class ViewConsoleMonitorIdvm extends View {

	private iPresentIdvm mIdvm;
	private iPresentSoup mSoup;

	public ViewConsoleMonitorIdvm(Model pModel) {
		super(pModel);
		mIdvm = (iPresentIdvm) pModel;
		mSoup = (iPresentSoup) pModel;
	}

	public void update() {
		clearScreen();
		printStats();
		printGrid();
	}

	private void printGrid() {
		Pos lIdvMidPos = mIdvm.getPos();
		for (int y = lIdvMidPos.y - 8; y < lIdvMidPos.y + 8; y++) {
			String lGridLine = "";
			for (int x = lIdvMidPos.x - 8; x < lIdvMidPos.x + 10; x++) {
				iBlock lBlock = mSoup.getBlock(new Pos(x, y));
				if (lBlock != null) {
					lGridLine = lGridLine + getPixel(lBlock);
				} else {
					if (x % 5 == 0 && y % 5 == 0) {
						lGridLine = lGridLine + ".";
					} else {
						lGridLine = lGridLine + " ";
					}
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

	private String getPixel(iBlock pBlock) {
		switch (pBlock.getBlockType()) {
		case FOOD:
			return "F";
		case LIFE:
			return "L";
		case SENSOR:
			return "S";
		case MOVE:
			return "M";
		case DEFENCE:
			return "D";
		}
		return "?";
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
