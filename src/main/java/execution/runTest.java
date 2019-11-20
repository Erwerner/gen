package execution;

import core.genes.Genome;
import core.soup.Soup;
import core.soup.idvm.Idvm;
import devutils.Measure;
import globals.Helpers;

public class runTest {

	public static void main(String[] args) throws InterruptedException {
		Idvm mIdvm = new Idvm(initializeGenome());
		Soup mSoup = new Soup(mIdvm);

		for (int x = 0; x < 20; x++)
			System.out.println(Helpers.rndIntRange(-1, 4));

		System.out.println("Start");
		Measure.startTimeMeasuring();
		while (mIdvm.isAlive()) {
			mSoup.step();
		}
		Measure.showTimeMeasuring();
	}

	public static Genome initializeGenome() {
		Genome lGenome = new Genome();
		return lGenome.forceMutation();
	}
}
