package execution;

import java.util.ArrayList;
import java.util.Collections;

import core.genes.Crossover;
import core.genes.Genome;
import core.soup.idvm.Idvm;
import globals.Helpers;
import ui.console.monitor.ModelMonitorIdvm;

public class runCrossover {
	static ArrayList<Thread> mThreads = new ArrayList<Thread>();
	private static final int cTopFittest = 4;

	public static void main(String[] args) throws CloneNotSupportedException, InterruptedException {
		System.out.println("Init");
		Genome lBestOfLastGeneration = new Genome().forceMutation();
		ArrayList<Idvm> lPopulation = new ArrayList<Idvm>();
		lPopulation = initializePopulation();
		int iGeneration = 0;
		while (true) {
			if (iGeneration < 25 || iGeneration % 5 != 0)
				lBestOfLastGeneration = null;
			lPopulation = runPopulation(lPopulation, lBestOfLastGeneration);
			System.out.println("Generation finished: " + iGeneration);
			ArrayList<Idvm> lFittestIdvm = evaluateFitness(lPopulation);
			checkFitness(lFittestIdvm);
			lBestOfLastGeneration = (Genome) lFittestIdvm.get(lFittestIdvm.size() - 1).getGenomeOrigin().clone();
			lPopulation = getOffsprings(lFittestIdvm);
			iGeneration++;
		}
	}

	private static ArrayList<Idvm> getOffsprings(ArrayList<Idvm> pFittestIdvm) throws CloneNotSupportedException {
		ArrayList<Idvm> lOffsprings = new ArrayList<Idvm>();
		ArrayList<Idvm> lParents = new ArrayList<Idvm>();
		for (int iIdvmIdx = pFittestIdvm.size() - 1; iIdvmIdx >= 0; iIdvmIdx--) {
			for (int iCopy = 0; iCopy < cTopFittest; iCopy++) {
				lParents.add(pFittestIdvm.get(iIdvmIdx));
				lParents.add(pFittestIdvm.get(iIdvmIdx));
			}
		}
		while (!lParents.isEmpty()) {
			Idvm lParent1 = lParents.get(0);
			boolean lDifferentParents = false;
			for (Idvm iIdvm : lParents) {
				if (iIdvm != lParent1)
					lDifferentParents = true;
				;
			}
			int lParent2Idx = Helpers.rndIntRange(1, lParents.size() - 1);
			Idvm lParent2 = lParents.get(lParent2Idx);
			if (lDifferentParents == true)
				if (lParent1 == lParent2)
					continue;
			Genome[] lGenomes = new Genome[2];
			lGenomes[0] = (Genome) lParent1.getGenomeOrigin().clone();
			lGenomes[1] = (Genome) lParent2.getGenomeOrigin().clone();
			Crossover lCrossover = new Crossover(lGenomes);
			Genome lOffspringGenome = lCrossover.crossover();
			lOffspringGenome.naturalMutation();
			lOffsprings.add(new Idvm(lOffspringGenome));

			lParents.remove(lParent2Idx);
			lParents.remove(0);
		}
		return lOffsprings;
	}

	private static void checkFitness(ArrayList<Idvm> pFittestIdvm) {
		int lCount = 0;
		for (Idvm iIdvm : pFittestIdvm) {
			lCount += iIdvm.getStepCount();
		}
		System.out.println(lCount + "/" + pFittestIdvm.size());
	}

	private static ArrayList<Idvm> evaluateFitness(ArrayList<Idvm> pPopulation) {
		ArrayList<Integer> lFitness = new ArrayList<Integer>();

		for (Idvm iIdvm : pPopulation) {
			if (!lFitness.contains(iIdvm.getStepCount()))
				lFitness.add(iIdvm.getStepCount());
		}
		Collections.sort(lFitness);
		ArrayList<Idvm> lFittestIdvm = new ArrayList<Idvm>();
		for (int iFitnessIdx = lFitness.size() - 1; iFitnessIdx >= 0; iFitnessIdx--) {
			// for (int iFitnessIdx = 0; iFitnessIdx < lFitness.size() ; iFitnessIdx++) {
			for (Idvm iIdvm : pPopulation) {
				if (iIdvm.getStepCount() == (int) lFitness.get(iFitnessIdx))
					lFittestIdvm.add(iIdvm);
			}
		}
		for (int iFitnessIdx = pPopulation.size() - 1; iFitnessIdx >= pPopulation.size() / cTopFittest; iFitnessIdx--) {
			lFittestIdvm.remove(lFittestIdvm.size() - 1);
		}
		return lFittestIdvm;
	}

	private static ArrayList<Idvm> runPopulation(ArrayList<Idvm> pPopulation, Genome pBestOfLastGeneration)
			throws CloneNotSupportedException, InterruptedException {
		ArrayList<Idvm> lExecutedPopulation = new ArrayList<Idvm>();
		ArrayList<IdvmExecutionThread> mIdvmExecutionThread = new ArrayList<IdvmExecutionThread>();
		mThreads = new ArrayList<Thread>();
		for (Idvm iIdvm : pPopulation) {
			IdvmExecutionThread lIdvmRunner = new IdvmExecutionThread((Genome) iIdvm.getGenomeOrigin().clone());
			Thread lThread = new Thread(lIdvmRunner);
			lThread.start();
			//lThread.join();mThreads.add(lThread);
			mIdvmExecutionThread.add(lIdvmRunner);
		}
		ModelMonitorIdvm lMonitor = new ModelMonitorIdvm();
		if (pBestOfLastGeneration != null)
			lMonitor.runGenome(pBestOfLastGeneration);
		for (Thread iThread : mThreads) {
			iThread.join();
		}
		for (IdvmExecutionThread iExecutionThread : mIdvmExecutionThread) {
			lExecutedPopulation.add(iExecutionThread.getExecutedIdvm());
		}
		return lExecutedPopulation;
	}

	private static ArrayList<Idvm> initializePopulation() {
		ArrayList<Idvm> lPopulation = new ArrayList<Idvm>();
		for (int iIdvmCount = 0; iIdvmCount < 4000; iIdvmCount++) {
			lPopulation.add(new Idvm(new Genome().forceMutation()));
		}
		return lPopulation;
	}

}
