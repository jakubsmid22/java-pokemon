package org.jakubsmid22.db;

public class Pokemon {

    private final int id;
    private final String name;
    private final String trainer;

    public Pokemon(int id, String name, String trainer) {
        this.id = id;
        this.name = name;
        this.trainer = trainer;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Pokemon #" + id + "\n" +
                "Name: " + name + "\n" +
                "Trainer: " + (trainer != null ? trainer : "no trainer") + "\n";
    }

}
