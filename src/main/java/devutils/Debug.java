package devutils;

public class Debug {
	public static boolean debugOn = true;
	public static String currentChange = "defence stops enemy, enemy random at border, 250 food";

	public static void printCurrentChange() {
		if (debugOn)
			if (currentChange != "")
				System.out.println(currentChange);
	}
}
