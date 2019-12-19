package execution;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import core.genes.Crossover;
import core.genes.Genome;
import core.soup.block.BlockType;
import core.soup.block.IdvmCell;
import core.soup.idvm.Idvm;
import devutils.Debug;
import globals.Helpers;
import ui.console.monitor.ModelMonitorIdvm;

// TODO 0 IMPL new Pairing 2x/2 + 2x/4 + 2x2/8
public class runCrossover {
	private static final int cPopulation = 1024 * 6;
	private static final int cTopFittest = 8;
	static ArrayList<Thread> mThreads = new ArrayList<Thread>();

	public static void main(String[] args)
			throws CloneNotSupportedException, InterruptedException, FileNotFoundException, IOException {
		System.out.println("Init");
		Debug.printCurrentChange();
		Genome lBestOfLastGeneration = new Genome().forceMutation();
		ArrayList<Idvm> lPopulation = new ArrayList<Idvm>();
		lPopulation = initializePopulation();
		int iGeneration = 0;
		while (true) {
			lPopulation = runPopulation(lPopulation, lBestOfLastGeneration);
			ArrayList<Idvm> lFittestIdvm = evaluateFitness(lPopulation);
			checkFitness(lFittestIdvm);
			lBestOfLastGeneration = (Genome) lFittestIdvm.get(lFittestIdvm.size() - 4).getGenomeOrigin().clone();
			Debug.printCurrentChange();
			System.out.println("Generation finished: " + iGeneration);
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
		printBlockStats(pFittestIdvm, 4);
		printBlockStats(pFittestIdvm, 6);
		printBlockStats(pFittestIdvm, 10);
		printBlockStats(pFittestIdvm, 14);
		printTargetStats(pFittestIdvm);
	}

	private static void printTargetStats(ArrayList<Idvm> pFittestIdvm) {
		Double lPercentageTotal = 0.0;
		for (Idvm iIdvm : pFittestIdvm) {
			lPercentageTotal += iIdvm.getGenomeOrigin().getPercentageOfWrongTargetDecision(14);
		}
		System.out.println("Wrong Target Decision: " + lPercentageTotal);
	}

	private static void printBlockStats(ArrayList<Idvm> pFittestIdvm, int pCount) {
		System.out.print(pCount + " Cells: ");
		BlockType[] lCellBlocks = { BlockType.DEFENCE, BlockType.MOVE, BlockType.LIFE, BlockType.SENSOR };
		for (BlockType iBlockType : lCellBlocks) {
			int lTotalBlockCount = 0;
			for (Idvm iIdvm : pFittestIdvm) {
				for (int idx = 0; idx < (pCount); idx++) {
					IdvmCell lCellGrow = iIdvm.getGenomeOrigin().cellGrow.get(idx);
					if (lCellGrow.getBlockType() == iBlockType)
						lTotalBlockCount++;
				}
			}
			System.out.print(100 * lTotalBlockCount / pFittestIdvm.size() / (pCount) + "% " + iBlockType + "; ");
		}
		System.out.println("");
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
			// for (int iFitnessIdx = 0; iFitnessIdx < lFitness.size() ;
			// iFitnessIdx++) {
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
			throws CloneNotSupportedException, InterruptedException, FileNotFoundException, IOException {
		ArrayList<Idvm> lExecutedPopulation = new ArrayList<Idvm>();
		ArrayList<IdvmExecutionThread> mIdvmExecutionThread = new ArrayList<IdvmExecutionThread>();
		mThreads = new ArrayList<Thread>();
		for (Idvm iIdvm : pPopulation) {
			new Helpers().waitForConfigFlag("run");
			IdvmExecutionThread lIdvmRunner = new IdvmExecutionThread((Genome) iIdvm.getGenomeOrigin().clone());
			Thread lThread = new Thread(lIdvmRunner);
			lThread.start();
			if (!new Helpers().isFlagTrue("parallel"))
				lThread.join();
			mThreads.add(lThread);
			mIdvmExecutionThread.add(lIdvmRunner);
			if (new Helpers().isFlagTrue("monitor"))
				new ModelMonitorIdvm().runGenome(pBestOfLastGeneration);
		}
		for (Thread iThread : mThreads) {
			if (new Helpers().isFlagTrue("monitor"))
				new ModelMonitorIdvm().runGenome(pBestOfLastGeneration);
			iThread.join();
		}
		for (IdvmExecutionThread iExecutionThread : mIdvmExecutionThread) {
			lExecutedPopulation.add(iExecutionThread.getExecutedIdvm());
		}
		return lExecutedPopulation;
	}

	private static ArrayList<Idvm> initializePopulation() {
		ArrayList<Idvm> lPopulation = new ArrayList<Idvm>();
		for (int iIdvmCount = 0; iIdvmCount < cPopulation; iIdvmCount++) {
			lPopulation.add(new Idvm(new Genome().forceMutation()));
		}
		return lPopulation;
	}

}
