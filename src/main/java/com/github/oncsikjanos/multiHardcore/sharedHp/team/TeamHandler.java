package com.github.oncsikjanos.multiHardcore.sharedHp.team;

import com.github.oncsikjanos.multiHardcore.sharedHp.commands.error.ErrorCodes;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TeamHandler {
    private Map<String, String> players;
    private Map<String, Team> teams;

    public TeamHandler(){
        this.players = new HashMap<>();
        this.teams = new HashMap<>();
    }

    public void playerJoinsTeam(String playerName, String teamName){
        this.players.put(playerName, teamName);
    }

    public void playerLeavesTeam(String playerName){
        this.players.remove(playerName);
    }

    public void playerTookDamage(String playerName, double dmgAmount){
        String teamName = players.get(playerName);
        Team team = teams.get(teamName);
        team.damagePlayersInTeam(playerName, dmgAmount);
    }

    public int createTeam(Player creator, String teamName){
        if(players.containsKey(creator.getName())){
            return ErrorCodes.PLAYER_ALREADY_IN_TEAM;
        }
        if(teams.containsKey(teamName)){
            return ErrorCodes.TEAMNAME_ALREADY_EXISTS;
        }

        players.put(creator.getName(), teamName);
        Team createdTeam = new Team(creator);
        teams.put(teamName, createdTeam);

        return ErrorCodes.OK;
    }

    public void disbandTeam(String creatorName){
        /*TODO*/
    }
}
