package core.genes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.soup.block.BlockType;
import core.soup.block.IdvmCell;
import core.soup.idvm.Idvm;
import core.soup.idvm.IdvmState;
import globals.Config;

public class Genome implements Cloneable {
	private GeneInt mHunger = new GeneInt(0, Idvm.cMaxEnergy, 50);
	// TODO IMPL make mutation rate a gene
	public Double mMutationRate = 0.02;
	// TODO REF make this private
	public ArrayList<IdvmCell> cellGrow = new ArrayList<IdvmCell>();
	public HashMap<IdvmState, ArrayList<MoveProbability>> movementSequences = new HashMap<IdvmState, ArrayList<MoveProbability>>();

	public Genome forceMutation() {
		ArrayList<MoveProbability> lInitialMoveProbability = new ArrayList<MoveProbability>();
		for (int i = 0; i < Config.cMaxSequence; i++) {
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
			ArrayList<Decisions> lLastPossible = new ArrayList<Decisions>();
			lLastPossible.add(Decisions.UP);
			for (MoveProbability iProbability : iMoveSequence.getValue()) {
				if (iProbability.mPossibleDirection == null) {
					iProbability.mPossibleDirection = (ArrayList<Decisions>) lLastPossible.clone();
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

	public GeneInt getHunger() {
		return mHunger;
	}

	public void setHunger(int pInt) {
		mHunger.setValue(pInt);
	}

	// TODO 2 check all clone methods are used
	@Override
	public Object clone() throws CloneNotSupportedException {
		Genome lClone = new Genome();

		lClone.setHunger(mHunger.getValue());

		for (IdvmCell iCell : cellGrow) {
			lClone.cellGrow
					.add(new IdvmCell(iCell.getBlockType(), new Pos(iCell.getPosOnIdvm().x, iCell.getPosOnIdvm().y)));
		}

		for (IdvmState iState : IdvmState.values()) {
			ArrayList<MoveProbability> lMovements = new ArrayList<MoveProbability>();
			for (MoveProbability iMovement : movementSequences.get(iState)) {
				MoveProbability lNewMovePorobability = (MoveProbability) iMovement.clone();
				/*
				 * MoveProbability lNewMovePorobability = new MoveProbability();
				 * lNewMovePorobability.mPossibleDirection = (ArrayList<Decisions>)
				 * iMovement.mPossibleDirection .clone();
				 */
				lMovements.add(lNewMovePorobability);
			}
			lClone.movementSequences.put(iState, lMovements);
		}
		return lClone;
	}
}
