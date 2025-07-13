package com.github.oncsikjanos.multiHardcore.player;

import com.github.oncsikjanos.multiHardcore.manager.ModeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;

public class PlayerEventListener implements Listener {
    private final ModeManager modeManager;

    public PlayerEventListener(Collection<? extends Player> serverPlayerList){
        this.modeManager = ModeManager.getInstance(serverPlayerList);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player p){
            String damagerName = e.getDamager().getName();
            String damagedPlayerName = p.getName();
            double dmg = e.getDamage();

            modeManager.playerTookDamage(damagedPlayerName, damagerName, dmg);
        }

    }

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent e) {
        if(e.getEntity() instanceof Player player){
            modeManager.playerHungerChanged(player);
        }
    }


    /*TODO: Have to check if it's needed ingame*/
    @EventHandler
    public void onPlayerHealthRegen(EntityRegainHealthEvent e){
        if(e.getEntity() instanceof Player player){
            if(e.getRegainReason() != EntityRegainHealthEvent.RegainReason.SATIATED)
            {
                modeManager.playerHealing(player, e.getRegainReason(), e.getAmount());
            }
        }

    }

    /* TODO: Maybe not needed because of basic DMG mechanism*/
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        modeManager.playerDeath(e.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent e){
        modeManager.playerLeftTheGame(e.getPlayer());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        modeManager.playerJoinedTheGame(e.getPlayer());
    }

}
