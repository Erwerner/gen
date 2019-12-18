package core.genes;

import static org.junit.Assert.*;

import org.junit.Test;

public class GenomePersisterTest {
	GenomePersister cut = new GenomePersister();
	
	@Test
	public void test() throws CloneNotSupportedException {
		String lPath = "ABC";
		Genome lGenomeOrigin = new Genome().forceMutation();
		
		cut.perist(lGenomeOrigin, lPath);
		Genome lPersistetGenome = cut.load(lPath);
		
		assertEquals(lGenomeOrigin, lPersistetGenome);
	}

}
