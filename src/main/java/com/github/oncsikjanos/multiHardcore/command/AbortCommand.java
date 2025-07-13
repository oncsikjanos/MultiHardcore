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

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

public class AbortCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> testComamnd(){
        return Commands.literal("test").executes(AbortCommand::testLogic);
    }

    private static int testLogic(CommandContext<CommandSourceStack> ctx){
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

        Bukkit.getWorlds().forEach(world -> {
            Bukkit.getLogger().log(Level.INFO, "World: " + world.getName());
        });

        return Command.SINGLE_SUCCESS;
    }
}
