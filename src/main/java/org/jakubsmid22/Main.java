package org.jakubsmid22;

import org.jakubsmid22.ui.PokemonManager;
import org.jakubsmid22.ui.TrainerManager;
import org.jakubsmid22.utils.InputScanner;


public class Main {
    public static void main(String[] args) {
        System.out.println("-- Welcome to Pokemon app --\n");

        while (true) {
            System.out.println("""
                    What you want to do?
                    0 - CLOSE PROGRAM
                    1 - ENTER POKEMON MANAGER
                    2 - ENTER TRAINER MANAGER
                    """);

            int choice = InputScanner.getInt();

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> new PokemonManager().showOptions();
                case 2 -> new TrainerManager().showOptions();
                default -> System.out.println("Wrong input!\n");
            }

        }

    }
}