package me.grsan.solidpets;

import org.bukkit.plugin.java.JavaPlugin;

public final class SolidPets extends JavaPlugin {

    private static SolidPets instance;

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SolidPets getInstance() {
        return instance;
    }
}
