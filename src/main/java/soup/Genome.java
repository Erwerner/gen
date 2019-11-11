package soup;

import java.util.ArrayList;

import soup.idvm.IdvmCell;

public class Genome {

	public int hunger;
	public ArrayList<IdvmCell> cell;
	
	public Genome(){
		cell = new ArrayList<IdvmCell>();
	}
	public IdvmCell getCell() {
		IdvmCell lCell = cell.get(0);
		cell.remove(0);
		return lCell;
	}

}
