package core.population;

import core.genes.iGene;

public class PopulationGene {

	private int mHostCounter;
	private iGene mOriginGene;
	public int mSequenceIndex;

	public PopulationGene(iGene pGene) {
		mOriginGene = pGene;
		mSequenceIndex = pGene.getSequendeIndex();
		increaseHostCounter();
	}

	public void increaseHostCounter() {
		mHostCounter++;
	};

	public int getHostCounter() {
		return mHostCounter;
	}

	public iGene getOriginGene() {
		return mOriginGene;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !this.getClass().isAssignableFrom(o.getClass()))
			return false;

		PopulationGene other = (PopulationGene) o;
		return mOriginGene.equals(other.mOriginGene) && mSequenceIndex == other.mSequenceIndex;
	}

	@Override
	public String toString() {
		return "#" + mHostCounter + " " + mOriginGene.getClass() + " @" + mSequenceIndex + ": " + mOriginGene;
	}

}
