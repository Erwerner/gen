package genes;

import java.util.ArrayList;
import java.util.HashMap;

import datatypes.Pos;

import soup.block.BlockType;
import soup.idvm.IdvmCell;
import soup.idvm.IdvmState;

public class Genome {
	private static final int cMaxSequence = 48;
	public int hunger;
	public ArrayList<IdvmCell> cellGrow = new ArrayList<IdvmCell>();
	public HashMap<IdvmState, ArrayList<MoveProbability>> movementSequences = new HashMap<IdvmState, ArrayList<MoveProbability>>();

	public Genome mutate() {
		mutateHunger();
		for (int iIdx = 0; iIdx < cMaxSequence; iIdx++) {
			mutateCellGrow(iIdx);
			mutateMoveSequences(iIdx);
		}
		return this;
	}

	private void mutateMoveSequences(int pIdx) {
	}

	private void mutateCellGrow(int pIdx) {
		if (pIdx < 4) {
			IdvmCell lNewCell = new IdvmCell(BlockType.LIFE, new Pos(pIdx % 2,
					pIdx % 2));
			cellGrow.add(lNewCell);
		}
	}

	private void mutateHunger() {
		// TODO Auto-generated method stub

	}
}
