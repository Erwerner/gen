package genes;

import java.util.ArrayList;
import java.util.HashMap;

import soup.block.BlockType;
import soup.idvm.IdvmCell;
import soup.idvm.IdvmState;
import datatypes.Pos;

public class Genome {
	public Genome() {
		mGenes.add(mHunger);
		// TODO 1 add all Genes here
	}

	private static final int cMaxSequence = 48;
	ArrayList<Gene> mGenes = new ArrayList<Gene>();
	private GeneInt mHunger = new GeneInt(0, 100, 50);
	//TODO IMPL make mutation rate a gene
	private Double mMutationRate = 0.1;
	// TODO 1 make this a gene
	// TODO make this private
	public ArrayList<IdvmCell> cellGrow = new ArrayList<IdvmCell>();
	// TODO make this a gene
	public HashMap<IdvmState, ArrayList<MoveProbability>> movementSequences = new HashMap<IdvmState, ArrayList<MoveProbability>>();

	// TODO 2 IMPL Randomize Genome
	public void mutate() {
		for (Gene iGene : mGenes) {
			iGene.mutate(mMutationRate);
		}
	}

	public int getHunger() {
		return mHunger.getValue();
	}

	public void setHunger(int pInt) {
		mHunger.setValue(pInt);
	}
}
