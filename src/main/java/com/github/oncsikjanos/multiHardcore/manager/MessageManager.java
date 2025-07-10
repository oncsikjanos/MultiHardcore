package com.github.oncsikjanos.multiHardcore.manager;

import com.github.oncsikjanos.multiHardcore.message.TeamMessage;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;



public class MessageManager {


    public void sendDMGMessageToPlayers(Player playerToSend, String damagedPlayerName,
                                        String damagerName, double damage){
        TextComponent message = TeamMessage.getDMGMessage(damagedPlayerName, damagerName, damage);

        playerToSend.sendMessage(message);
    }

}
