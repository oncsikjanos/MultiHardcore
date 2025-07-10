package com.github.oncsikjanos.multiHardcore.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.codehaus.plexus.util.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class HardcoreWorldGenerator {

    public World generateWorld(){
        String worldName = "hardcore_world";

        if(Bukkit.getWorld(worldName) != null){
            File previousWorld = Bukkit.getWorld(worldName).getWorldFolder();
            try{
                FileUtils.deleteDirectory(previousWorld);
            }
            catch(IOException e){
                Bukkit.getLogger().warning("Cant delete previous world's directory");
            }
        }

        WorldCreator worldCreator = new WorldCreator(worldName)
                .hardcore(true);
        return worldCreator.createWorld();
    }

    public void teleportToWorld(@NotNull World world, @NotNull Player player){
        player.teleport(world.getSpawnLocation());
    }

    public void teleportToWorld(@NotNull World world, @NotNull Collection<? extends Player> players){
        players.forEach(p -> {
            p.teleport(world.getSpawnLocation());
        });
    }

    public World getDefaultWorld(){
        World defaultWorld = Bukkit.getWorlds().stream().filter(w -> !w.isHardcore())
                .findFirst().orElse(null);

        if(defaultWorld == null){
            WorldCreator worldCreator = new WorldCreator("world");
            defaultWorld = worldCreator.createWorld();
        }

        return defaultWorld;
    }

}
