package genes;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.genes.Genome;
import core.genes.MoveProbability;
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
		ArrayList<MoveProbability> lIdleSequence = cut.movementSequences
				.get(IdvmState.IDLE);
		cut.naturalMutation();
		for (MoveProbability iMovement : lIdleSequence)
			assertNotNull(iMovement);
	}

	@Test
	public void nullSequencesBecomePrevious() {
		cut.mMutationRate = 0.0;
		cut.forceMutation();
		ArrayList<MoveProbability> lIdleSequence = cut.movementSequences
				.get(IdvmState.IDLE);
		assertNotNull(lIdleSequence.get(0).mPossibleDirection);
		assertNotNull(lIdleSequence.get(1).mPossibleDirection);
		lIdleSequence.get(1).mPossibleDirection = null;
		cut.naturalMutation();

		assertNotNull(lIdleSequence.get(1).mPossibleDirection);
	}

	@Test
	public void followingSequencesHaveSameContent() {
		cut.mMutationRate = 0.0;
		cut.forceMutation();
		ArrayList<MoveProbability> lIdleSequence = cut.movementSequences
				.get(IdvmState.IDLE);
		ArrayList<Decisions> lFirstProbability = lIdleSequence.get(0).mPossibleDirection;
		assertNotNull(lFirstProbability);
		assertNotNull(lIdleSequence.get(1).mPossibleDirection);
		lIdleSequence.get(1).mPossibleDirection = null;
		cut.naturalMutation();
		assertEquals(lFirstProbability, lIdleSequence.get(1).mPossibleDirection);
	}

	@Test
	public void followingSequencesAreNotSameInstance() {
		cut.mMutationRate = 0.0;
		cut.forceMutation();
		ArrayList<MoveProbability> lIdleSequence = cut.movementSequences
				.get(IdvmState.IDLE);
		ArrayList<Decisions> lFirstProbability = lIdleSequence.get(0).mPossibleDirection;
		assertNotNull(lFirstProbability);
		assertNotNull(lIdleSequence.get(1).mPossibleDirection);
		lIdleSequence.get(1).mPossibleDirection = null;
		cut.naturalMutation();
		lFirstProbability.clear();
		assertNotEquals(lFirstProbability,
				lIdleSequence.get(1).mPossibleDirection);
	}

	@Test
	public void cloneWorksCorrectForCellGrow()
			throws CloneNotSupportedException {
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
	public void cloneWorksCorrectForSequences()
			throws CloneNotSupportedException {
		cut.forceMutation();
		Genome lCopy = (Genome) cut.clone();
		Genome lAlteredCopy = (Genome) cut.clone();
		lAlteredCopy.forceMutation();
		ArrayList<Decisions> lActMovement = cut.movementSequences
				.get(IdvmState.IDLE).get(1).mPossibleDirection;
		ArrayList<Decisions>  lCopyMovement = lCopy.movementSequences.get(
				IdvmState.IDLE).get(1).mPossibleDirection;
		ArrayList<Decisions>  lAlteredCopyMovement = lAlteredCopy.movementSequences
				.get(IdvmState.IDLE).get(1).mPossibleDirection;
		assertEquals(lCopyMovement, lActMovement);
		assertNotEquals(lAlteredCopyMovement, lActMovement);
	}
}
