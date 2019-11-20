package core.genes;

import static org.junit.Assert.*;

import globals.Config;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import core.soup.block.IdvmCell;
import core.soup.idvm.IdvmState;

public class CrossoverTest {
	Crossover cut;
	Genome mGenome1;
	Genome mGenome2;

	@Before
	public void setUp() throws Exception {
		mGenome1 = new Genome().forceMutation();
		mGenome2 = new Genome().forceMutation();
		Genome[] lGenomes = { mGenome1, mGenome2 };
		cut = new Crossover(lGenomes);
	}

	@Test
	public void crossoverWotksForSequences() {
		
		Genome lCrossoverGenome = cut.crossover();
		ArrayList<MoveProbability> lActSequence = lCrossoverGenome.movementSequences
				.get(IdvmState.IDLE);
		ArrayList<MoveProbability> lGenome1Sequence = mGenome1.movementSequences
				.get(IdvmState.IDLE);
		ArrayList<MoveProbability> lGenome2Sequence = mGenome2.movementSequences
				.get(IdvmState.IDLE);
		
		ArrayList<MoveProbability> lFirstCrossoverSequence;
		ArrayList<MoveProbability> lSecondCrossoverSequence;
		if(lActSequence.get(0) == lGenome1Sequence.get(0)){
			lFirstCrossoverSequence = lGenome1Sequence;
			lSecondCrossoverSequence = lGenome2Sequence;
		}else{
			lFirstCrossoverSequence = lGenome2Sequence;
			lSecondCrossoverSequence = lGenome1Sequence;
			}
		
		for (int i = 0; i < Config.cMaxSequence; i++) {
			if (lActSequence.get(i).mPossibleDirection.equals(lFirstCrossoverSequence.get(i).mPossibleDirection))
				continue;
			assertTrue(i > 0);
			assertEquals(lSecondCrossoverSequence.get(i).mPossibleDirection, lActSequence.get(i).mPossibleDirection);
		}
	}
	@Test
	public void crossoverWotksForCellGrow() {
		Genome lCrossoverGenome = cut.crossover();
		
		ArrayList<IdvmCell> lFistCrossoverCellGrow;
		ArrayList<IdvmCell> lSecondCrossoverCellGrow;
		if(lCrossoverGenome.cellGrow.get(Config.cMaxSequence-1)==mGenome1.cellGrow.get(Config.cMaxSequence-1)){
			lFistCrossoverCellGrow = mGenome2.cellGrow;
			lSecondCrossoverCellGrow = mGenome1.cellGrow;	
		}else{
			lFistCrossoverCellGrow = mGenome1.cellGrow;
			lSecondCrossoverCellGrow = mGenome2.cellGrow;
		}
		
		for (int i = 4; i < Config.cMaxSequence; i++) {
			if (lCrossoverGenome.cellGrow.get(i).equals(lFistCrossoverCellGrow.get(i)))
				continue;
			assertTrue(i > 0);
			assertEquals(lSecondCrossoverCellGrow.get(i), lCrossoverGenome.cellGrow.get(i));
		}
	}

}
