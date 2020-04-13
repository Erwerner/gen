package core.genes;

public interface iGene {
	//TODO 1 IMPL Gene Origin Counter
	void mutate();

	iGene clone() throws CloneNotSupportedException;

	public void setSequendeIndex(int pSequenceIndex);
	public int getSequendeIndex();
}
