package execution;

import java.io.FileNotFoundException;
import java.io.IOException;

import core.datatypes.Pos;
import core.genes.Genome;
import core.genes.GenomePersister;
import core.soup.block.BlockType;
import core.soup.block.IdvmCell;
import ui.console.monitor.ModelMonitorIdvm;

public class runPersister {
	private static final String cFilename = "run1";

	public static void main(String args[]) throws FileNotFoundException, IOException, ClassNotFoundException {
		/*
		Genome lGenome = new Genome().forceMutation();
		lGenome.cellGrow.set(0, new IdvmCell(BlockType.LIFE, new Pos(0, 0)));
		lGenome.cellGrow.set(1, new IdvmCell(BlockType.SENSOR, new Pos(0, 1)));
		lGenome.cellGrow.set(2, new IdvmCell(BlockType.SENSOR, new Pos(1, 0)));
		lGenome.cellGrow.set(3, new IdvmCell(BlockType.SENSOR, new Pos(1, 1)));
		new GenomePersister().save(lGenome, cFilename);
		*/
		Genome lGenome = new GenomePersister().load(cFilename);
		new ModelMonitorIdvm().runGenome(lGenome);
	}

}
