package com.github.oncsikjanos.multiHardcore.command;

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

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerateCommand {
    private static Plugin ownPlugin;

    public GenerateCommand(Plugin ownPlugin){
        this.ownPlugin = ownPlugin;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> generateCommand(){
        return Commands.literal("generate").executes(GenerateCommand::generateLogic);
    }

    private static int generateLogic(CommandContext<CommandSourceStack> ctx){
        CommandSender sender = ctx.getSource().getSender();
        Entity executor = ctx.getSource().getExecutor();

        if(!(executor instanceof Player p)){
            sender.sendMessage(TeamMessage.ONLY_PLAYERS_CAN_USE);
            return Command.SINGLE_SUCCESS;
        }

        if(executor != sender) {
            sender.sendMessage(TeamMessage.TEAM_CREATION_ONLY_FOR_SELF);
            return Command.SINGLE_SUCCESS;
        }

        Logger logger  = Bukkit.getLogger();

        String worldName = "hardcore_world_mv";

        /*HardcoreWorldGenerator.generateWorldWithMultiVerse(worldName, ownPlugin);*/
        World generatedWorld  = HardcoreWorldGenerator.generateWorld();
        /*HardcoreWorldGenerator.teleportToWorldWithMultiVerse(worldName, Bukkit.getOnlinePlayers(), ownPlugin);*/
        if(generatedWorld != null){
            HardcoreWorldGenerator.teleportToWorld(generatedWorld, Bukkit.getOnlinePlayers(), ownPlugin);
            HardcoreWorldGenerator.removeUnusedWorlds(generatedWorld.getName());
        }

        logger.log(Level.INFO, "Hardcore World Generated: " + worldName);


        Bukkit.getWorlds().forEach(world -> {
            logger.log(Level.INFO, "World: " + world.getName());
        });

        return Command.SINGLE_SUCCESS;
    }
}
