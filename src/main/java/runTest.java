import soup.block.BlockType;

public class runTest {

	public static void main(String[] args) throws InterruptedException {
		testThread th1 = new testThread(4000);
		testThread th2 = new testThread(1000);
		Thread thread1 = new Thread(th1);
		Thread thread2 = new Thread(th2);

		thread1.start();
		thread2.start();
		thread1.join();
		System.out.println(th1.val());
		System.out.println(th2.val());
	}


}
