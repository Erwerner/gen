package devutils;

public class Debug {
	public static boolean debugOn = true;
	public static String currentChange = "best 8, no hunger, copy Decision 0.5, no clear, max food *10";

	public static void printCurrentChange() {
		if (debugOn)
			if (currentChange != "")
				System.out.println(currentChange);
	}
}
