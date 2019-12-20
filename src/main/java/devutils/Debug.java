package devutils;

public class Debug {
	public static boolean debugOn = true;
	public static String currentChange = "wip";

	public static void printCurrentChange() {
		if (debugOn)
			if (currentChange != "")
				System.out.println(currentChange);
	}
}
