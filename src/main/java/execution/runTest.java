package execution;

import globals.Helpers;

public class runTest {

	public static void main(String[] args) throws InterruptedException {
		for(int i=0;i<100;i++)
			System.out.println(Helpers.rndDouble(0.0, 1.0));
	}
}
