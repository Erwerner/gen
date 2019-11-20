package core.genes;

public interface iGene {

	void mutate(Double pMutationRate);
	iGene clone() throws CloneNotSupportedException;
}
