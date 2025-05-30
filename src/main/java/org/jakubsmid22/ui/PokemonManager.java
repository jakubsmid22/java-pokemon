package org.jakubsmid22.ui;

import org.jakubsmid22.crud.PokemonCrud;
import org.jakubsmid22.db.Pokemon;
import org.jakubsmid22.utils.InputScanner;

import java.util.List;

public class PokemonManager {

    public PokemonManager() {
        System.out.println("-- Welcome to Pokemon Manager --");
    }

    public void showOptions() {

        System.out.println();

        while (true) {
            System.out.println("""
                    What you want to do?
                    0 - GO BACK TO MENU
                    1 - FIND POKEMON BY NAME
                    2 - SHOW ALL POKEMONS
                    3 - SHOW ALL POKEMONS WITHOUT TRAINER
                    4 - ADD POKEMON
                    """);

            int choice = InputScanner.getInt();

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> findPokemonByName();
                case 2 -> showAll();
                case 3 -> showAllWithoutTrainer();
                case 4 -> addPokemon();
                default -> System.out.println("Wrong input!");
            }

        }

    }

    void findPokemonByName() {
        System.out.print("Enter name: ");
        List<Pokemon> pokemons = PokemonCrud.readByName(InputScanner.getString());

        if (pokemons.isEmpty()) {
            System.out.println("\nNo pokemon was found.\n");
            return;
        }

        pokemons.forEach(System.out::println);

        System.out.println("""
                What you want to do?
                0 - GO BACK
                1 - EDIT POKEMON NAME
                2 - DELETE POKEMON
                """);

        int choice = InputScanner.getInt();

        switch (choice) {
            case 0 -> {
                return;

            }
            case 1 -> {
                if (pokemons.size() > 1) {
                    System.out.println("Enter an id of a pokemon you want to edit");

                    int id = InputScanner.getInt();

                    for (Pokemon pokemon : pokemons) {
                        if (pokemon.getId() == id) {
                            editPokemon(pokemon);
                            return;
                        }
                    }

                    System.out.println("Pokemon with id " + id + " does not exist.\n");

                } else {
                    editPokemon(pokemons.get(0));
                }
            }

            case 2 -> {
                if (pokemons.size() > 1) {
                    System.out.println("Enter an id of a pokemon you want to delete");

                    int id = InputScanner.getInt();

                    for (Pokemon pokemon : pokemons) {
                        if (pokemon.getId() == id) {
                            deletePokemon(pokemon.getId());
                            return;
                        }
                    }

                    System.out.println("Pokemon with id " + id + " does not exist.\n");

                } else {
                    deletePokemon(pokemons.get(0).getId());
                }
            }

            default -> System.out.println("Wrong input!");
        }


    }


    void editPokemon(Pokemon pokemon) {
        System.out.print("Enter a new name: ");
        if (PokemonCrud.updateName(pokemon.getId(), InputScanner.getString()) > 0) {
            System.out.println("Pokemon edited.\n");
        } else {
            System.out.println("Error occurred");
        }
    }

    void deletePokemon(int id) {
        if (PokemonCrud.delete(id) > 0) {
            System.out.println("Pokemon #" + id + " deleted.\n");
        } else {
            System.out.println("Error occurred");
        }
    }

    void showAll() {
        List<Pokemon> pokemons = PokemonCrud.readAll();
        System.out.println("All pokemons:\n");
        pokemons.forEach(System.out::println);
    }

    void showAllWithoutTrainer() {
        List<Pokemon> pokemons = PokemonCrud.readAllWithoutTrainer();
        System.out.println("Pokemons without trainer:\n");
        pokemons.forEach(System.out::println);
    }

    void addPokemon() {
        System.out.print("Enter name: ");
        if (PokemonCrud.create(InputScanner.getString()) > 0) {
            System.out.println("\nPokemon created.\n");
        } else {
            System.out.println("Error occurred.");
        }
    }


}
