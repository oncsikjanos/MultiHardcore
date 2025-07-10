package com.github.oncsikjanos.multiHardcore.sharedHp.team;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Player> players;
    private Player creator;

    public Team(Player creator){
        this.creator = creator;
        this.players = new ArrayList<>();
    }

    public void damagePlayersInTeam(String excludedPlayerName, double dmgAmount){
        for(Player p : players){
            if(!p.getName().equals(excludedPlayerName)){
                p.damage(dmgAmount);
            }
        }
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player){
        this.players.remove(player);
    }
}

