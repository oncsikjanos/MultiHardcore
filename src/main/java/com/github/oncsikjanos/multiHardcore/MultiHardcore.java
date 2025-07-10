package com.github.oncsikjanos.multiHardcore;

import com.github.oncsikjanos.multiHardcore.sharedHp.commands.team.TeamCreateCommand;
import com.github.oncsikjanos.multiHardcore.sharedHp.database.controller.TeamController;
import com.github.oncsikjanos.multiHardcore.sharedHp.player.PlayerEventListener;
import com.github.oncsikjanos.multiHardcore.sharedHp.team.TeamHandler;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEvent;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class MultiHardcore extends JavaPlugin {

    private static TeamHandler teamHandler;
    private static TeamController teamController;

    public MultiHardcore() {
        teamHandler = new TeamHandler();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(new TeamCreateCommand(teamHandler).teamCreateCommand().build());
        });
        teamController = new TeamController(getDataFolder(), "sharedHp.db");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
