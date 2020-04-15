package devutils;

public class Debug {
	public static boolean debugOn = true;
	public static String currentChange = "2K, dies partner, 1 Food Start, 2 Pairing Cost, changed crossover, 100 Food / 2 Partner";

	public static void printCurrentChange() {
		if (debugOn)
			if (currentChange != "")
				System.out.println(currentChange);
	}
}
