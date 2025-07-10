package com.github.oncsikjanos.multiHardcore.message;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public final class TeamMessage extends Message{
        public static final TextComponent PLAYER_DMG_TEXT = Message.MOD_NAME_PREFIX
                .append(Component.text("Only players can create team!", NamedTextColor.RED));

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
                        .append(Component.text(damage, NamedTextColor.RED, TextDecoration.BOLD))
                        .append(Component.text(" from "))
                        .append(Component.text(damagerName, NamedTextColor.RED, TextDecoration.BOLD));
        }
}
