package com.github.oncsikjanos.multiHardcore.player;

import com.github.oncsikjanos.multiHardcore.manager.ModeManager;
import org.bukkit.Bukkit;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Collection;
import java.util.logging.Logger;

public class PlayerEventListener implements Listener {
    private final ModeManager modeManager;

    public PlayerEventListener(){
        this.modeManager = ModeManager.getInstance();
    }

    @EventHandler
    public void onPlayerDamageFromEntity(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player p){
            String damagerName = e.getDamager().getName();
            String damagedPlayerName = p.getName();
            double dmg = e.getDamage();

            if(e.getDamager() instanceof Arrow arrow){
                 if(arrow.getShooter() instanceof LivingEntity source){
                     damagerName = source.getName();
                 }
            }

            modeManager.playerTookDamage(damagedPlayerName, damagerName, dmg);
        }

    }

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent e) {
        if(e.getEntity() instanceof Player player){
            modeManager.playerHungerChanged(player);
        }
    }

    @EventHandler
    public void onOtherDamage(EntityDamageEvent e) {
        if(e.getDamageSource().getDamageType() ==DamageType.GENERIC){
            Bukkit.getLogger().warning(e.getCause().toString());
            Bukkit.getLogger().warning("Generic cause: "+e.getDamageSource().toString());
        }
        else if(e.getEntity() instanceof Player p &&  e.getDamageSource().getCausingEntity() == null){
            Bukkit.getLogger().warning("Normal cause: " + e.getCause().toString());
            Bukkit.getLogger().warning(e.getDamageSource().toString());
            modeManager.playerTookDamageFromWorld(p.getName(),
                    e.getDamage(),
                    e.getDamageSource().getDamageType(),
                    e.getCause());
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

    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        Bukkit.getLogger().info("PlayerPortalEvent triggered");
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            //modeManager.netherPortalEventHandler(player);
        }

        if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            //modeManager.endPortalEventHandler(player);
        }
    }

    @EventHandler
    public void test(PlayerTeleportEvent e){
        if(e.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL){
            modeManager.endPortalEventHandler(player);
        }
        else if (e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL){
            modeManager.netherPortalEventHandler(player);
        }

        Logger logger = Bukkit.getLogger();
        logger.info("PlayerTeleportEvent triggered");
        logger.info("Cause: " + e.getCause().toString());
        logger.info("Eventname" + e.getEventName());
        logger.info("Full description: " + e.toString());
    }
}
