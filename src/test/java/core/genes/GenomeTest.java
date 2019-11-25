package core.genes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.soup.idvm.IdvmState;

public class GenomeTest {
	Genome cut = new Genome();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void initalGenomeIsEmpty() {
		assertEquals(0, cut.cellGrow.size());
	}

	@Test
	public void forecedMutationSets4InitialCells() {
		assertEquals(0, cut.cellGrow.size());
		cut.forceMutation();
		assertEquals(new Pos(0, 0), cut.cellGrow.get(0).getPosOnIdvm());
		assertEquals(new Pos(0, 1), cut.cellGrow.get(1).getPosOnIdvm());
		assertEquals(new Pos(1, 0), cut.cellGrow.get(2).getPosOnIdvm());
		assertEquals(new Pos(1, 1), cut.cellGrow.get(3).getPosOnIdvm());
	}

	@Test
	public void mutationDoesntSetNullSequences() {
		cut.forceMutation();
		ArrayList<MoveDecisionsProbability> lIdleSequence = cut.moveSequencesForState.get(IdvmState.IDLE);
		cut.naturalMutation();
		for (MoveDecisionsProbability iProbability : lIdleSequence)
			assertNotNull(iProbability);
	}

	@Test
	public void nullSequencesBecomePrevious() {
		cut.forceMutation();
		ArrayList<MoveDecisionsProbability> lIdleSequence = cut.moveSequencesForState.get(IdvmState.IDLE);
		assertNotNull(lIdleSequence.get(0).mPossibleDecisions);
		assertNotNull(lIdleSequence.get(1).mPossibleDecisions);
		lIdleSequence.get(1).mPossibleDecisions = null;
		cut.naturalMutation();

		assertNotNull(lIdleSequence.get(1).mPossibleDecisions);
	}

	@Test
	public void followingSequencesHaveSameContent() {
		cut.forceMutation();
		ArrayList<MoveDecisionsProbability> lIdleSequence = cut.moveSequencesForState.get(IdvmState.IDLE);
		ArrayList<Decisions> lFirstProbability = lIdleSequence.get(0).mPossibleDecisions;
		assertNotNull(lFirstProbability);
		assertNotNull(lIdleSequence.get(1).mPossibleDecisions);
		lIdleSequence.get(1).mPossibleDecisions = null;
		cut.naturalMutation();
		assertEquals(lFirstProbability, lIdleSequence.get(1).mPossibleDecisions);
	}

	@Test
	public void followingSequencesAreNotSameInstance() {
		cut.forceMutation();
		ArrayList<MoveDecisionsProbability> lIdleSequence = cut.moveSequencesForState.get(IdvmState.IDLE);
		ArrayList<Decisions> lFirstProbability = lIdleSequence.get(0).mPossibleDecisions;
		assertNotNull(lFirstProbability);
		assertNotNull(lIdleSequence.get(1).mPossibleDecisions);
		lIdleSequence.get(1).mPossibleDecisions = null;
		cut.naturalMutation();
		lFirstProbability.clear();
		assertNotEquals(lFirstProbability, lIdleSequence.get(1).mPossibleDecisions);
	}

	@Test
	public void cloneWorksCorrectForCellGrow() throws CloneNotSupportedException {
		cut.forceMutation();
		Genome lCopy = (Genome) cut.clone();
		Genome lAlteredCopy = (Genome) cut.clone();
		lAlteredCopy.forceMutation();
		cut.cellGrow.get(5).setPosition(new Pos(10, 10));
		lAlteredCopy.cellGrow.get(5).setPosition(new Pos(10, 10));
		lCopy.cellGrow.get(5).setPosition(new Pos(10, 10));
		assertEquals(lCopy.cellGrow.get(5), cut.cellGrow.get(5));
		assertNotEquals(lAlteredCopy.cellGrow.get(5), cut.cellGrow.get(5));
	}

	@Test
	public void cloneWorksCorrectForSequences() throws CloneNotSupportedException {
		cut.forceMutation();
		Genome lCopy = (Genome) cut.clone();
		Genome lAlteredCopy = (Genome) cut.clone();
		lAlteredCopy.moveSequencesForState.get(IdvmState.IDLE).get(1).appendDecision(Decisions.UP, 100);
		ArrayList<Decisions> lActMovement = cut.moveSequencesForState.get(IdvmState.IDLE).get(1).mPossibleDecisions;
		ArrayList<Decisions> lCopyMovement = lCopy.moveSequencesForState.get(IdvmState.IDLE).get(1).mPossibleDecisions;
		ArrayList<Decisions> lAlteredCopyMovement = lAlteredCopy.moveSequencesForState.get(IdvmState.IDLE)
				.get(1).mPossibleDecisions;
		assertEquals(lCopyMovement, lActMovement);
		assertNotEquals(lAlteredCopyMovement, lActMovement);
	}
}
