package com.github.oncsikjanos.multiHardcore.message;

import org.bukkit.damage.DamageType;

import java.util.HashMap;
import java.util.Map;

public class DamageMessage extends Message {
    static Map<DamageType, String> damageCauseStringMap = new HashMap<>();

    static {
        //damageCauseStringMap.put(DamageType.ARROW, "Arrow");
        damageCauseStringMap.put(DamageType.BAD_RESPAWN_POINT, "Bad Respawn Point");
        damageCauseStringMap.put(DamageType.CACTUS, "Touching Cactus");
        damageCauseStringMap.put(DamageType.CAMPFIRE, "Touching Campfire");
        damageCauseStringMap.put(DamageType.CRAMMING, "Cramming");
        damageCauseStringMap.put(DamageType.DRAGON_BREATH, "Dragon Breath"); //Handled by EntityByEntityEvent, only safety
        damageCauseStringMap.put(DamageType.DROWN, "Drowning");
        damageCauseStringMap.put(DamageType.DRY_OUT, "Drying Out"); //Only mobs get this type of dmg
        //damageCauseStringMap.put(DamageType.ENDER_PEARL, "Thrown Ender Pearl"); // Probably will cause weird bug, have to test it
        damageCauseStringMap.put(DamageType.EXPLOSION, "Explosion");
        damageCauseStringMap.put(DamageType.FALL, "Fall Damage");
        damageCauseStringMap.put(DamageType.FALLING_ANVIL, "Falling Anvil");
        damageCauseStringMap.put(DamageType.FALLING_BLOCK, "Falling Block");
        damageCauseStringMap.put(DamageType.FALLING_STALACTITE, "Falling Stalactite");
        damageCauseStringMap.put(DamageType.FIREBALL, "Fireball"); //Handled by EntityByEntityEvent, only safety
        damageCauseStringMap.put(DamageType.FIREWORKS, "Fireworks");
        damageCauseStringMap.put(DamageType.FLY_INTO_WALL, "Flying Into Wall");
        damageCauseStringMap.put(DamageType.FREEZE, "Freezing");
        //damageCauseStringMap.put(DamageType.GENERIC, "Generic");
        //damageCauseStringMap.put(DamageType.GENERIC_KILL, "Generic Kill");
        damageCauseStringMap.put(DamageType.HOT_FLOOR, "Touching Hot Floor");
        damageCauseStringMap.put(DamageType.IN_FIRE, "Fire");
        damageCauseStringMap.put(DamageType.IN_WALL, "Stuck In Wall");
        damageCauseStringMap.put(DamageType.INDIRECT_MAGIC, "Indirect Magic");
        damageCauseStringMap.put(DamageType.LAVA, "Lava");
        damageCauseStringMap.put(DamageType.LIGHTNING_BOLT, "Lightning Bolt");
        //damageCauseStringMap.put(DamageType.MACE_SMASH, "Mace Smash");
        damageCauseStringMap.put(DamageType.MAGIC, "Magic");
        damageCauseStringMap.put(DamageType.MOB_ATTACK, "Mob Attack"); //Handled by EntityByEntityEvent, only safety
        damageCauseStringMap.put(DamageType.MOB_ATTACK_NO_AGGRO, "Mob Attack"); //Handled by EntityByEntityEvent, only safety
        damageCauseStringMap.put(DamageType.MOB_PROJECTILE, "Mob Attack"); //Handled by EntityByEntityEvent, only safety
        damageCauseStringMap.put(DamageType.ON_FIRE, "Fire");
        damageCauseStringMap.put(DamageType.OUT_OF_WORLD, "Out Of World");
        damageCauseStringMap.put(DamageType.OUTSIDE_BORDER, "Outside Border");
        damageCauseStringMap.put(DamageType.PLAYER_ATTACK, "Player Attack"); //Handled by EntityByEntityEvent, only safety
        damageCauseStringMap.put(DamageType.PLAYER_EXPLOSION, "Player Explosion"); //Handled by EntityByEntityEvent, only safety
        damageCauseStringMap.put(DamageType.SONIC_BOOM, "Sonic Boom"); //Handled by EntityByEntityEvent, only safety
        damageCauseStringMap.put(DamageType.SPIT, "Spit"); //Handled by EntityByEntityEvent, only safety
        damageCauseStringMap.put(DamageType.STALAGMITE, "Stalagmite"); //Handled by EntityByEntityEvent, only safety
        damageCauseStringMap.put(DamageType.STARVE, "Starving");
        damageCauseStringMap.put(DamageType.STING, "Sting"); //Handled by EntityByEntityEvent, only safety
        damageCauseStringMap.put(DamageType.SWEET_BERRY_BUSH, "Touching Sweet Berry Bush");
        damageCauseStringMap.put(DamageType.THORNS, "Thorns Enchantment"); //Handled by EntityByEntityEvent, only safety
        damageCauseStringMap.put(DamageType.THROWN, "Thrown Trident"); //Handled by EntityByEntityEvent, only safety
        damageCauseStringMap.put(DamageType.TRIDENT, "Trident"); //Handled by EntityByEntityEvent, only safety
        damageCauseStringMap.put(DamageType.UNATTRIBUTED_FIREBALL, "Unattributed Fireball"); //Handled by EntityByEntityEvent, only safety
        damageCauseStringMap.put(DamageType.WIND_CHARGE, "Wind Charge"); //Player don't get this type of dmg, only safety
        damageCauseStringMap.put(DamageType.WITHER, "Wither"); //Handled by EntityByEntityEvent, only safety
        damageCauseStringMap.put(DamageType.WITHER, "Wither Skull"); //Handled by EntityByEntityEvent, only safety
    }

    public static String getString(DamageType damageType){
        return damageCauseStringMap.get(damageType);
    }
}
