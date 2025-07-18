package com.github.oncsikjanos.multiHardcore.message;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.WorldBorder;

public final class TeamMessage extends Message{
        public static final TextComponent ONLY_PLAYERS_CAN_USE = Message.MOD_NAME_PREFIX
                .append(Component.text("Only players can create team!", NamedTextColor.RED));
        public static final TextComponent TEAM_CREATION_ONLY_FOR_SELF = Message.MOD_NAME_PREFIX
                .append(Component.text("You can only create team for yourself!", NamedTextColor.RED));
        public static final TextComponent TEAM_CREATED_SUCCESFULLY = Message.MOD_NAME_PREFIX
                .append(Component.text("Team succesfully created!", NamedTextColor.GREEN));
        public static final TextComponent TEAM_ALREADY_EXISTS = Message.MOD_NAME_PREFIX
                .append(Component.text("Team already exists!", NamedTextColor.RED));
        public static final TextComponent TEAM_ALREADY_JOINED = Message.MOD_NAME_PREFIX
                .append(Component.text("You are already in a team!", NamedTextColor.RED));
        private TeamMessage(){}


        /**
         * Generates DMG message, which will be broadcast among all players
         * @param damagedPlayerName damaged player's name
         * @param damagerName damager name (e.g. Skeleton, fall dmg)
         * @param damage damage amount
         * @return Generated message TextComponent
         */
        public static TextComponent getDMGMessage(String damagedPlayerName,
                                                             String damagerName, double damage){
                return Message.MOD_NAME_PREFIX
                        .append(Component.text(damagedPlayerName, NamedTextColor.RED, TextDecoration.BOLD))
                        .append(Component.text(" took "))
                        .append(Component.text(String.format("%.1f", damage), NamedTextColor.RED, TextDecoration.BOLD))
                        .append(Component.text(" damage from "))
                        .append(Component.text(damagerName, NamedTextColor.RED, TextDecoration.BOLD));
        }
}
