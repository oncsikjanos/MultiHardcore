package com.github.oncsikjanos.multiHardcore.message;

import org.bukkit.damage.DamageType;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.EnumMap;
import java.util.Map;

public class DamageMessage extends Message {
    static Map<EntityDamageEvent.DamageCause, String> damageCauseStringMap
            = new EnumMap<>(EntityDamageEvent.DamageCause.class);

    static {
        damageCauseStringMap.put(EntityDamageEvent.DamageCause.CONTACT, "Damage caused when an entity contacts a block such as a Cactus, Dripstone (Stalagmite) or Berry Bush.");
        damageCauseStringMap.put(EntityDamageEvent.DamageCause.DROWNING, "Drowning");
        damageCauseStringMap.put(EntityDamageEvent.DamageCause.WORLD_BORDER, "World Border");
        damageCauseStringMap.put(EntityDamageEvent.DamageCause.SUFFOCATION, "Suffocation");
        damageCauseStringMap.put(EntityDamageEvent.DamageCause.FALL, "Fall Damage");
        damageCauseStringMap.put(EntityDamageEvent.DamageCause.FIRE, "Fire");
        damageCauseStringMap.put(EntityDamageEvent.DamageCause.FIRE_TICK, "Fire");
        damageCauseStringMap.put(EntityDamageEvent.DamageCause.LAVA, "Lava");
        damageCauseStringMap.put(EntityDamageEvent.DamageCause.MELTING, "Snowman Melting");
        damageCauseStringMap.put(EntityDamageEvent.DamageCause.STARVATION, "Starvation");
    }

}
