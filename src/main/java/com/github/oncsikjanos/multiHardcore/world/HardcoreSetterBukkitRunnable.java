package com.github.oncsikjanos.multiHardcore.world;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.NBTFileHandle;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HardcoreSetterBukkitRunnable extends BukkitRunnable {
    private File worldFolder;
    private Logger logger;


    public HardcoreSetterBukkitRunnable(File worldFolder) {
        logger = Bukkit.getLogger();
        this.worldFolder = worldFolder;
    }

    @Override
    public void run() {
        try{
            File levelDat  =  new File(worldFolder, "level.dat");
            logger.log(Level.INFO, "Loading level.dat");
            logger.log(Level.INFO, "level.dat location: "+levelDat.getAbsolutePath());
            logger.log(Level.INFO, "level.dat exists: "+levelDat.exists());
            if(levelDat.exists()){
                NBTFileHandle nbtFile = NBT.getFileHandle(levelDat);

                nbtFile.setBoolean("hardcore", true);
                nbtFile.save();

                logger.log(Level.INFO, "Saved level.dat");
                this.cancel();
            }

        } catch(IOException e){
            logger.warning("Error during setting hardcore level/ saving level.dat");
        }
    }
}
