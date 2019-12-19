package core.genes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import core.datatypes.Decisions;
import core.datatypes.Pos;
import core.soup.block.BlockType;
import core.soup.block.IdvmCell;
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
		ArrayList<MoveDecisionsProbability> lIdleSequence = cut.moveSequencesForState
				.get(IdvmState.IDLE);
		cut.naturalMutation();
		for (MoveDecisionsProbability iProbability : lIdleSequence)
			assertNotNull(iProbability);
	}

	@Test
	public void nullSequencesBecomePrevious() {
		cut.forceMutation();
		ArrayList<MoveDecisionsProbability> lIdleSequence = cut.moveSequencesForState
				.get(IdvmState.IDLE);
		assertNotNull(lIdleSequence.get(0).mPossibleDecisions);
		assertNotNull(lIdleSequence.get(1).mPossibleDecisions);
		lIdleSequence.get(1).mPossibleDecisions = null;
		cut.naturalMutation();

		assertNotNull(lIdleSequence.get(1).mPossibleDecisions);
	}

	@Test
	public void followingSequencesHaveSameContent() {
		cut.forceMutation();
		ArrayList<MoveDecisionsProbability> lIdleSequence = cut.moveSequencesForState
				.get(IdvmState.IDLE);
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
		ArrayList<MoveDecisionsProbability> lIdleSequence = cut.moveSequencesForState
				.get(IdvmState.IDLE);
		ArrayList<Decisions> lFirstProbability = lIdleSequence.get(0).mPossibleDecisions;
		assertNotNull(lFirstProbability);
		assertNotNull(lIdleSequence.get(1).mPossibleDecisions);
		lIdleSequence.get(1).mPossibleDecisions = null;
		cut.naturalMutation();
		lFirstProbability.clear();
		assertNotEquals(lFirstProbability,
				lIdleSequence.get(1).mPossibleDecisions);
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
		lAlteredCopy.moveSequencesForState.get(IdvmState.IDLE).get(1)
				.appendDecision(Decisions.UP, 100);
		ArrayList<Decisions> lActMovement = cut.moveSequencesForState.get(
				IdvmState.IDLE).get(1).mPossibleDecisions;
		ArrayList<Decisions> lCopyMovement = lCopy.moveSequencesForState.get(
				IdvmState.IDLE).get(1).mPossibleDecisions;
		ArrayList<Decisions> lAlteredCopyMovement = lAlteredCopy.moveSequencesForState
				.get(IdvmState.IDLE).get(1).mPossibleDecisions;
		assertEquals(lCopyMovement, lActMovement);
		assertNotEquals(lAlteredCopyMovement, lActMovement);
	}

	@Test
	public void percentageOfTargetIsZeroWithNoSensor() {
		cut.forceMutation();
		IdvmCell lLifeCell = new IdvmCell(BlockType.LIFE, new Pos(0, 0));
		cut.cellGrow.set(0, lLifeCell);
		cut.cellGrow.set(1, lLifeCell);
		cut.cellGrow.set(2, lLifeCell);
		cut.cellGrow.set(3, lLifeCell);
		cut.cellGrow.set(4, lLifeCell);
		cut.cellGrow.set(5, new IdvmCell(BlockType.SENSOR, new Pos(1, 1)));

		Double lPercentage = cut.getPercentageOfWrongTargetDecision(1);
		Double lExp = 0.0;
		assertEquals(lExp, lPercentage);
	}

	@Test
	public void percentageOfTargetIsZeroWithNoTargetDirection() {
		cut.forceMutation();
		IdvmCell lLifeCell = new IdvmCell(BlockType.LIFE, new Pos(0, 0));
		cut.cellGrow.set(0, lLifeCell);
		cut.cellGrow.set(1, lLifeCell);
		cut.cellGrow.set(2, lLifeCell);
		cut.cellGrow.set(3, lLifeCell);
		cut.cellGrow.set(4, lLifeCell);
		cut.cellGrow.set(5, new IdvmCell(BlockType.SENSOR, new Pos(1, 1)));

		ArrayList<MoveDecisionsProbability> lEnemyMovement = cut.moveSequencesForState.get(IdvmState.ENEMY);
		ArrayList<MoveDecisionsProbability> lFoodMovement = cut.moveSequencesForState.get(IdvmState.FOOD);
		

		MoveDecisionsProbability lDecisionNoTarget = new MoveDecisionsProbability();
		lDecisionNoTarget.appendDecision(Decisions.LEFT, 1);
		lEnemyMovement.set(0, lDecisionNoTarget );
		lFoodMovement.set(0, lDecisionNoTarget );
		lEnemyMovement.set(1, lDecisionNoTarget );
		lFoodMovement.set(1, lDecisionNoTarget );
		lEnemyMovement.set(2, lDecisionNoTarget );
		lFoodMovement.set(2, lDecisionNoTarget );
		lEnemyMovement.set(3, lDecisionNoTarget );
		lFoodMovement.set(3, lDecisionNoTarget );
		lEnemyMovement.set(4, lDecisionNoTarget );
		lFoodMovement.set(4, lDecisionNoTarget );
		lEnemyMovement.set(5, lDecisionNoTarget );
		lFoodMovement.set(5, lDecisionNoTarget );
		lEnemyMovement.set(6, lDecisionNoTarget );
		lFoodMovement.set(6, lDecisionNoTarget );
		
		Double lPercentage = cut.getPercentageOfWrongTargetDecision(2);
		Double lExp = 0.0;
		assertEquals(lExp, lPercentage);
	}
	@Test
	public void percentageOfTargetIsCorrectIfOverwrite() {
		cut.forceMutation();
		IdvmCell lLifeCell = new IdvmCell(BlockType.LIFE, new Pos(0, 0));
		cut.cellGrow.set(0, lLifeCell);
		cut.cellGrow.set(1, lLifeCell);
		cut.cellGrow.set(2, lLifeCell);
		cut.cellGrow.set(3, lLifeCell);
		cut.cellGrow.set(4, new IdvmCell(BlockType.SENSOR, new Pos(1, 1)));
		cut.cellGrow.set(5, new IdvmCell(BlockType.LIFE, new Pos(1, 1)));
		cut.cellGrow.set(6, new IdvmCell(BlockType.LIFE, new Pos(1, 1)));

		ArrayList<MoveDecisionsProbability> lEnemyMovement = cut.moveSequencesForState.get(IdvmState.ENEMY);
		ArrayList<MoveDecisionsProbability> lFoodMovement = cut.moveSequencesForState.get(IdvmState.FOOD);
		
		MoveDecisionsProbability lDecisionNoTarget = new MoveDecisionsProbability();
		lDecisionNoTarget.appendDecision(Decisions.LEFT, 1);
		lEnemyMovement.set(0, lDecisionNoTarget );
		lFoodMovement.set(0, lDecisionNoTarget );
		lEnemyMovement.set(1, lDecisionNoTarget );
		lFoodMovement.set(1, lDecisionNoTarget );
		lEnemyMovement.set(2, lDecisionNoTarget );
		lFoodMovement.set(2, lDecisionNoTarget );
		lEnemyMovement.set(3, lDecisionNoTarget );
		lFoodMovement.set(3, lDecisionNoTarget );
		lEnemyMovement.set(4, lDecisionNoTarget );
		lFoodMovement.set(4, lDecisionNoTarget );
		lEnemyMovement.set(5, lDecisionNoTarget );
		lFoodMovement.set(5, lDecisionNoTarget );
		lEnemyMovement.set(6, lDecisionNoTarget );
		lFoodMovement.set(6, lDecisionNoTarget );
		
		
		MoveDecisionsProbability lDecisionTarget = new MoveDecisionsProbability();
		lDecisionTarget.appendDecision(Decisions.TARGET, 1);
		lEnemyMovement.set(5, lDecisionTarget );
		lFoodMovement.set(5, lDecisionTarget );
		
		Double lPercentage = cut.getPercentageOfWrongTargetDecision(3);
		Double lExp = 0.25;
		assertEquals(lExp, lPercentage);
	}
	@Test
	public void percentageOfTargetIsCorrect() {
		cut.forceMutation();
		IdvmCell lLifeCell = new IdvmCell(BlockType.LIFE, new Pos(0, 0));
		cut.cellGrow.set(0, lLifeCell);
		cut.cellGrow.set(1, new IdvmCell(BlockType.SENSOR, new Pos(1, 1)));
		cut.cellGrow.set(2, lLifeCell);
		cut.cellGrow.set(3, lLifeCell);

		ArrayList<MoveDecisionsProbability> lEnemyMovement = cut.moveSequencesForState.get(IdvmState.ENEMY);
		ArrayList<MoveDecisionsProbability> lFoodMovement = cut.moveSequencesForState.get(IdvmState.FOOD);
		

		MoveDecisionsProbability lDecisionNoTarget = new MoveDecisionsProbability();
		lDecisionNoTarget.appendDecision(Decisions.LEFT, 1);
		lEnemyMovement.set(0, lDecisionNoTarget );
		lFoodMovement.set(0, lDecisionNoTarget );
		lEnemyMovement.set(1, lDecisionNoTarget );
		lFoodMovement.set(1, lDecisionNoTarget );
		lEnemyMovement.set(2, lDecisionNoTarget );
		lFoodMovement.set(2, lDecisionNoTarget );
		lEnemyMovement.set(3, lDecisionNoTarget );
		lFoodMovement.set(3, lDecisionNoTarget );
		lEnemyMovement.set(4, lDecisionNoTarget );
		lFoodMovement.set(4, lDecisionNoTarget );
		lEnemyMovement.set(5, lDecisionNoTarget );
		lFoodMovement.set(5, lDecisionNoTarget );
		lEnemyMovement.set(6, lDecisionNoTarget );
		lFoodMovement.set(6, lDecisionNoTarget );
		
		MoveDecisionsProbability lDecisionTarget = new MoveDecisionsProbability();
		lDecisionTarget.appendDecision(Decisions.TARGET, 1);
		lEnemyMovement.set(3, lDecisionTarget );
		lFoodMovement.set(3, lDecisionTarget );
		
		Double lPercentage = cut.getPercentageOfWrongTargetDecision(0);
		Double lExp = 1.0;
		assertEquals(lExp, lPercentage);
	}
}
