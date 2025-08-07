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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HardcoreWorldGenerator {
    private static final int MAX_HEALTH = 20;
    private static final int MAX_FOOD = 20;
    private static final String WORLD_NAME = "hardcore_world";

    public static World generateWorld(String dateString, WorldType worldType) {
        String worldName;
        World world;
        WorldCreator worldCreator;
        //Date currentDate = new Date();
        //String dateString = new Date().currentDate.toString().replaceAll("[: ]", "-");
        Logger logger = Bukkit.getLogger();

        worldName = WORLD_NAME.concat(dateString);

        if(Bukkit.getWorld(worldName) != null && worldType == WorldType.NORMAL){
            logger.severe("World already exists! This should not happen!");
            return null;
        }

        switch (worldType) {
            case NORMAL:
                worldCreator = new WorldCreator(worldName).hardcore(true);
                logger.log(Level.INFO, "Normal world created: " + worldName);
                break;

            case NETHER:
                worldName = worldName.concat("_nether");
                worldCreator = new WorldCreator(worldName).hardcore(true);
                worldCreator.environment(World.Environment.NETHER);
                logger.log(Level.INFO, "Nether world created: " + worldName);
                break;

            case THE_END:
                worldName = worldName.concat("_the_end");
                worldCreator = new WorldCreator(worldName).hardcore(true);
                worldCreator.environment(World.Environment.THE_END);
                logger.log(Level.INFO, "The_end world created: " + worldName);
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

    public static Map<WorldType, World> generateLinkedWorlds(String baseName) {
        Logger logger = Bukkit.getLogger();
        Map<WorldType, World> result = new HashMap<>();

        // Overworld
        World overworld = new WorldCreator(baseName)
                .environment(World.Environment.NORMAL)
                .hardcore(true)
                .createWorld();
        result.put(WorldType.NORMAL, overworld);
        logger.info("Overworld created: " + overworld.getName());

        // Nether
        World nether = new WorldCreator(baseName + "_nether")
                .environment(World.Environment.NETHER)
                .hardcore(true)
                .createWorld();
        result.put(WorldType.NETHER, nether);
        logger.info("Nether created: " + nether.getName());

        // The End
        World theEnd = new WorldCreator(baseName + "_the_end")
                .environment(World.Environment.THE_END)
                .hardcore(true)
                .createWorld();
        result.put(WorldType.THE_END, theEnd);
        logger.info("End created: " + theEnd.getName());

        return result;
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
                if(true){
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

    public static void removeUnusedWorlds2(String newWorldNamePrefix) {
        Logger logger = Bukkit.getLogger();
        List<World> worldsToRemove = new ArrayList<>();

        for (World world : Bukkit.getWorlds()) {
            String loadedWorldName = world.getName();
            logger.info("Checking if removable: " + loadedWorldName);

            // Keep the new world and its _nether / _the_end versions
            if (loadedWorldName.startsWith(newWorldNamePrefix)) continue;

            // Only remove worlds that are prefixed with your plugin's WORLD_NAME
            if (loadedWorldName.startsWith(HardcoreWorldGenerator.WORLD_NAME)) {
                worldsToRemove.add(world);
            }
        }

        for (World world : worldsToRemove) {
            String name = world.getName();

            // Make sure all players are moved off the world
            for (Player p : world.getPlayers()) {
                p.teleport(Bukkit.getWorld(newWorldNamePrefix).getSpawnLocation());
            }

            // Try unloading
            boolean unloaded = Bukkit.unloadWorld(world, false);
            if (unloaded) {
                File worldFolder = world.getWorldFolder();
                try {
                    FileUtils.deleteDirectory(worldFolder);
                    logger.info("Successfully removed unused world: " + name);
                } catch (IOException e) {
                    logger.warning("Failed to delete world folder: " + name);
                    e.printStackTrace();
                }
            } else {
                logger.warning("Could not unload world: " + name);
            }
        }
    }

    public enum WorldType {
        NORMAL,
        NETHER,
        THE_END
    }

}
