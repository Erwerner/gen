package execution;

import core.genes.Genome;
import core.soup.idvm.Idvm;

public class IdvmExecutionThread implements Runnable {

	private Genome mGenome;
	private Idvm mIdvm;

	public IdvmExecutionThread(Genome pGenome) throws CloneNotSupportedException {
		mGenome = (Genome) pGenome.clone();
	}

	public void run() {
		mIdvm = new Idvm((Genome) mGenome);
	}

	public int getCount() {
		return mIdvm.getStepCount();
	}

	public Idvm getExecutedIdvm() {
		return mIdvm;
	}
}
