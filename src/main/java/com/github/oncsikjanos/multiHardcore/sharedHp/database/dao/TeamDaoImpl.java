package com.github.oncsikjanos.multiHardcore.sharedHp.database.dao;

import com.github.oncsikjanos.multiHardcore.sharedHp.team.Team;

import java.sql.*;

public class TeamDaoImpl implements TeamDAO{
    Connection connection;

    public TeamDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Team get() {
        return null;
    }

    @Override
    public int add(String creatorName) {
        String query = "INSERT INTO Team (creator, tryCount, onGame) VALUES (?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, creatorName);
            statement.setInt(2, 0);
            statement.setInt(3, 0);
            statement.execute(query);

            try(ResultSet resultSet = statement.getGeneratedKeys()){
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while inserting team to database");
            System.err.println(e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean remove(Team team) {
        return false;
    }

    @Override
    public boolean update(Team team) {
        return false;
    }
}
