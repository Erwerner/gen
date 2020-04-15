package core.genes;

import static core.soup.idvm.IdvmState.IDLE;
import static org.junit.Assert.*;

import org.junit.Test;

import core.datatypes.Decisions;
import core.datatypes.Direction;
import core.soup.idvm.IdvmState;

public class MoveDecisionsProbabilityTest {

	MoveDecisionsProbability cut = new MoveDecisionsProbability(IDLE);
	
	@Test
	public void equalsIgnoresAmountOfSameDirections() {
		MoveDecisionsProbability other = new MoveDecisionsProbability(IDLE);
		
		cut.appendDecision(Decisions.DOWN, 2);
		other.appendDecision(Decisions.DOWN, 3);
		
		assertEquals(cut, other);
	}

}
