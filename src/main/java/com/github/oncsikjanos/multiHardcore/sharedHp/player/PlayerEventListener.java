package com.github.oncsikjanos.multiHardcore.sharedHp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEventListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player){
            ((Player) e.getEntity()).chat("dmged");
        }

    }
}
