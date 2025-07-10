package com.github.oncsikjanos.multiHardcore.sharedHp.player;

import com.github.oncsikjanos.multiHardcore.sharedHp.team.TeamHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEventListener implements Listener {
    private final TeamHandler teamHandler;

    public PlayerEventListener(){
        this.teamHandler = new TeamHandler();
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player p){
            /*Signal to teammates to take dmg too*/
            //teamHandler.playerTookDamage(p.getName(), e.getDamage());
            /*Write to chat who took dmg, how many, from what, only write it to teammates*/
        }

    }

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent e) {
        if(e.getEntity() instanceof Player){
            /*Signal to teammates to take change hunger too*/
        }
    }

    @EventHandler
    public void onPlayerHealthRegen(EntityRegainHealthEvent e){
        if(e.getEntity() instanceof Player){
            /*Signal teammates to regen health too*/
        }

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        if(e.getEntity() instanceof Player){
            /*Signal teammates to die too*/
            /* Write to teammates some information example:
             what should they write to console to go back to island*/
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent e){

    }
}
