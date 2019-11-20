package core.exceptions;

import core.datatypes.Decisions;

@SuppressWarnings("serial")
public class WrongDecision extends RuntimeException {

	public WrongDecision(Decisions pDirection) {
System.out.print(pDirection);
	}

}
