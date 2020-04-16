package ui.console.populationprinter;

import core.population.Population;
import ui.controller.iControllPrintPopulation;
import ui.mvc.Model;
import ui.presenter.WrongPresenterType;
import ui.presenter.iPresentPopulation;

public class ModelPopulationPrinter extends Model implements iControllPrintPopulation {
	private iPresentPopulation mPopulation;

	@Override
	public <T> Object getPresenter(Class pType) {
		if (pType == iPresentPopulation.class)
			return mPopulation;
		throw new WrongPresenterType();
	}

	public void print(Population pPopulation) {
		mPopulation = pPopulation;
		new ViewConsolePopulationPrinter(this);
		notifyViews();
	}

}
