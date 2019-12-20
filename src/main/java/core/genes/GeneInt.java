package core.genes;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class GeneInt implements Serializable, iGene  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pMax;
	private int pMin;
	private int mValue;

	public void mutate() {
		mValue = ThreadLocalRandom.current().nextInt(pMin, pMax + 1);
	}

	public GeneInt(int pPMin, int pPMax, int pStartValue) {
		super();
		pMax = pPMax;
		pMin = pPMin;
		mValue = pStartValue;
	}

	public int getValue() {
		return mValue;
	}

	public void setValue(int pInt) {
		mValue = pInt;
	}

	@Override
	public iGene clone() throws CloneNotSupportedException {
		return new GeneInt(pMin, pMax, mValue);
	}
	@Override
	public boolean equals(Object o) {
		GeneInt other = (GeneInt) o;
		return mValue == other.mValue && pMin == other.pMin && pMax == other.pMax;
	}

}
