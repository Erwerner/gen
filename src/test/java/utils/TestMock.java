package utils;

import java.util.ArrayList;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.genes.Genome;
import core.genes.MoveDecisionsProbability;
import core.soup.block.BlockType;
import core.soup.block.IdvmCell;
import core.soup.idvm.Idvm;
import core.soup.idvm.IdvmState;

public class TestMock {
	public static Idvm getIdvmMock() {
		Genome lGenome;
		lGenome = new Genome();
		lGenome.cellGrow = new ArrayList<IdvmCell>();
		lGenome.cellGrow.add(0, new IdvmCell(BlockType.LIFE, new Pos(0, 0)));
		lGenome.cellGrow.add(1, new IdvmCell(BlockType.SENSOR, new Pos(1, 0)));
		lGenome.cellGrow.add(2, new IdvmCell(BlockType.MOVE, new Pos(0, 1)));
		lGenome.cellGrow.add(3, new IdvmCell(BlockType.DEFENCE, new Pos(1, 1)));
		lGenome.cellGrow.add(4, new IdvmCell(BlockType.MOVE, new Pos(0, 2)));
		lGenome.cellGrow.add(5, new IdvmCell(BlockType.SENSOR, new Pos(1, 1)));

		lGenome.moveSequencesForState.clear();
		for (IdvmState iState : IdvmState.values()) {
			ArrayList<MoveDecisionsProbability> lIdlelMoveProbability = new ArrayList<MoveDecisionsProbability>();
			lIdlelMoveProbability.add(new MoveDecisionsProbability(iState).appendDecision(Decisions.LEFT, 1));
			lIdlelMoveProbability.add(new MoveDecisionsProbability(iState).appendDecision(Decisions.DOWN, 1));
			lGenome.moveSequencesForState.put(iState,
					(ArrayList<MoveDecisionsProbability>) lIdlelMoveProbability.clone());
		}

		lGenome.setHunger(50);
		return new Idvm(lGenome);
	}
}
