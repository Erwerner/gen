package core.genes;

public interface iGene {
	void mutate();

	iGene clone() throws CloneNotSupportedException;

	public void setSequendeIndex(int pSequenceIndex);
	public int getSequendeIndex();
}
