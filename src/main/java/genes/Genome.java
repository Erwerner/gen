package genes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import soup.block.BlockType;
import soup.block.IdvmCell;
import soup.idvm.Idvm;
import soup.idvm.IdvmState;
import datatypes.Direction;
import datatypes.Pos;

//TODO 2 IMPL Corossover
public class Genome implements Cloneable {
	private static final int cMaxSequence = 48;
	private GeneInt mHunger = new GeneInt(0, Idvm.cMaxEnergy, 50);
	// TODO IMPL make mutation rate a gene
	public Double mMutationRate = 0.02;
	// TODO REF make this private
	public ArrayList<IdvmCell> cellGrow = new ArrayList<IdvmCell>();
	public HashMap<IdvmState, ArrayList<MoveProbability>> movementSequences = new HashMap<IdvmState, ArrayList<MoveProbability>>();

	public Genome forceMutation() {
		ArrayList<MoveProbability> lInitialMoveProbability = new ArrayList<MoveProbability>();
		for (int i = 0; i < 48; i++) {
			cellGrow.add((new IdvmCell(BlockType.NOTHING, new Pos(0, 0))));
			lInitialMoveProbability.add(new MoveProbability());
		}
		for (IdvmState iState : IdvmState.values()) {
			movementSequences.put(iState, lInitialMoveProbability);
		}
		mutate(1.0);
		return this;
	}

	public void naturalMutation() {
		mutate(mMutationRate);
	}

	private void mutate(Double pMutationRate) {
		ArrayList<iGene> lGenes;
		lGenes = getGeneCollection();

		for (iGene iGene : lGenes)
			iGene.mutate(pMutationRate);

		for (int i = 0; i < 4; i++)
			setInitialCell(i, i / 2, i % 2);

		for (Entry<IdvmState, ArrayList<MoveProbability>> iMoveSequence : movementSequences.entrySet()) {
			ArrayList<Direction> lLastPossible = new ArrayList<Direction>();
			lLastPossible.add(Direction.UP);
			for (MoveProbability iProbability : iMoveSequence.getValue()) {
				if (iProbability.mPossibleDirection == null) {
					iProbability.mPossibleDirection = (ArrayList<Direction>) lLastPossible.clone();
				} else {
					lLastPossible = iProbability.mPossibleDirection;
				}
			}
		}
	}

	private void setInitialCell(int pIdx, int pX, int pY) {
		BlockType lCellType = cellGrow.get(pIdx).getBlockType();
		cellGrow.set(pIdx, new IdvmCell(lCellType, new Pos(pX, pY)));
	}

	private ArrayList<iGene> getGeneCollection() {
		ArrayList<iGene> lGenes = new ArrayList<iGene>();
		lGenes.add(mHunger);
		for (iGene iGene : cellGrow) {
			lGenes.add(iGene);
		}
		for (ArrayList<MoveProbability> iMoveProbability : movementSequences.values()) {
			for (iGene iGene : iMoveProbability) {
				lGenes.add(iGene);
			}
		}
		return lGenes;
	}

	public int getHunger() {
		return mHunger.getValue();
	}

	public void setHunger(int pInt) {
		mHunger.setValue(pInt);
	}

	// TODO 1 TEST clone
	@Override
	public Object clone() throws CloneNotSupportedException {
		Genome lClone = new Genome();

		lClone.setHunger(getHunger());

		for (IdvmCell iCell : cellGrow) {
			lClone.cellGrow
					.add(new IdvmCell(iCell.getBlockType(), new Pos(iCell.getPosOnIdvm().x, iCell.getPosOnIdvm().y)));
		}

		for (IdvmState iState : IdvmState.values()) {
			ArrayList<MoveProbability> lMovements = new ArrayList<MoveProbability>();
			for (MoveProbability iMovement : movementSequences.get(iState)) {
				lMovements = (ArrayList<MoveProbability>) iMovement.mPossibleDirection.clone();
			}
			lClone.movementSequences.put(iState, lMovements);
		}
		return lClone;
	}
}
