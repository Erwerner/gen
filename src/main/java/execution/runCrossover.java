package execution;

import java.util.ArrayList;
import java.util.Collections;

import core.genes.Crossover;
import core.genes.Genome;
import core.soup.Soup;
import core.soup.idvm.Idvm;
import globals.Helpers;

public class runCrossover {

	public static void main(String[] args) throws CloneNotSupportedException {
		ArrayList<Idvm> lPopulation = new ArrayList<Idvm>();
		lPopulation = initializePopulation();
		runPopulation(lPopulation);
		ArrayList<Idvm> lFittestIdvm = evaluateFitness(lPopulation);
		checkFitness(lFittestIdvm);
		lPopulation = getOffsprings(lFittestIdvm);
	}

	private static ArrayList<Idvm> getOffsprings(ArrayList<Idvm> pFittestIdvm) throws CloneNotSupportedException {
		ArrayList<Idvm> lOffsprings = new ArrayList<Idvm>();
		ArrayList<Idvm> lParents = new ArrayList<Idvm>();
		System.out.println("Fittest" + pFittestIdvm.size());
		for (int iIdvmIdx = 0; iIdvmIdx <= pFittestIdvm.size() - 1; iIdvmIdx++) {
			lParents.add(pFittestIdvm.get(iIdvmIdx));
			lParents.add(pFittestIdvm.get(iIdvmIdx));
			lParents.add(pFittestIdvm.get(iIdvmIdx));
			lParents.add(pFittestIdvm.get(iIdvmIdx));
		}
		while (!lParents.isEmpty()) {
			System.out.println("Rest" + lParents.size());
			Idvm lParent1 = lParents.get(0);
			int lParent2Idx = Helpers.rndIntRange(1, lParents.size() - 1);
			Idvm lParent2 = lParents.get(lParent2Idx);
			if (lParent1 == lParent2)
				continue;
			Genome[] lGenomes = new Genome[2];
			lGenomes[0] = (Genome) lParent1.getGenomeOrigin().clone();
			lGenomes[1] = (Genome) lParent2.getGenomeOrigin().clone();
			Crossover lCrossover = new Crossover(lGenomes);
			System.out.println(lParent1.getStepCount() + "+" + lParent2.getStepCount());
			Genome lOffspringGenome = lCrossover.crossover();
			lOffspringGenome.naturalMutation();
			lOffsprings.add(new Idvm(lOffspringGenome));

			lParents.remove(lParent2Idx);
			lParents.remove(0);
		}
		return lOffsprings;
	}

	private static void checkFitness(ArrayList<Idvm> pFittestIdvm) {
		for (Idvm iIdvm : pFittestIdvm) {
			System.out.println(iIdvm.getStepCount());
		}
	}

	private static ArrayList<Idvm> evaluateFitness(ArrayList<Idvm> pPopulation) {
		ArrayList<Integer> lFitness = new ArrayList<Integer>();

		for (Idvm iIdvm : pPopulation) {
			if (!lFitness.contains(iIdvm.getStepCount()))
				lFitness.add(iIdvm.getStepCount());
		}
		Collections.sort(lFitness);
		ArrayList<Idvm> lFittestIdvm = new ArrayList<Idvm>();
		for (int iFitnessIdx = lFitness.size() - 1; iFitnessIdx >= lFitness.size()/2 - 1; iFitnessIdx--) {
			for (Idvm iIdvm : pPopulation) {
				if (iIdvm.getStepCount() == (int) lFitness.get(iFitnessIdx))
					lFittestIdvm.add(iIdvm);
			}
		}
		for (int iFitnessIdx = lFittestIdvm.size() - 1; iFitnessIdx >= lFittestIdvm.size() / 2; iFitnessIdx--) {
			//lFittestIdvm.remove(iFitnessIdx);
		}
		return lFittestIdvm;
	}

	private static void runPopulation(ArrayList<Idvm> pPopulation) {
		for (Idvm iIdvm : pPopulation) {
			new Soup(iIdvm).executeIdvm();
		}
	}

	private static ArrayList<Idvm> initializePopulation() {
		ArrayList<Idvm> lPopulation = new ArrayList<Idvm>();
		for (int iIdvmCount = 0; iIdvmCount < 100; iIdvmCount++) {
			lPopulation.add(new Idvm(new Genome().forceMutation()));
		}
		return lPopulation;
	}

}
