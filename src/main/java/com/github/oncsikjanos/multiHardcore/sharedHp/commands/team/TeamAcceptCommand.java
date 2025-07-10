package com.github.oncsikjanos.multiHardcore.sharedHp.commands.team;

import com.github.oncsikjanos.multiHardcore.sharedHp.commands.error.ErrorCodes;
import com.github.oncsikjanos.multiHardcore.sharedHp.message.TeamMessage;
import com.github.oncsikjanos.multiHardcore.sharedHp.team.TeamHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class TeamAcceptCommand extends TeamCommand {

    public TeamAcceptCommand(TeamHandler teamHandler) {
        super(teamHandler);
    }

    public static LiteralArgumentBuilder<CommandSourceStack> teamJoinCommand() {
        /return Commands.literal("team").then(
                Commands.literal("join").then(Commands.argument("player_name")
                        .executes(TeamAcceptCommand::teamJoinLogic)));*/
    }

    private static int teamJoinLogic(CommandContext<CommandSourceStack> ctx){
        CommandSender sender = ctx.getSource().getSender();
        Entity executor = ctx.getSource().getExecutor();

        if(!(executor instanceof Player p)){
            sender.sendMessage(TeamMessage.ONLY_PLAYERS_CAN_USE);
            return Command.SINGLE_SUCCESS;
        }

        if(executor != sender){
            sender.sendMessage(TeamMessage.TEAM_CREATION_ONLY_FOR_SELF);
            return Command.SINGLE_SUCCESS;
        }

        int code = teamHandler.createTeam(p, sender.getName()+"_team");

        switch(code)
        {
            case ErrorCodes.OK:
                sender.sendMessage(TeamMessage.TEAM_CREATED_SUCCESFULLY);
                break;
            case ErrorCodes.TEAMNAME_ALREADY_EXISTS:
                sender.sendMessage(TeamMessage.TEAM_ALREADY_EXISTS);
                break;
            case ErrorCodes.PLAYER_ALREADY_IN_TEAM:
                sender.sendMessage(TeamMessage.TEAM_ALREADY_JOINED);
                break;
            default:
                sender.sendMessage(TeamMessage.UNKNOWN_ERROR);
                break;
        }

        return Command.SINGLE_SUCCESS;
    }

}
