package com.github.oncsikjanos.multiHardcore.manager;

import org.bukkit.entity.Player;

import java.util.Collection;

public class ModeManager {
    private static volatile ModeManager instance;

    private final Collection<? extends Player> serverPlayerList;
    private Player healerPlayer;
    private static MessageManager messageHandler;

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
        this.messageHandler = new MessageManager();
        //this.healerPlayer = null;
    }

    public void playerTookDamage(String damagedPlayerName, String damagerName, double dmgAmount){
         serverPlayerList.forEach(player -> {
             player.damage(dmgAmount);
             messageHandler.sendDMGMessageToPlayers(player, damagedPlayerName, damagerName, dmgAmount);
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
    public void playerDeath(){
        serverPlayerList.forEach(p -> {
            p.setHealth(0);
        });
    }

    /*TODO: Have to check if it's needed ingame*/
    public void playerHealing(Player player){
        if(checkHealerPlayerEqual(player)){}
    }

    private boolean checkHealerPlayerEqual(Player player){
        return this.healerPlayer != null && this.healerPlayer.getUniqueId().equals(player.getUniqueId());
    }

}
