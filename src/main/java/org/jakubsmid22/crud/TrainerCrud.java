package org.jakubsmid22.crud;

import org.jakubsmid22.db.DataSource;
import org.jakubsmid22.db.Pokemon;
import org.jakubsmid22.db.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainerCrud {

    private static final Logger logger = LoggerFactory.getLogger(TrainerCrud.class);

    public static List<Trainer> readByName(String name) {

        final String SQL = "select * from trainer where name like ?";

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet result = preparedStatement.executeQuery();
            List<Trainer> trainers = new ArrayList<>();

            while (result.next()) {
                trainers.add(new Trainer(
                        result.getInt("id"),
                        result.getString("name")));
            }

            return trainers;


        } catch (SQLException e) {
            logger.error("Error while finding a trainer: ", e);
            return new ArrayList<>();
        }

    }

    public static int update(int id, String name) {

        final String SQL = "update trainer set name = ? where id = ?";

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error while editing trainer: ", e);
            return 0;
        }

    }

    public static int delete(int id) {
        final String SQL = "delete from trainer where id = ?";

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error while deleting trainer: ", e);
            return 0;
        }
    }

    public static List<Pokemon> readTrainersPokemons(Trainer trainer) {
        final String SQL = "select * from pokemon where trainer_id = ?";

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setInt(1, trainer.getId());
            ResultSet result = preparedStatement.executeQuery();
            List<Pokemon> pokemons = new ArrayList<>();

            while (result.next()) {
                pokemons.add(new Pokemon(
                        result.getInt("id"),
                        result.getString("name"),
                        trainer.getName()
                ));
            }


            return pokemons;

        } catch (SQLException e) {
            logger.error("Error while reading pokemons: ", e);
            return new ArrayList<>();
        }
    }

    public static List<Trainer> readAll() {

        final String SQL = "select * from trainer";

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            ResultSet result = preparedStatement.executeQuery();

            List<Trainer> trainers = new ArrayList<>();

            while (result.next()) {
                trainers.add(new Trainer(
                        result.getInt("id"),
                        result.getString("name")
                ));
            }

            return trainers;

        } catch (SQLException e) {
            logger.error("Error while reading trainers.");
            return new ArrayList<>();
        }
    }

    public static List<Trainer> orderByPokemons() {

        final String SQL = "select trainer.id, trainer.name, count(pokemon.id) as pokemon_count " +
                "from trainer " +
                "left join pokemon on pokemon.trainer_id = trainer.id " +
                "group by trainer.id, trainer.name " +
                "order by pokemon_count desc";

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            ResultSet result = preparedStatement.executeQuery();

            List<Trainer> trainers = new ArrayList<>();

            while (result.next()) {
                Trainer trainer = new Trainer(result.getInt("id"), result.getString("name"));
                trainer.setPokemonsCount(result.getInt("pokemon_count"));
                trainers.add(trainer);
            }

            return trainers;

        } catch (SQLException e) {
            logger.error("Error while reading trainers: ", e);
            return new ArrayList<>();
        }
    }

    public static int create(String name) {
        final String SQL = "insert into trainer(name) values(?)";

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setString(1, name);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while creating trainer: ", e);
            return 0;
        }
    }

}
