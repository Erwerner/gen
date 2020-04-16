package service;

import java.util.ArrayList;

import core.genes.Genome;
import core.population.Population;
import core.soup.EnvironmentConfig;
import core.soup.idvm.Idvm;
import globals.Helpers;
import ui.console.monitor.ModelMonitorIdvm;

public class PopulationRunner {

	private Population mPopulation;

	public PopulationRunner(Population pPopulation) {
		mPopulation = pPopulation;
	}

	public void run(EnvironmentConfig pEnvironmentConfig) throws CloneNotSupportedException, InterruptedException {

		ArrayList<Idvm> lExecutedPopulation = new ArrayList<Idvm>();
		ArrayList<IdvmExecutionThread> mIdvmExecutionThread = new ArrayList<IdvmExecutionThread>();
		ArrayList<Thread> lThreads = new ArrayList<Thread>();
		for (Idvm iIdvm : mPopulation.getIdvmList()) {
			new Helpers().waitForConfigFlag("run");
			IdvmExecutionThread lIdvmRunner = new IdvmExecutionThread((Genome) iIdvm.getGenomeOrigin().clone(),
					pEnvironmentConfig);
			Thread lThread = new Thread(lIdvmRunner);
			lThread.start();
			if (!new Helpers().isFlagTrue("parallel"))
				lThread.join();
			lThreads.add(lThread);
			mIdvmExecutionThread.add(lIdvmRunner);
			if (new Helpers().isFlagTrue("monitor"))
				new ModelMonitorIdvm().runGenome((Genome) mPopulation.getIdvmList()
						.get(Helpers.rndInt(mPopulation.getIdvmList().size() - 1)).getGenomeOrigin().clone());
		}
		for (Thread iThread : lThreads) {
			if (new Helpers().isFlagTrue("monitor"))
				new ModelMonitorIdvm().runGenome((Genome) mPopulation.getIdvmList()
						.get(Helpers.rndInt(mPopulation.getIdvmList().size() - 1)).getGenomeOrigin().clone());
			iThread.join();
		}
		for (IdvmExecutionThread iExecutionThread : mIdvmExecutionThread) {
			lExecutedPopulation.add(iExecutionThread.getExecutedIdvm());
		}
		mPopulation.setIdvmList(lExecutedPopulation);
	}

}
