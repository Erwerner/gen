package pairing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import core.genes.Genome;
import core.soup.idvm.Idvm;

public class Pairing2481616Test {
	Pairing2481616 cut;
	private Genome mGenome1 = new Genome().forceMutation();
	private Genome mGenome2 = new Genome().forceMutation();
	private Genome mGenome3 = new Genome().forceMutation();
	private Genome mGenome4 = new Genome().forceMutation();

	@Before
	public void setUp() throws Exception {
		ArrayList<Idvm> lPopulation = new ArrayList<Idvm>();
		lPopulation.add(new Idvm(mGenome1));
		lPopulation.add(new Idvm(mGenome2));
		lPopulation.add(new Idvm(mGenome3));
		lPopulation.add(new Idvm(mGenome4));
		lPopulation.add(new Idvm(mGenome1));
		lPopulation.add(new Idvm(mGenome2));
		lPopulation.add(new Idvm(mGenome3));
		lPopulation.add(new Idvm(mGenome4));
		lPopulation.add(new Idvm(mGenome1));
		lPopulation.add(new Idvm(mGenome2));
		lPopulation.add(new Idvm(mGenome3));
		lPopulation.add(new Idvm(mGenome4));
		lPopulation.add(new Idvm(mGenome1));
		lPopulation.add(new Idvm(mGenome2));
		lPopulation.add(new Idvm(mGenome3));
		lPopulation.add(new Idvm(mGenome4));
		cut = new Pairing2481616(lPopulation);
	}

	// TODO 1 Test Pairing
	@Test
	public void test() {
		ArrayList<Genome> lAct = cut.pair();
		assertEquals(16, lAct.size());
	}

}
