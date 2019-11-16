package genes;

import java.util.ArrayList;
import java.util.HashMap;

import datatypes.Pos;
import soup.block.BlockType;
import soup.idvm.IdvmCell;
import soup.idvm.IdvmState;

public class Genome implements iGene{
	public Genome()  {
		mGenes.add(mHunger);
	}

	private static final int cMaxSequence = 48;
	ArrayList<Gene> mGenes = new ArrayList<Gene>();
	private GeneInt mHunger = new GeneInt(0, 100, 50);
	public ArrayList<IdvmCell> cellGrow = new ArrayList<IdvmCell>();
	public HashMap<IdvmState, ArrayList<MoveProbability>> movementSequences = new HashMap<IdvmState, ArrayList<MoveProbability>>();

	//TODO 1 IMPL Randomize Genome
	public void mutate() {
		for (Gene iGene : mGenes) {
			iGene.mutate();
		}
		for (int iIdx = 0; iIdx < cMaxSequence; iIdx++) {
			mutateCellGrow(iIdx);
			mutateMoveSequences(iIdx);
		}
	}

	private void mutateMoveSequences(int pIdx) {
	}

	private void mutateCellGrow(int pIdx) {
		if (pIdx < 4) {
			IdvmCell lNewCell = new IdvmCell(BlockType.LIFE, new Pos(pIdx % 2, pIdx % 2));
			cellGrow.add(lNewCell);
		}
	}

	public int getHunger() {
		return mHunger.getValue();
	}

	public void setHunger(int pInt) {
		mHunger.setValue(pInt);
	}
}
