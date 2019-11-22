package core.genes;

public interface iGene {

	void mutate();
	iGene clone() throws CloneNotSupportedException;
}
