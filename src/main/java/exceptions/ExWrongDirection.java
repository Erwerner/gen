package exceptions;

import datatypes.Direction;

@SuppressWarnings("serial")
public class ExWrongDirection extends RuntimeException {

	public ExWrongDirection(Direction pDirection) {
System.out.print(pDirection);
	}

}
