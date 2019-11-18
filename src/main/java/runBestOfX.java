import genes.Genome;
import ui.console.monitor.ModelMonitorIdvm;

public class runBestOfX {

	/**
	 * @param args
	 * @throws CloneNotSupportedException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws CloneNotSupportedException,
			InterruptedException {
		System.out.println("Start");
		Genome lGenomeGeneration = null;// ModelMonitorIdvm.initializeGenome();
		;
		for (int iGeneration = 0; iGeneration < 350; iGeneration++) {
			lGenomeGeneration = runGeneration((Genome) lGenomeGeneration,
					iGeneration);
			if (iGeneration % 10 == 0) {
				ModelMonitorIdvm lMonitor = new ModelMonitorIdvm();
				lMonitor.runGenome((Genome) lGenomeGeneration.clone());
			}
		}
		System.out.println("All Genomes Finished");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		while (true) {
			ModelMonitorIdvm lMonitor = new ModelMonitorIdvm();
			lMonitor.runGenome((Genome) lGenomeGeneration.clone());
		}
	}

	private static Genome runGeneration(Genome lGenomeGeneration,
			int pIdxGeneration) throws CloneNotSupportedException,
			InterruptedException {

		int lGenCount = 0;
		Genome lGenomeBest = lGenomeGeneration;

		int lStepBest = 0;
		for (int i = 0; i < 200; i++) {
			// System.out.println("");
			// System.out.print("Gen " + pIdxGeneration + ": Run Genome #" + i);
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
			for (int j = 0; j < 1; j++) {
				IdvmExecutionThread lIdvmRunner1 = new IdvmExecutionThread(lGenome);
				Thread thread1 = new Thread(lIdvmRunner1);
				IdvmExecutionThread lIdvmRunner2 = new IdvmExecutionThread(lGenome);
				Thread thread2 = new Thread(lIdvmRunner2);
				IdvmExecutionThread lIdvmRunner3 = new IdvmExecutionThread(lGenome);
				Thread thread3 = new Thread(lIdvmRunner3);
				IdvmExecutionThread lIdvmRunner4 = new IdvmExecutionThread(lGenome);
				Thread thread4 = new Thread(lIdvmRunner4);
				IdvmExecutionThread lIdvmRunner5 = new IdvmExecutionThread(lGenome);
				Thread thread5 = new Thread(lIdvmRunner5);
				IdvmExecutionThread lIdvmRunner6 = new IdvmExecutionThread(lGenome);
				Thread thread6 = new Thread(lIdvmRunner6);
				IdvmExecutionThread lIdvmRunner7 = new IdvmExecutionThread(lGenome);
				Thread thread7 = new Thread(lIdvmRunner7);
				IdvmExecutionThread lIdvmRunner8 = new IdvmExecutionThread(lGenome);
				Thread thread8 = new Thread(lIdvmRunner8);
				thread1.start();
				thread2.start();
				thread3.start();
				thread4.start();
				thread5.start();
				thread6.start();
				thread7.start();
				thread8.start();
				thread1.join();
				thread2.join();
				thread3.join();
				thread4.join();
				thread5.join();
				thread6.join();
				thread7.join();
				thread8.join();
				lTotalStepCount = lTotalStepCount + lIdvmRunner1.getCount();
				lTotalStepCount = lTotalStepCount + lIdvmRunner2.getCount();
				lTotalStepCount = lTotalStepCount + lIdvmRunner3.getCount();
				lTotalStepCount = lTotalStepCount + lIdvmRunner4.getCount();
				lTotalStepCount = lTotalStepCount + lIdvmRunner5.getCount();
				lTotalStepCount = lTotalStepCount + lIdvmRunner6.getCount();
				lTotalStepCount = lTotalStepCount + lIdvmRunner7.getCount();
				lTotalStepCount = lTotalStepCount + lIdvmRunner8.getCount();
			}
			// System.out.print(" finished with Step #" + lTotalStepCount);
			if (lTotalStepCount > lStepBest) {
				// System.out.print(" is best Idvm");
				lStepBest = lTotalStepCount;
				lGenomeBest = (Genome) lGenome.clone();
			}
			lGenCount = lGenCount + lTotalStepCount;
		}
		System.out.println("Generation " + pIdxGeneration + " Finished with "
				+ lGenCount + " Idvm: " + lStepBest);
		return (Genome) lGenomeBest.clone();
	}

}
