package utils;
import java.util.Scanner;

public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public static boolean getBoolean(String s) {
        return false;
    }
}
