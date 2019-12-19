package core.genes;

import java.io.Serializable;

public class TestPeristable implements Serializable {
	public TestPeristable(String pString) {
		super();
		mString = pString;
	}


	private static final long serialVersionUID = 1L;
	public String mString;


    @Override
    public boolean equals(Object obj) {
    	TestPeristable lOther = (TestPeristable) obj;
    	return this.mString.equals(lOther.mString);
    }
	
	
}
