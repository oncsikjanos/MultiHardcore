package com.github.oncsikjanos.multiHardcore;


import com.github.oncsikjanos.multiHardcore.player.PlayerEventListener;
import com.github.oncsikjanos.multiHardcore.manager.ModeManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MultiHardcore extends JavaPlugin {

    private static ModeManager modeManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        //this.modeManager = ModeManager.getInstance(getServer().getOnlinePlayers());
        getServer().getPluginManager().registerEvents(new PlayerEventListener(getServer().getOnlinePlayers()), this);
        //getServer().c
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
