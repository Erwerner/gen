package execution;

import globals.Helpers;

import java.util.ArrayList;

import ui.console.monitor.ModelMonitorIdvm;
import core.genes.Crossover;
import core.genes.Genome;
import core.genes.GenomePersister;
import core.soup.EnvironmentConfig;
import core.soup.block.BlockType;
import core.soup.block.IdvmCell;
import core.soup.idvm.Idvm;
import devutils.Debug;

public class runCrossover {
	private static final int cInitialPopulationMultiplikator = 1;
	private static final int cPopulation = 1024 * 10 * cInitialPopulationMultiplikator;
	static ArrayList<Thread> mThreads = new ArrayList<Thread>();
	private static GenomePersister mPersister;

	public static void main(String[] args) throws CloneNotSupportedException, InterruptedException {
		System.out.println("Init");
		Debug.printCurrentChange();
		mPersister = new GenomePersister();
		//ArrayList<Idvm> lPopulation = initializePopulation();
		ArrayList<Idvm> lPopulation = loadPopulation();
		int iGeneration = 1;
		while (true) {
			EnvironmentConfig lEnvironmentConfig = new EnvironmentConfig();
			lEnvironmentConfig.cEnemySupply = lEnvironmentConfig.cEnemySupply + (iGeneration % 25) * 1;
			lEnvironmentConfig.cFoodSupply = lEnvironmentConfig.cFoodSupply - (iGeneration % 41) * 1;
			lPopulation = runPopulation(lPopulation, lEnvironmentConfig);
			ArrayList<Idvm> lFittestIdvm = evaluateFitness(lPopulation);
			checkFitness(lFittestIdvm);
			Debug.printCurrentChange();
			System.out.println("Generation finished: " + iGeneration + ", Food: " + lEnvironmentConfig.cFoodSupply
					+ ", Enemy: " + lEnvironmentConfig.cEnemySupply);
			lPopulation = getOffsprings(lFittestIdvm);
			iGeneration++;
			if (new Helpers().isFlagTrue("persist"))
				persistPopulation(lPopulation);
		}
	}

	private static ArrayList<Idvm> loadPopulation() {
		ArrayList<Idvm> lPopulation = new ArrayList<Idvm>();
		for (Integer i = 0; i < cPopulation / 4 - 1; i++) {
			lPopulation.add(new Idvm(mPersister.load(i.toString())));
		}
		return lPopulation;
	}

	private static void persistPopulation(ArrayList<Idvm> pPopulation) {
		Integer lCount = 0;
		System.out.println("wait for persisting...");
		for (Idvm iIdvm : pPopulation) {
			mPersister.save(iIdvm.getGenomeOrigin(), lCount.toString());
			lCount++;
		}
		System.out.println("persisted");
	}

	private static ArrayList<Idvm> getOffsprings(ArrayList<Idvm> pFittestIdvm) throws CloneNotSupportedException {
		ArrayList<Idvm> lOffsprings = new ArrayList<Idvm>();
		ArrayList<Idvm> lParents = new ArrayList<Idvm>();
		for (int iIdvmIdx = pFittestIdvm.size() - 1; iIdvmIdx >= 0; iIdvmIdx--) {
			lParents.add(pFittestIdvm.get(iIdvmIdx));
			lParents.add(pFittestIdvm.get(iIdvmIdx));
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
		int lStepCount = 0;
		for (Idvm iIdvm : pFittestIdvm) {
			lStepCount += iIdvm.getStepCount();
		}
		int lPartnerCount = 0;
		for (Idvm iIdvm : pFittestIdvm) {
			lPartnerCount += iIdvm.getPartnerCount();
		}
		System.out.println("Average steps: " + lStepCount / pFittestIdvm.size() + " * " + pFittestIdvm.size());
		System.out.println(
				"Average partner: " + 100 * lPartnerCount / pFittestIdvm.size() + "/100 * " + pFittestIdvm.size());
		printBlockStats(pFittestIdvm, 4);
		printBlockStats(pFittestIdvm, 6);
		printBlockStats(pFittestIdvm, 8);
		printBlockStats(pFittestIdvm, 10);
		printBlockStats(pFittestIdvm, 12);
	}

	private static void printBlockStats(ArrayList<Idvm> pFittestIdvm, int pCount) {
		System.out.print(pCount + " Cells: ");
		BlockType[] lCellBlocks = { BlockType.DEFENCE, BlockType.MOVE, BlockType.LIFE, BlockType.SENSOR,
				BlockType.NULL };
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
		ArrayList<Idvm> lFittestIdvm = new ArrayList<Idvm>();
		for (Idvm iIdvm : pPopulation) {
				for (int iPairings = 0; iPairings < iIdvm.getPartnerCount() * 16; iPairings++)
					lFittestIdvm.add(iIdvm);
		}
		while (lFittestIdvm.size() > cPopulation / cInitialPopulationMultiplikator)
			lFittestIdvm.remove(Helpers.rndInt(lFittestIdvm.size() - 1));
		return lFittestIdvm;
	}

	private static ArrayList<Idvm> runPopulation(ArrayList<Idvm> pPopulation, EnvironmentConfig pEnvironmentConfig)
			throws CloneNotSupportedException, InterruptedException {
		ArrayList<Idvm> lExecutedPopulation = new ArrayList<Idvm>();
		ArrayList<IdvmExecutionThread> mIdvmExecutionThread = new ArrayList<IdvmExecutionThread>();
		mThreads = new ArrayList<Thread>();
		for (Idvm iIdvm : pPopulation) {
			new Helpers().waitForConfigFlag("run");
			IdvmExecutionThread lIdvmRunner = new IdvmExecutionThread((Genome) iIdvm.getGenomeOrigin().clone(),
					pEnvironmentConfig);
			Thread lThread = new Thread(lIdvmRunner);
			lThread.start();
			if (!new Helpers().isFlagTrue("parallel"))
				lThread.join();
			mThreads.add(lThread);
			mIdvmExecutionThread.add(lIdvmRunner);
			if (new Helpers().isFlagTrue("monitor"))
				new ModelMonitorIdvm().runGenome(
						(Genome) pPopulation.get(Helpers.rndInt(pPopulation.size() - 1)).getGenomeOrigin().clone());
		}
		for (Thread iThread : mThreads) {
			if (new Helpers().isFlagTrue("monitor"))
				new ModelMonitorIdvm().runGenome(
						(Genome) pPopulation.get(Helpers.rndInt(pPopulation.size() - 1)).getGenomeOrigin().clone());
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
