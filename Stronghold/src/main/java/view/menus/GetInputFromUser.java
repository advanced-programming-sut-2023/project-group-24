package view.menus;

import java.util.Scanner;

public class GetInputFromUser {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getUserInput() {
        String userInput;
        userInput = scanner.nextLine();
        return userInput;
    }
}
