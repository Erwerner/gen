import java.util.ArrayList;

import datatypes.Pos;
import genes.Genome;
import genes.MoveProbability;
import soup.Soup;
import soup.block.BlockType;
import soup.idvm.Idvm;
import soup.idvm.IdvmCell;
import soup.idvm.IdvmState;
import soup.idvm.iIdvm;
import ui.console.ViewConsoleGrid;

public class runMonitor {

	public static void main(String[] args) {
		run();
	}
	public static void run() {

		IdvmCell[] mCellGrow = new IdvmCell[6];
		mCellGrow[0] = new IdvmCell(BlockType.LIFE, new Pos(0, 0));
		mCellGrow[1] = new IdvmCell(BlockType.SENSOR, new Pos(1, 0));
		mCellGrow[2] = new IdvmCell(BlockType.MOVE, new Pos(0, 1));
		mCellGrow[3] = new IdvmCell(BlockType.DEFENCE, new Pos(1, 1));
		mCellGrow[4] = new IdvmCell(BlockType.MOVE, new Pos(0, 2));
		mCellGrow[5] = new IdvmCell(BlockType.SENSOR, new Pos(1, 1));

		Genome mGenome = new Genome();
		for (IdvmCell iCell : mCellGrow) {
			mGenome.cell.add(new IdvmCell(iCell.getBlockType(), iCell
					.getPosOnIdvm()));
		}
		ArrayList<MoveProbability> lIdlelMoveProbability = new ArrayList<MoveProbability>();
		lIdlelMoveProbability.add(new MoveProbability(0, 0, 1, 0));
		lIdlelMoveProbability.add(new MoveProbability(0, 1, 0, 0));
		mGenome.movementSequences.put(IdvmState.IDLE, lIdlelMoveProbability);

		mGenome.hunger = 50;
		
		iIdvm lIdvm = new Idvm(mGenome );
		Soup lSoup = new Soup(lIdvm);
		new ViewConsoleGrid(lSoup);
		
		lSoup.run();
	}
}
