package com.github.oncsikjanos.multiHardcore.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldUnloaderRunnable extends BukkitRunnable {
    private WorldType worldType;

    public WorldUnloaderRunnable(WorldType worldType){
        this.worldType = worldType;
    }

    @Override
    public void run() {
        World worldToUnload;
        boolean saveWorld;

        if(WorldType.HARDCORE.equals(worldType)){
            //worldToUnload = HardcoreWorldGenerator

        } else if (WorldType.DEFAULT.equals(worldType)) {
            worldToUnload =  HardcoreWorldGenerator.getWorld(WorldType.DEFAULT);
            saveWorld = true;
        }


        //Bukkit.unloadWorld(worldToUnload, saveWorld);
    }

    public  enum WorldType {
        DEFAULT,
        HARDCORE,
    }

}
