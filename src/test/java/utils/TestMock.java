package utils;

import java.util.ArrayList;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.genes.Genome;
import core.genes.MoveProbability;
import core.soup.block.BlockType;
import core.soup.block.IdvmCell;
import core.soup.idvm.Idvm;
import core.soup.idvm.IdvmState;
import core.soup.idvm.iIdvm;

public class TestMock {
	public static iIdvm getIdvmMock() {
		Genome lGenome;
		IdvmCell[] lCellGrow = new IdvmCell[6];
		lCellGrow[0] = new IdvmCell(BlockType.LIFE, new Pos(0, 0));
		lCellGrow[1] = new IdvmCell(BlockType.SENSOR, new Pos(1, 0));
		lCellGrow[2] = new IdvmCell(BlockType.MOVE, new Pos(0, 1));
		lCellGrow[3] = new IdvmCell(BlockType.DEFENCE, new Pos(1, 1));
		lCellGrow[4] = new IdvmCell(BlockType.MOVE, new Pos(0, 2));
		lCellGrow[5] = new IdvmCell(BlockType.SENSOR, new Pos(1, 1));

		lGenome = new Genome();
		for (IdvmCell iCell : lCellGrow) {
			lGenome.cellGrow.add(new IdvmCell(iCell.getBlockType(), iCell
					.getPosOnIdvm()));
		}
		ArrayList<MoveProbability> lIdlelMoveProbability = new ArrayList<MoveProbability>();
		lIdlelMoveProbability.add(new MoveProbability().setDirection(
				Decisions.LEFT, 1));
		lIdlelMoveProbability.add(new MoveProbability().setDirection(
				Decisions.DOWN, 1));
		lGenome.movementSequences.put(IdvmState.IDLE, lIdlelMoveProbability);
		lGenome.movementSequences.put(IdvmState.FOOD, lIdlelMoveProbability);

		lGenome.setHunger(50);
		return new Idvm(lGenome);
	}
}
