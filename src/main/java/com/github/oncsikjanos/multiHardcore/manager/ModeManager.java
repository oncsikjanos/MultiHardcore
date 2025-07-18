package com.github.oncsikjanos.multiHardcore.manager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.Collection;

public class ModeManager {
    private static volatile ModeManager instance;

    private final Collection<? extends Player> serverPlayerList;
    private Player healerPlayer;
    private World normal;
    private World nether;
    private World end;

    public static ModeManager getInstance() {
        if (instance == null) {
            synchronized (ModeManager.class) {
                if (instance == null) {
                    instance = new ModeManager(Bukkit.getOnlinePlayers());
                }
            }
        }
        return instance;
    }

    private ModeManager(Collection<? extends Player> serverPlayerList){
        this.serverPlayerList = serverPlayerList;
        this.healerPlayer = null;
        this.normal = null;
        this.nether = null;
        this.end = null;
    }

    public World getNormal() {
        return normal;
    }

    public void setNormal(World normal) {
        this.normal = normal;
    }

    public World getNether() {
        return nether;
    }

    public void setNether(World nether) {
        this.nether = nether;
    }

    public World getEnd() {
        return end;
    }

    public void setEnd(World end) {
        this.end = end;
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

    public void playerTookDamageFromWorld(){

    }

    public void netherPortalEventHandler(Player player){
        event.useTravelAgent(true);
        event.getPortalTravelAgent().setCanCreatePortal(true);
        Location location;
        if (player.getWorld() == normal) {
            location = new Location(getNether(), event.getFrom().getBlockX() / 8, event.getFrom().getBlockY(), event.getFrom().getBlockZ() / 8);
        } else {
            location = new Location(getWorld(), event.getFrom().getBlockX() * 8, event.getFrom().getBlockY(), event.getFrom().getBlockZ() * 8);
        }
        event.setTo(event.getPortalTravelAgent().findOrCreate(location));
    }

    public void endPortalEventHandler(Player player){
        if (player.getWorld() == getWorld()) {
            Location loc = new Location(getEnd(), 100, 50, 0); // This is the vanilla location for obsidian platform.
            event.setTo(loc);
            Block block = loc.getBlock();
            for (int x = block.getX() - 2; x <= block.getX() + 2; x++) {
                for (int z = block.getZ() - 2; z <= block.getZ() + 2; z++) {
                    Block platformBlock = loc.getWorld().getBlockAt(x, block.getY() - 1, z);
                    if (platformBlock.getType() != Material.OBSIDIAN) {
                        platformBlock.setType(Material.OBSIDIAN);
                    }
                    for (int yMod = 1; yMod <= 3; yMod++) {
                        Block b = platformBlock.getRelative(BlockFace.UP, yMod);
                        if (b.getType() != Material.AIR) {
                            b.setType(Material.AIR);
                        }
                    }
                }
            }
        } else if (player.getWorld() == getEnd()) {
            event.setTo(getWorld().getSpawnLocation());
        }
    }

    private boolean checkHealerPlayerEqual(Player player){
        return this.healerPlayer != null && this.healerPlayer.getUniqueId().equals(player.getUniqueId());
    }

}
