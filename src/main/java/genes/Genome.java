package genes;

import java.util.ArrayList;
import java.util.HashMap;

import soup.idvm.IdvmCell;
import soup.idvm.IdvmState;

public class Genome {
	public int hunger;
	public ArrayList<IdvmCell> cell = new ArrayList<IdvmCell>();
	public HashMap<IdvmState, ArrayList<MoveProbability>> movementSequences = new HashMap<IdvmState, ArrayList<MoveProbability>>();
}
