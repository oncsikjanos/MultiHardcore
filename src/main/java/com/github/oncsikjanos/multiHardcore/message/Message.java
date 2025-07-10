package com.github.oncsikjanos.multiHardcore.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class Message {
    public static final TextComponent MOD_NAME_PREFIX = Component.text("[", NamedTextColor.WHITE)
            .append(Component.text("MultiHardcore", NamedTextColor.AQUA))
            .append(Component.text("] ", NamedTextColor.WHITE));
    public static final TextComponent UNKNOWN_ERROR = Message.MOD_NAME_PREFIX
            .append(Component.text("Unknown error!", NamedTextColor.RED));
}
