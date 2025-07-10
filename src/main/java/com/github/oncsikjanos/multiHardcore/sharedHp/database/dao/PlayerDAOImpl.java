package com.github.oncsikjanos.multiHardcore.sharedHp.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerDAOImpl implements PlayerDAO {
    Connection connection;

    public PlayerDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String get() {
        return "";
    }

    @Override
    public int add(int teamId, String playerName) {
        String query = "INSERT INTO Player (teamId, name) VALUES (?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, teamId);
            statement.setString(2, playerName);
            return statement.executeUpdate();
        }
        catch(SQLException e) {
            System.err.println("Error during adding player to database");
            System.err.println(e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean remove(String player) {
        String query = "DELETE FROM Player WHERE name = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, player);
        }
        catch(SQLException e) {
            System.err.println("Error during removing player from database");
            System.err.println(e.getMessage());
            return false;
        }
        catch (Exception e) {
            System.err.println("Unknown error during removing player from database");
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
}
