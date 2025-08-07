package com.github.oncsikjanos.multiHardcore.command;

import com.github.oncsikjanos.multiHardcore.manager.ModeManager;
import com.github.oncsikjanos.multiHardcore.message.TeamMessage;
import com.github.oncsikjanos.multiHardcore.world.HardcoreWorldGenerator;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.util.Tick;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerateCommand {
    private static Plugin ownPlugin;

    public GenerateCommand(Plugin ownPlugin){
        this.ownPlugin = ownPlugin;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> generateCommand(){
        return Commands.literal("generate").executes(GenerateCommand::generateLogic2);
    }

    private static int generateLogic(CommandContext<CommandSourceStack> ctx){
        CommandSender sender = ctx.getSource().getSender();
        Entity executor = ctx.getSource().getExecutor();
        ModeManager modeManager = ModeManager.getInstance();
        World normalWorld;
        World endWorld;
        World netherWorld;

        if(!(executor instanceof Player p)){
            sender.sendMessage(TeamMessage.ONLY_PLAYERS_CAN_USE);
            return Command.SINGLE_SUCCESS;
        }

        if(executor != sender) {
            sender.sendMessage(TeamMessage.TEAM_CREATION_ONLY_FOR_SELF);
            return Command.SINGLE_SUCCESS;
        }

        Logger logger  = Bukkit.getLogger();

        String dateString = new Date().toString().replaceAll("[: ]", "-");

        normalWorld  = HardcoreWorldGenerator.generateWorld(dateString, HardcoreWorldGenerator.WorldType.NORMAL);
        endWorld = HardcoreWorldGenerator.generateWorld(dateString, HardcoreWorldGenerator.WorldType.THE_END);
        netherWorld = HardcoreWorldGenerator.generateWorld(dateString, HardcoreWorldGenerator.WorldType.NETHER);

        if(normalWorld != null){
            modeManager.setNormal(normalWorld);
            HardcoreWorldGenerator.teleportToWorld(normalWorld, Bukkit.getOnlinePlayers(), ownPlugin);
            HardcoreWorldGenerator.removeUnusedWorlds2(normalWorld.getName());
            logger.log(Level.INFO, "Hardcore World Generated: " + normalWorld.getName());
        }

        if(endWorld != null){
            modeManager.setEnd(endWorld);
            logger.log(Level.INFO, "Hardcore End World Generated: " + endWorld.getName());
        }

        if(netherWorld != null){
            modeManager.setNether(netherWorld);
            logger.log(Level.INFO, "Hardcore Nether World Generated: " + netherWorld.getName());
        }


        Bukkit.getWorlds().forEach(world -> {
            logger.log(Level.INFO, "World: " + world.getName());
        });

        return Command.SINGLE_SUCCESS;
    }

    private static int generateLogic2(CommandContext<CommandSourceStack> ctx){
        CommandSender sender = ctx.getSource().getSender();
        Entity executor = ctx.getSource().getExecutor();
        ModeManager modeManager = ModeManager.getInstance();

        if (!(executor instanceof Player p)) {
            sender.sendMessage(TeamMessage.ONLY_PLAYERS_CAN_USE);
            return Command.SINGLE_SUCCESS;
        }

        if (executor != sender) {
            sender.sendMessage(TeamMessage.TEAM_CREATION_ONLY_FOR_SELF);
            return Command.SINGLE_SUCCESS;
        }

        Logger logger = Bukkit.getLogger();
        String dateString = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String baseName = "custom";

        Map<HardcoreWorldGenerator.WorldType, World> worlds = HardcoreWorldGenerator.generateLinkedWorlds(baseName);

        World overworld = worlds.get(HardcoreWorldGenerator.WorldType.NORMAL);
        World nether = worlds.get(HardcoreWorldGenerator.WorldType.NETHER);
        World theEnd = worlds.get(HardcoreWorldGenerator.WorldType.THE_END);

        if (overworld != null) {
            modeManager.setNormal(overworld);
            HardcoreWorldGenerator.teleportToWorld(overworld, Bukkit.getOnlinePlayers(), ownPlugin);
            HardcoreWorldGenerator.removeUnusedWorlds(overworld.getName());
            logger.log(Level.INFO, "Hardcore World Generated: " + overworld.getName());
        }

        if (nether != null) {
            modeManager.setNether(nether);
            logger.log(Level.INFO, "Hardcore Nether World Generated: " + nether.getName());
        }

        if (theEnd != null) {
            modeManager.setEnd(theEnd);
            logger.log(Level.INFO, "Hardcore End World Generated: " + theEnd.getName());
        }

        Bukkit.getWorlds().forEach(world -> {
            logger.log(Level.INFO, "World: " + world.getName());
        });

        return Command.SINGLE_SUCCESS;
    }
}
