package org.jakubsmid22.ui;

import org.jakubsmid22.crud.PokemonCrud;
import org.jakubsmid22.crud.TrainerCrud;
import org.jakubsmid22.db.Pokemon;
import org.jakubsmid22.db.Trainer;
import org.jakubsmid22.utils.InputScanner;

import java.util.List;

public class TrainerManager {

    public TrainerManager() {
        System.out.println("-- Welcome to Trainer Manager --");
    }

    public void showOptions() {

        System.out.println();

        while (true) {
            System.out.println("""
                    What you want to do?
                    0 - GO BACK TO MENU
                    1 - FIND TRAINER BY NAME
                    2 - SHOW ALL TRAINERS
                    3 - ORDER TRAINERS BY NUMBER OF POKEMONS
                    4 - ADD TRAINER
                    """);

            int choice = InputScanner.getInt();

            switch (choice) {
                case 0 -> {
                    return;
                }

                case 1 -> findTrainerByName();

                case 2 -> showAllTrainers();

                case 3 -> orderTrainersByNumberOfPokemons();

                case 4 -> addTrainer();

                default -> System.out.println("Wrong input!");
            }

        }

    }

    void findTrainerByName() {
        System.out.print("Enter name: ");
        List<Trainer> trainers = TrainerCrud.readByName(InputScanner.getString());

        if (trainers.isEmpty()) {
            System.out.println("\nNo trainer was found.\n");
            return;
        }

        trainers.forEach(System.out::println);

        System.out.println("""
                What you want to do?
                0 - GO BACK
                1 - EDIT TRAINER NAME
                2 - DELETE TRAINER
                3 - SHOW TRAINER'S POKEMONS
                4 - CATCH POKEMON
                """);

        int choice = InputScanner.getInt();

        switch (choice) {
            case 0 -> {
                return;

            }
            case 1 -> {
                if (trainers.size() > 1) {
                    System.out.println("Enter an id of a trainer you want to edit");

                    int id = InputScanner.getInt();

                    for (Trainer trainer : trainers) {
                        if (trainer.getId() == id) {
                            editTrainer(trainer);
                            return;
                        }
                    }

                    System.out.println("Trainer with id " + id + " does not exist.\n");

                } else {
                    editTrainer(trainers.get(0));
                }
            }

            case 2 -> {
                if (trainers.size() > 1) {
                    System.out.println("Enter an id of a trainer you want to delete");

                    int id = InputScanner.getInt();

                    for (Trainer trainer : trainers) {
                        if (trainer.getId() == id) {
                            deleteTrainer(id);
                            return;
                        }
                    }

                    System.out.println("Trainer with id " + id + " does not exist.\n");

                } else {
                    deleteTrainer(trainers.get(0).getId());
                }
            }

            case 3 -> {
                if (trainers.size() > 1) {
                    System.out.println("Enter an id of a trainer whose pokemons you want to see");

                    int id = InputScanner.getInt();

                    for (Trainer trainer : trainers) {
                        if (trainer.getId() == id) {
                            showTrainersPokemons(trainer);
                            return;
                        }
                    }

                    System.out.println("Trainer with id " + id + " does not exist.\n");

                } else {
                    showTrainersPokemons(trainers.get(0));
                }
            }

            case 4 -> {
                if (trainers.size() > 1) {
                    System.out.println("Enter an id of a trainer who will catch a pokemon");

                    int id = InputScanner.getInt();

                    for (Trainer trainer : trainers) {
                        if (trainer.getId() == id) {
                            catchPokemon(trainer);
                            return;
                        }
                    }

                    System.out.println("Trainer with id " + id + " does not exist.\n");

                } else {
                    catchPokemon(trainers.get(0));
                }
            }

            default -> System.out.println("Wrong input!");
        }

    }

    void editTrainer(Trainer trainer) {
        System.out.print("Enter a new name: ");
        if (TrainerCrud.update(trainer.getId(), InputScanner.getString()) > 0) {
            System.out.println("Trainer edited.\n");
        } else {
            System.out.println("Error occurred");
        }
    }

    void deleteTrainer(int id) {
        if (TrainerCrud.delete(id) > 0) {
            System.out.println("Trainer #" + id + " deleted.\n");
        } else {
            System.out.println("Error occurred");
        }
    }

    void showTrainersPokemons(Trainer trainer) {
        List<Pokemon> pokemons = TrainerCrud.readTrainersPokemons(trainer);
        System.out.println(trainer.getName() + "'s pokemons:\n");
        if (pokemons.isEmpty()) {
            System.out.println(trainer.getName() + " does not have any pokemon.\n");
            return;
        }
        pokemons.forEach(System.out::println);
    }

    void catchPokemon(Trainer trainer) {
        List<Pokemon> pokemonsWithoutTrainer = PokemonCrud.readAllWithoutTrainer();
        System.out.println("\nPokemons available:\n");
        pokemonsWithoutTrainer.forEach(System.out::println);
        System.out.println("Enter an id of pokemon who will be catched:");
        int pokemonId = InputScanner.getInt();

        for (Pokemon pokemon : pokemonsWithoutTrainer) {
            if (pokemon.getId() == pokemonId) {
                if (PokemonCrud.updateTrainer(trainer.getId(), pokemonId) > 0) {
                    System.out.println("\n" + trainer.getName() + " caught " + pokemon.getName() + "!\n");
                }
                return;
            }
        }

        System.out.println("Pokemon with id " + pokemonId + " have a trainer or does not exist.\n");
    }

    void showAllTrainers() {
        List<Trainer> trainers = TrainerCrud.readAll();
        System.out.println("All trainers:\n");
        trainers.forEach(System.out::println);
    }

    void orderTrainersByNumberOfPokemons() {
        List<Trainer> trainers = TrainerCrud.orderByPokemons();
        System.out.println("Sorted trainers:");
        trainers.forEach(System.out::println);
    }

    void addTrainer() {
        System.out.print("Enter name: ");
        if (TrainerCrud.create(InputScanner.getString()) > 0) {
            System.out.println("\nTrainer created.\n");
        } else {
            System.out.println("\nError occurred.\n");
        }
    }

}
