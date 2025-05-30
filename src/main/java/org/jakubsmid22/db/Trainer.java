package org.jakubsmid22.db;

public class Trainer {

    private final int id;
    private final String name;
    private int pokemonsCount = -1;

    public Trainer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setPokemonsCount(int pokemonsCount) {
        this.pokemonsCount = pokemonsCount;
    }

    @Override
    public String toString() {
        return "\nTrainer #" + this.id + "\n" +
                "Name: " + this.name + "\n" +
                (pokemonsCount >= 0 ? "Number of pokemons: " + pokemonsCount + "\n" : "");
    }
}
