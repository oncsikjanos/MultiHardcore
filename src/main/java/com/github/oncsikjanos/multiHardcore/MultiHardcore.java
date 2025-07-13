package com.github.oncsikjanos.multiHardcore;


import com.github.oncsikjanos.multiHardcore.command.AbortCommand;
import com.github.oncsikjanos.multiHardcore.command.GenerateCommand;
import com.github.oncsikjanos.multiHardcore.player.PlayerEventListener;
import com.github.oncsikjanos.multiHardcore.manager.ModeManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class MultiHardcore extends JavaPlugin {

    private static ModeManager modeManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        //this.modeManager = ModeManager.getInstance(getServer().getOnlinePlayers());
        getServer().getPluginManager().registerEvents(new PlayerEventListener(getServer().getOnlinePlayers()), this);
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(new GenerateCommand(this).generateCommand().build());
            commands.registrar().register(new AbortCommand().testComamnd().build());
        });
        //getServer().c
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
