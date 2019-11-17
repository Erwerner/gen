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
		for (int iGeneration = 0; iGeneration < 1; iGeneration++) {
			lGenomeGeneration = runGeneration(lGenomeGeneration);
			System.out.println("Generation Finished");
		}
		System.out.println("All Genomes Finished");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelMonitorIdvm lMonitor = new ModelMonitorIdvm();
		lMonitor.runGenome(lGenomeGeneration);
	}

	private static Genome runGeneration(Genome lGenomeGeneration)
			throws CloneNotSupportedException {

		Genome lGenomeBest = lGenomeGeneration;
		int lStepBest = 0;
		for (int i = 0; i < 50; i++) {
			System.out.println("");
			System.out.print("Run Genome #" + i);
			Genome lGenome = (Genome) lGenomeGeneration.clone();
			lGenome.forceMutation();
			lGenome.naturalMutation();
			int lTotalStepCount = 0;
			for (int j = 0; j < 5; j++) {
				iIdvm lIdvm = new Idvm(lGenome);
				new Soup(lIdvm).executeIdvm();
				lTotalStepCount = +lIdvm.getStepCount();
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
