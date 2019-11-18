
public class testThread implements Runnable{

	private int mVal=0;
	private int mTime;


	public testThread(int pI) {
mTime =pI;
	}

	public void run() {
		try {
			Thread.sleep(mTime);
			mVal=1;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int val() {
		// TODO Auto-generated method stub
		return mVal;
	}

}
