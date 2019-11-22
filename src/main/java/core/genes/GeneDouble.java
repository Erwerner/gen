package core.genes;

import globals.Helpers;

public class GeneDouble implements iGene {
	private Double mValue;
	Double mMin;
	Double mMax;

	public GeneDouble(Double pMin, Double pMax, Double pStartValue) {
		mMin = pMin;
		mMax = pMax;
		setValue(pStartValue);
	}

	public void mutate() {
		mValue = Helpers.rndDouble(mMin, mMax);
	}

	public iGene clone() throws CloneNotSupportedException {
		return new GeneDouble(mMin, mMax, mValue);
	}

	public Double getValue() {
		return mValue;
	}

	public void setValue(Double pValue) {
		mValue = pValue;
	}

}
