package com.github.oncsikjanos.multiHardcore.sharedHp.database.controller;

import com.github.oncsikjanos.multiHardcore.sharedHp.database.dao.PlayerDAO;
import com.github.oncsikjanos.multiHardcore.sharedHp.database.dao.PlayerDAOImpl;
import com.github.oncsikjanos.multiHardcore.sharedHp.database.dao.TeamDAO;
import com.github.oncsikjanos.multiHardcore.sharedHp.database.dao.TeamDaoImpl;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TeamController {
    private Connection connection;
    private final TeamDAO teamDAO;
    private final PlayerDAO playerDAO;

    public TeamController(File dataFolder, String databaseName){
        connectToDatabase(dataFolder, databaseName);
        teamDAO = new TeamDaoImpl(connection);
        playerDAO = new PlayerDAOImpl(connection);
    }

    /**
     * Handles connecting to database on plugin init
     * @param dataFolder -> location of the database
     * @param databaseName -> name of the database (xy.db)
     */
    private void connectToDatabase(File dataFolder, String databaseName){
        try{
            File databaseFile = new File(dataFolder, databaseName);
            databaseFile.getParentFile().mkdirs();
            boolean databaseExists = databaseFile.exists();

            connection = DriverManager.getConnection("jdbc:sqlite:"+databaseFile.getAbsolutePath());

            if(!databaseExists){
                createTeamTable();
                createPlayerTable();
            }
        }
        catch (SQLException e){
            System.err.println("Error in connecting to database");
            System.err.println(e.getMessage());
        }
    }


    /**
     * ONLY USED ON FIRST PLUGIN INIT
     * Creates team table with the following attributes:
     * creator: varchar(255) -> player's name who created the team
     * hp: int -> contains team current hp level
     * hunger: int -> contains team current hunger level
     * try_count :int -> contains teams run count (deaths+1)
     * run_time: int -> contains current run time
     * on_game: boolean -> contains if the team is already on a game
     */
    private void createTeamTable(){
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS Team (id int PRIMARY KEY UNIQUE NOT NULL," +
                    "creator varchar(255) UNIQUE NOT NULL, " +
                    "hp int, " +
                    "hunger int, " +
                    "try_count int NOT NULL, " +
                    "run_time int," +
                    "on_game int NOT NULL CHECK(onGame IN (0,1))) ");
        }
        catch (SQLException e){
            System.err.println("Error in creating team table");
            System.err.println(e.getMessage());
        }
    }


    /**
     * ONLY USED ON FIRST PLUGIN INIT
     * Creates player table with the following attributes:
     * name: varchar(255) -> player's name
     * teamId: FOREIGN KEY -> player's joined team ID
     */
    private void createPlayerTable(){
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS Player (teamId int NOT NULL, " +
                    "name varchar(255) UNIQUE, " +
                    "FOREIGN KEY (teamId) REFERENCES Team(teamId))");

        }
        catch (SQLException e){
            System.err.println("Error in creating player table");
            System.err.println(e.getMessage());
        }
    }


    /**
     *
     * @param playerName
     * @return
     */
    public boolean teamCreation(String playerName){
        int createdTeamID = teamDAO.add(playerName);
        int playerAdded;

        if(createdTeamID == -1){
            System.err.println("Could not add team");
            return false;
        }
        playerAdded = playerDAO.add(createdTeamID, playerName);

        if(playerAdded < 1){
            if(playerAdded != -1){
                System.err.println("Unknown error while adding player");
            }
            return false;
        }

        return true;
    }

}
