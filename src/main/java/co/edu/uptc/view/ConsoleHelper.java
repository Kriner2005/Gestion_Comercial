package co.edu.uptc.view;

import co.edu.uptc.i18n.I18n;

import java.util.Scanner;

public class ConsoleHelper {

    private static ConsoleHelper instance;
    private final Scanner scanner = new Scanner(System.in);

    private ConsoleHelper() {
    }

    public static ConsoleHelper getInstance() {
        if (instance == null) {
            instance = new ConsoleHelper();
        }
        return instance;
    }

    public String readLine() {
        return scanner.nextLine().trim();
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public String readOption() {
        System.out.print(I18n.msg("menu.option_prompt"));
        return scanner.nextLine().trim();
    }

    public void print(String text) {
        System.out.println(text);
    }

    public void printBlankLine() {
        System.out.println();
    }

    public void printSuccess(String msg) {
        System.out.println("  [OK] " + msg);
    }

    public void printError(String msg) {
        System.err.println("  [ERROR] " + msg);
    }

    public void printSeparator() {
        System.out.println("-".repeat(60));
    }

    public void printHeader(String title) {
        String line = "=".repeat(60);
        System.out.println(line);
        System.out.printf("  %s%n", title);
        System.out.println(line);
    }
}