package devutils;

public class Debug {
	public static boolean debugOn = true;
	public static String currentChange = "Copy 5; Reset 7, Hunger";

	public static void printCurrentChange() {
		if (debugOn)
			if (currentChange != "")
				System.out.println(currentChange);
	}
}
