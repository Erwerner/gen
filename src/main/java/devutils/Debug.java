package devutils;

public class Debug {
	public static boolean debugOn = true;
	public static String currentChange = "Gen+350: Pairing 1, Move 0; Life 2;101-61;10-34; Slow";

	public static void printCurrentChange() {
		if (debugOn)
			if (currentChange != "")
				System.out.println(currentChange);
	}
}
