import java.io.*;
import java.nio.charset.*;

public class UtilsTest {

	public static void inputStreamToStringTest() {
		String origStr = "Sample string in input stream";
		InputStream is = new ByteArrayInputStream (origStr.getBytes(StandardCharsets.UTF_8));
		String convertedStr = null;

		try {
			convertedStr = Utils.inputStreamToString(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (origStr.equals(convertedStr)) {
			System.out.println("inpuStreamToStringTest() passed");
		} else {
			System.out.println("inputStreamToStringTest() failed");
		}
	} 

	public static void runTailCommandTest() {
		System.out.println(Utils.runTailCommand("../build/a.txt"));
	}

	public static void removeTailTest() {
		System.out.println(Utils.removeTail("../build/a.txt"));
	}

	public static void prependStringToFileUsingEdTest() {
		Utils.prependStringToFileUsingEd("hello1\nhello2", "test.txt");
	}

	public static void main (String[] args) {
		/* inputStreamToStringTest();
		runTailCommandTest();
		removeTailTest(); */
		prependStringToFileUsingEdTest();
	}
}

