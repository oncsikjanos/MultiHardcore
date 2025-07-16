package com.github.oncsikjanos.multiHardcore.world;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.NBTFileHandle;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.codehaus.plexus.util.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.mvplugins.multiverse.core.MultiverseCoreApi;
import org.mvplugins.multiverse.core.utils.result.Attempt;
import org.mvplugins.multiverse.core.world.LoadedMultiverseWorld;
import org.mvplugins.multiverse.core.world.WorldManager;
import org.mvplugins.multiverse.core.world.options.CreateWorldOptions;
import org.mvplugins.multiverse.core.world.options.UnloadWorldOptions;
import org.mvplugins.multiverse.core.world.reasons.CreateFailureReason;

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

    public static World generateWorld() {
        String worldName;
        Date currentDate = new Date();
        String dateString = currentDate.toString().replaceAll("[: ]", "-");
        Logger logger = Bukkit.getLogger();

        worldName = WORLD_NAME.concat(dateString);

        if(Bukkit.getWorld(worldName) != null){
            logger.warning("World already exists! This should not happen!");
            return null;
        }

        WorldCreator worldCreator = new WorldCreator(worldName)
                .hardcore(true);
        World world = worldCreator.createWorld();

        world.getChunkAt(0,0);
        world.save();

        logger.log(Level.INFO, "World Created: " + worldName);

        return world;
    }

    public static void generateWorldWithMultiVerse(String worldName, Plugin plugin){
        Logger logger = Bukkit.getLogger();
        MultiverseCoreApi mvCoreApi = MultiverseCoreApi.get();

        WorldManager worldManager = mvCoreApi.getWorldManager();
        CreateWorldOptions createWorldOptions = CreateWorldOptions.worldName(worldName);

        Attempt<LoadedMultiverseWorld, CreateFailureReason> worldCreation = worldManager.createWorld(createWorldOptions);

        if(worldCreation.isSuccess()){
            World world = worldCreation.get().getBukkitWorld().get();
            File worldFolder =  world.getWorldFolder();
            logger.log(Level.INFO, "Generated world folder: " + worldFolder.getAbsolutePath());

            world.getChunkAt(0,0).load();
            world.save();

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                try{
                    File levelDat  =  new File(worldFolder, "level.dat");
                    logger.log(Level.INFO, "Loading level.dat");
                    logger.log(Level.INFO, "level.dat location: "+levelDat.getAbsolutePath());
                    logger.log(Level.INFO, "level.dat exists: "+levelDat.exists());
                    if(levelDat.exists()){
                        NBTFileHandle nbtFile = NBT.getFileHandle(levelDat);
                        ReadWriteNBT dataCompound = nbtFile.getCompound("Data");

                        if(dataCompound != null){
                            worldManager.unloadWorld(UnloadWorldOptions.world(worldCreation.get()));

                            logger.log(Level.INFO, "Hardcore before set is: "+dataCompound.getBoolean("hardcore"));
                            dataCompound.setBoolean("hardcore", true);
                            logger.log(Level.INFO, "Set hardcore to true");
                            logger.log(Level.INFO, "Hardcore is: "+dataCompound.getBoolean("hardcore"));
                            nbtFile.save();
                        }
                        else{
                            logger.log(Level.WARNING, "Could not load DATA compound from level.dat");
                        }

                        logger.log(Level.INFO, "Saved level.dat");
                        worldManager.loadWorld(worldName);
                    }

                } catch(IOException e){
                    logger.warning("Error during setting hardcore level/ saving level.dat");
                }
            }, 5L*20);
        }
    }

    public static void teleportToWorldWithMultiVerse(String worldName,
                                                     @NotNull Collection<? extends Player> players,
                                                     Plugin plugin){
        MultiverseCoreApi mvCoreApi = MultiverseCoreApi.get();
        WorldManager worldManager = mvCoreApi.getWorldManager();
        World loadedWorld  = worldManager.getLoadedWorld(worldName).get().getBukkitWorld().get();

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                players.forEach(player -> {
                    player.teleport(loadedWorld.getSpawnLocation());
                });
                }, 5L * 20);
    }

    public static void teleportToWorld(@NotNull World world, @NotNull Collection<? extends Player> players, Plugin plugin){
        players.forEach(p -> {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                p.teleport(world.getSpawnLocation());
                p.setHealth(MAX_HEALTH);
                p.setFoodLevel(MAX_FOOD);
            }, 2*20L);
        });
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

            if(!loadedWorldName.equals(newWorldName)){
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

}
