package execution;

import core.genes.Genome;
import core.soup.EnvironmentConfig;
import core.soup.Soup;
import core.soup.idvm.Idvm;

public class IdvmExecutionThread implements Runnable {

	private Genome mGenome;
	private Idvm mIdvm;
	private EnvironmentConfig mEnvironmentConfig;

	public IdvmExecutionThread(Genome pGenome, EnvironmentConfig pEnvironmentConfig) throws CloneNotSupportedException {
		mGenome = (Genome) pGenome.clone();
		mEnvironmentConfig = pEnvironmentConfig;
	}

	public void run() {
		mIdvm = new Idvm((Genome) mGenome);
		new Soup(mIdvm, mEnvironmentConfig).executeIdvm();
	}

	public Idvm getExecutedIdvm() {
		return mIdvm;
	}
}
