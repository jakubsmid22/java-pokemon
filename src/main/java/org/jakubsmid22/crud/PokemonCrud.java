package org.jakubsmid22.crud;

import org.jakubsmid22.db.DataSource;
import org.jakubsmid22.db.Pokemon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PokemonCrud {

    private static final Logger logger = LoggerFactory.getLogger(PokemonCrud.class);

    public static List<Pokemon> readByName(String name) {

        final String SQL = "select pokemon.id, pokemon.name, trainer.name as trainerName " +
                "from pokemon " +
                "left join trainer on pokemon.trainer_id = trainer.id " +
                "where pokemon.name like ?";

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet result = preparedStatement.executeQuery();
            List<Pokemon> pokemons = new ArrayList<>();

            while (result.next()) {
                pokemons.add(new Pokemon(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("trainerName")
                ));
            }

            return pokemons;


        } catch (SQLException e) {
            logger.error("Error while finding a pokemon: ", e);
            return new ArrayList<>();
        }

    }

    public static int updateName(int id, String name) {

        final String SQL = "update pokemon set name = ? where id = ?";

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error while editing pokemon: ", e);
            return 0;
        }

    }

    public static int updateTrainer(int trainerId, int pokemonId) {
        final String SQL = "update pokemon set trainer_id = ? where id = ?";

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setInt(1, trainerId);
            preparedStatement.setInt(2, pokemonId);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error while editing pokemon: ", e);
            return 0;
        }
    }

    public static int delete(int id) {
        final String SQL = "delete from pokemon where id = ?";

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error while deleting pokemon: ", e);
            return 0;
        }
    }

    public static List<Pokemon> readAll() {

        final String SQL = "select pokemon.id, pokemon.name, trainer.name as trainerName " +
                "from pokemon " +
                "left join trainer on pokemon.trainer_id = trainer.id";

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            ResultSet result = preparedStatement.executeQuery();

            List<Pokemon> pokemons = new ArrayList<>();

            while (result.next()) {
                pokemons.add(new Pokemon(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("trainerName")
                ));
            }

            return pokemons;

        } catch (SQLException e) {
            logger.error("Error while reading pokemons.");
            return new ArrayList<>();
        }
    }

    public static List<Pokemon> readAllWithoutTrainer() {

        final String SQL = "select id, name from pokemon where trainer_id is null";

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            ResultSet result = preparedStatement.executeQuery();

            List<Pokemon> pokemons = new ArrayList<>();

            while (result.next()) {
                pokemons.add(new Pokemon(
                        result.getInt("id"),
                        result.getString("name"),
                        null
                ));
            }

            return pokemons;

        } catch (SQLException e) {
            logger.error("Error while reading pokemons without trainer.");
            return new ArrayList<>();
        }
    }

    public static int create(String name) {
        final String SQL = "insert into pokemon(name) values(?)";

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setString(1, name);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while creating pokemon.");
            return 0;
        }
    }


}
