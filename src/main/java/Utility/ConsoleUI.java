package main.java.Utility;

import java.util.Scanner;
import java.util.logging.Logger;

public class ConsoleUI {

    private final Scanner scanner;
    private final Logger logger;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.logger = Logger.getLogger(ConsoleUI.class.getName());
    }

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public String readLine(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                logger.warning("Введите корректное число, попробуйте снова.");
            }
        }
    }

    public void printMenu(String... options) {
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s%n", i + 1, options[i]);
        }
    }

    public void printSeparator() {
        System.out.println("=".repeat(40));
    }
}
