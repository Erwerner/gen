package core.soup;

import java.util.ArrayList;
import java.util.List;

import core.soup.idvm.Idvm;

public class Population {
	List<Idvm> mIdvmList = new ArrayList<Idvm>();

	public int getIdvmCount() {
		return mIdvmList.size();
	}

	public void appendIdvm(Idvm pIdvm) {
		mIdvmList.add(pIdvm);
	}

}
