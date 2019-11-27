package core.genes;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.soup.block.BlockType;
import core.soup.block.IdvmCell;
import core.soup.idvm.IdvmState;
import globals.Config;
import globals.Helpers;

public class Genome implements Cloneable {
	private GeneInt mHunger = new GeneInt(0, Config.cMaxEnergy, Config.cMaxEnergy / 2);
	// TODO 9 REF make this private
	public ArrayList<IdvmCell> cellGrow = new ArrayList<IdvmCell>();
	public HashMap<IdvmState, ArrayList<MoveDecisionsProbability>> moveSequencesForState = new HashMap<IdvmState, ArrayList<MoveDecisionsProbability>>();

	public Genome forceMutation() {
		initSequences();
		mutate(1.0);
		return this;
	}

	public Genome() {
	}

	private void initSequences() {
		cellGrow = new ArrayList<IdvmCell>();
		moveSequencesForState = new HashMap<IdvmState, ArrayList<MoveDecisionsProbability>>();
		ArrayList<MoveDecisionsProbability> lInitialMoveProbability = new ArrayList<MoveDecisionsProbability>();
		for (int i = 0; i <= Config.cMaxSequence; i++) {
			cellGrow.add((new IdvmCell(BlockType.NOTHING, new Pos(0, 0))));
			lInitialMoveProbability.add(new MoveDecisionsProbability());
		}
		for (IdvmState iState : IdvmState.values()) {
			moveSequencesForState.put(iState, lInitialMoveProbability);
		}
	}

	public void naturalMutation() {
		mutate(Config.cMutationRate);
	}

	private void mutate(Double pMutationRate) {
		ArrayList<iGene> lGenes;
		lGenes = getGeneCollection();

		for (iGene iGene : lGenes)
			if (Helpers.checkChance(pMutationRate))
				iGene.mutate();

		for (int i = 0; i < 4; i++)
			setInitialCellToCenterPos(i, i / 2, i % 2);

		for (Entry<IdvmState, ArrayList<MoveDecisionsProbability>> iStateMoveSequence : moveSequencesForState
				.entrySet()) {
			ArrayList<Decisions> lLastPossibleDecisions = new ArrayList<Decisions>();
			lLastPossibleDecisions.add(Decisions.UP);
			for (MoveDecisionsProbability iProbability : iStateMoveSequence.getValue()) {
				if (iProbability.mPossibleDecisions == null) {
					// Repeat last probability
					iProbability.mPossibleDecisions = (ArrayList<Decisions>) lLastPossibleDecisions.clone();
				} else {
					lLastPossibleDecisions = iProbability.mPossibleDecisions;
				}
			}
		}
	}

	private void setInitialCellToCenterPos(int pIdx, int pX, int pY) {
		BlockType lCellType = cellGrow.get(pIdx).getBlockType();
		cellGrow.set(pIdx, new IdvmCell(lCellType, new Pos(pX, pY)));
	}

	private ArrayList<iGene> getGeneCollection() {
		ArrayList<iGene> lGenes = new ArrayList<iGene>();
		lGenes.add(mHunger);

		for (iGene iGene : cellGrow) {
			lGenes.add(iGene);
		}
		for (ArrayList<MoveDecisionsProbability> iMoveProbability : moveSequencesForState.values()) {
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

	@Override
	public Object clone() throws CloneNotSupportedException {
		Genome lClone = new Genome();

		lClone.setHunger(mHunger.getValue());
		lClone.cellGrow.clear();
		for (IdvmCell iCell : cellGrow)
			lClone.cellGrow.add((IdvmCell) iCell.clone());

		lClone.moveSequencesForState.clear();
		for (IdvmState iState : IdvmState.values()) {
			ArrayList<MoveDecisionsProbability> lNewSequence = new ArrayList<MoveDecisionsProbability>();
			assertNotNull(moveSequencesForState);
			for (MoveDecisionsProbability iOriginProbability : moveSequencesForState.get(iState)) {
				MoveDecisionsProbability lNewMovePorobability = (MoveDecisionsProbability) iOriginProbability.clone();
				lNewSequence.add(lNewMovePorobability);
			}
			lClone.moveSequencesForState.put(iState, lNewSequence);
		}
		return lClone;
	}
}
