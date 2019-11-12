package soup;

import java.util.ArrayList;

import datatypes.Directions;

import soup.idvm.IdvmCell;
import soup.idvm.Movement;

public class Genome {

	public int hunger;
	public ArrayList<IdvmCell> cell = new ArrayList<IdvmCell>();;
	public ArrayList<Movement> idleMovement = new ArrayList<Movement>();
	
	public IdvmCell getCell() {
		IdvmCell lCell = cell.get(0);
		cell.remove(0);
		return lCell;
	}
	public Directions getIdleDirection(){
		return idleMovement.get(0).getDirection();
	}
}
