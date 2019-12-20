package core.genes;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class GenomePersisterTest {
	GenomePersister cut = new GenomePersister();

	@Test
	public void test() throws CloneNotSupportedException, FileNotFoundException, ClassNotFoundException, IOException {
		String lPath = "XY";
		Genome lGenomeOrigin = new Genome().forceMutation();
		
		cut.perist(lGenomeOrigin, lPath);
		Genome lPersistetGenome = (Genome) cut.load(lPath);
		
		assertEquals(lGenomeOrigin, lPersistetGenome);
	}
}
