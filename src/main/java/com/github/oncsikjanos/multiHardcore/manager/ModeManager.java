package com.github.oncsikjanos.multiHardcore.manager;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.Collection;

public class ModeManager {
    private static volatile ModeManager instance;

    private final Collection<? extends Player> serverPlayerList;
    private Player healerPlayer;

    public static ModeManager getInstance(Collection<? extends Player> serverPlayerList) {
        if (instance == null) {
            synchronized (ModeManager.class) {
                if (instance == null) {
                    instance = new ModeManager(serverPlayerList);
                }
            }
        }
        return instance;
    }

    private ModeManager(Collection<? extends Player> serverPlayerList){
        this.serverPlayerList = serverPlayerList;
        //this.healerPlayer = null;
    }

    public void playerTookDamage(String damagedPlayerName, String damagerName, double dmgAmount){
         serverPlayerList.forEach(player -> {
             if(player.getName().equals(damagedPlayerName)){
                 player.damage(dmgAmount);
             }

             MessageManager.sendDMGMessageToPlayers(player, damagedPlayerName, damagerName, dmgAmount);
         });
    }

    public void playerJoinedTheGame(Player player){
        if(this.healerPlayer == null){
            this.healerPlayer = player;
        }
    }

    public void playerLeftTheGame(Player player){
        if(checkHealerPlayerEqual(player)){
            this.healerPlayer = null;
        }
    }

    public void playerHungerChanged(Player player){
        if(checkHealerPlayerEqual(player)){
            serverPlayerList.forEach(p -> {
                p.setFoodLevel(player.getFoodLevel());
            });
        }
    }

    /* TODO: Maybe not needed because of basic DMG mechanism*/
    public void playerDeath(String deadPlayerName){
        serverPlayerList.forEach(p -> {
            if(!p.getName().equals(deadPlayerName) && p.getHealth() > 0) {
                p.setHealth(0);
            }
        });
    }

    /*TODO: Have to check if it's needed ingame*/
    public void playerHealing(Player player, EntityRegainHealthEvent.RegainReason regainReason, double healthAmount){
        if(regainReason == EntityRegainHealthEvent.RegainReason.SATIATED) {
            if(checkHealerPlayerEqual(player)){
                serverPlayerList.forEach(p -> {
                    if(!checkHealerPlayerEqual(p)){
                        p.setHealth(player.getHealth());
                    }
                });
            }
            else{
                player.setHealth(player.getHealth() - healthAmount);
            }
        }
        else{
            serverPlayerList.forEach(serverPlayer -> {
                if(!serverPlayer.getUniqueId().equals(player.getUniqueId())){
                    if(serverPlayer.getHealth() > player.getHealth()){
                        player.setHealth(serverPlayer.getHealth());
                    }
                    else{
                        serverPlayer.setHealth(player.getHealth());
                    }

                }
            });
        }
    }

    private boolean checkHealerPlayerEqual(Player player){
        return this.healerPlayer != null && this.healerPlayer.getUniqueId().equals(player.getUniqueId());
    }

}
