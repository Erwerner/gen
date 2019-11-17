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
		Genome lGenomeGeneration = new Genome();
		lGenomeGeneration.forceMutation();
		for (int iGeneration = 0; iGeneration < 25; iGeneration++) {
			lGenomeGeneration = runGeneration((Genome) lGenomeGeneration.clone(), iGeneration);
			System.out.println("Generation Finished");
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

		Genome lGenomeBest = (Genome) lGenomeGeneration.clone();
		int lStepBest = 0;
		for (int i = 0; i < 40; i++) {
			System.out.println("");
			System.out.print("Gen " + pIdxGeneration + ": Run Genome #" + i);
			Genome lGenome = (Genome) lGenomeGeneration.clone();
			// lGenome.forceMutation();
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
		}
		return (Genome) lGenomeBest.clone();
	}

}
