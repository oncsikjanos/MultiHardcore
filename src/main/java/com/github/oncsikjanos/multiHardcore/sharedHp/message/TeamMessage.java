package com.github.oncsikjanos.multiHardcore.sharedHp.message;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

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
}
