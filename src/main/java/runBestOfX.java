import soup.Soup;
import soup.idvm.Idvm;
import soup.idvm.iIdvm;
import ui.console.monitor.ModelMonitorIdvm;
import genes.Genome;

public class runBestOfX {

	/**
	 * @param args
	 * @throws CloneNotSupportedException
	 */
	public static void main(String[] args) throws CloneNotSupportedException {
		Genome lGenomeGeneration = null;
		;
		for (int iGeneration = 0; iGeneration < 100; iGeneration++) {
			lGenomeGeneration = runGeneration((Genome) lGenomeGeneration,
					iGeneration);
		}
		System.out.println("All Genomes Finished");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			ModelMonitorIdvm lMonitor = new ModelMonitorIdvm();
			lMonitor.runGenome((Genome) lGenomeGeneration.clone());
		}
	}

	private static Genome runGeneration(Genome lGenomeGeneration,
			int pIdxGeneration) throws CloneNotSupportedException {

		int lGenCount = 0;
		Genome lGenomeBest = new Genome();
		lGenomeBest.forceMutation();
		int lStepBest = 0;
		for (int i = 0; i < 100; i++) {
			System.out.println("");
			System.out.print("Gen " + pIdxGeneration + ": Run Genome #" + i);
			Genome lGenome;
			if (lGenomeGeneration != null) {
				lGenome = (Genome) lGenomeGeneration.clone();
			} else {
				lGenome = new Genome();
				lGenome.forceMutation();
			}
			if (i != 0)
				lGenome.naturalMutation();
			int lTotalStepCount = 0;
			for (int j = 0; j < 10; j++) {
				iIdvm lIdvm = new Idvm((Genome) lGenome.clone());
				new Soup(lIdvm).executeIdvm();
				lTotalStepCount = lTotalStepCount + lIdvm.getStepCount();
			}
			System.out.print(" finished with Step #" + lTotalStepCount);
			if (lTotalStepCount > lStepBest) {
				System.out.print(" is best Idvm");
				lStepBest = lTotalStepCount;
				lGenomeBest = (Genome) lGenome.clone();
			}
			lGenCount = lGenCount + lTotalStepCount;
		}
		System.out.println("");
		System.out.println("Generation Finished with " + lGenCount);
		return (Genome) lGenomeBest.clone();
	}

}
