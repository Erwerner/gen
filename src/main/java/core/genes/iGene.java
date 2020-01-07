package core.genes;

public interface iGene {

	//TODO 4 IMPL Gene Origin Counter
	void mutate();

	iGene clone() throws CloneNotSupportedException;
}
