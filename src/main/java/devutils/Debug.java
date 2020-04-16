package devutils;

public class Debug {
	public static boolean debugOn = true;
	public static String currentChange = "Pairing 1, Move 0; Life 2;71-31;10-34; Partner/6; Sensor 5";

	public static void printCurrentChange() {
		if (debugOn)
			if (currentChange != "")
				System.out.println(currentChange);
	}
}
