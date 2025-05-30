package org.jakubsmid22.utils;

import java.util.Scanner;

public class InputScanner {

    static final Scanner scanner = new Scanner(System.in);

    public static int getInt() {
        while (true) {
            try {
                int num = scanner.nextInt();
                scanner.nextLine();
                return num;
            } catch (RuntimeException e) {
                System.out.println("Please enter a number!");
                scanner.nextLine();
            }
        }
    }

    public static String getString() {
        return scanner.nextLine();
    }

}
