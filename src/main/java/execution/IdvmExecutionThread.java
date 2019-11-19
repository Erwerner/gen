package execution;
import genes.Genome;
import soup.Soup;
import soup.idvm.Idvm;
import soup.idvm.iIdvm;

public class IdvmExecutionThread implements Runnable {

	private Genome mGenome;
	private int lIdvmStepCount;

	public IdvmExecutionThread(Genome pGenome) throws CloneNotSupportedException {
		mGenome = (Genome) pGenome.clone();
	}

	public void run() {
		iIdvm lIdvm;
			lIdvm = new Idvm((Genome) mGenome);
			new Soup(lIdvm).executeIdvm();
			lIdvmStepCount = lIdvm.getStepCount();
	}

	public int getCount() {
		return lIdvmStepCount;
	}
}