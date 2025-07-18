package com.github.oncsikjanos.multiHardcore.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.codehaus.plexus.util.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HardcoreWorldGenerator {
    private static final int MAX_HEALTH = 20;
    private static final int MAX_FOOD = 20;
    private static final String WORLD_NAME = "hardcore_world";

    public static World generateWorld(WorldType worldType) {
        String worldName;
        World world;
        WorldCreator worldCreator;
        Date currentDate = new Date();
        String dateString = currentDate.toString().replaceAll("[: ]", "-");
        Logger logger = Bukkit.getLogger();

        worldName = WORLD_NAME.concat(dateString);

        if(Bukkit.getWorld(worldName) != null){
            logger.severe("World already exists! This should not happen!");
            return null;
        }

        switch (worldType) {
            case NORMAL:
                worldCreator = new WorldCreator(worldName).hardcore(true);
                logger.log(Level.INFO, "Normal world created: " + worldName);
                break;

            case NETHER:
                worldCreator = new WorldCreator(worldName.concat("_the_end")).hardcore(true);
                worldCreator.environment(World.Environment.THE_END);
                logger.log(Level.INFO, "The end world created: " + worldName);
                break;

            case THE_END:
                worldCreator = new WorldCreator(worldName.concat("_nether")).hardcore(true);
                worldCreator.environment(World.Environment.NETHER);
                logger.log(Level.INFO, "Nether world created: " + worldName);
                break;
            default:
                logger.severe("Invalid world type! Should not happen!");
                return null;

        }

        world = worldCreator.createWorld();
        world.getChunkAt(0,0);
        world.save();

        return world;
    }

    public static void teleportToWorld(@NotNull World world, @NotNull Collection<? extends Player> players,
                                       Plugin plugin){
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                players.forEach(p -> {
                    p.teleport(world.getSpawnLocation());
                    p.setHealth(MAX_HEALTH);
                    p.setFoodLevel(MAX_FOOD);
                    p.getInventory().clear();
                });
            }, 2*20L);
    }

    public static World getWorld(WorldUnloaderRunnable.WorldType worldType){
        World defaultWorld = Bukkit.getWorlds().stream().filter(w -> !w.isHardcore())
                .findFirst().orElse(null);

        if(defaultWorld == null){
            WorldCreator worldCreator = new WorldCreator("world");
            defaultWorld = worldCreator.createWorld();
        }

        return defaultWorld;
    }

    public static void removeUnusedWorlds(String newWorldName){
        Logger logger = Bukkit.getLogger();

        Bukkit.getWorlds().forEach(world -> {
            String loadedWorldName = world.getName();
            logger.log(Level.INFO, "CHECK IF REMOVEABLE: " + loadedWorldName);

            if(!loadedWorldName.contains(newWorldName)){
                if(!Bukkit.isTickingWorlds()){
                    File previousWorld = Bukkit.getWorld(loadedWorldName).getWorldFolder();

                    Bukkit.unloadWorld(world, false);

                    if(loadedWorldName.contains(WORLD_NAME)) {
                        try{
                            FileUtils.deleteDirectory(previousWorld);
                            logger.log(Level.INFO, "Removed unused world: " + world.getName());

                        }
                        catch(IOException e){
                            Bukkit.getLogger().warning("Cant delete previous world's directory");
                        }
                    }
                }
            }
        });
    }

    public enum WorldType {
        NORMAL,
        NETHER,
        THE_END
    }

}
