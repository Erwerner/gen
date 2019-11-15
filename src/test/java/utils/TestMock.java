package utils;

import genes.Genome;
import genes.MoveProbability;

import java.util.ArrayList;

import datatypes.Pos;
import soup.block.BlockType;
import soup.idvm.Idvm;
import soup.idvm.IdvmCell;
import soup.idvm.IdvmState;
import soup.idvm.iIdvm;

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
			lGenome.cellGrow.add(new IdvmCell(iCell.getBlockType(), iCell.getPosOnIdvm()));
		}
		ArrayList<MoveProbability> lIdlelMoveProbability = new ArrayList<MoveProbability>();
		lIdlelMoveProbability.add(new MoveProbability(0, 0, 1, 0, 0));
		lIdlelMoveProbability.add(new MoveProbability(0, 1, 0, 0, 0));
		lGenome.movementSequences.put(IdvmState.IDLE, lIdlelMoveProbability);

		lGenome.hunger = 50;
		return new Idvm(lGenome);
	}
}
