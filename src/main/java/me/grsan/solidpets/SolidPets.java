package me.grsan.solidpets;

import me.grsan.solidpets.pets.Pet;
import me.grsan.solidpets.util.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class SolidPets extends JavaPlugin {

    private static SolidPets instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("summonTestPet").setExecutor((sender, command, label, args) -> {

            if (!(sender instanceof Player)) {
                getLogger().log(Level.INFO, ChatColor.RED + "You must be a player to execute this command!");
                return true;
            }

            Player p = (Player) sender;
            Pet pet = new Pet(p, Rarity.COMMON);

            return true;

        });
    }

    @Override
    public void onLoad() {
        //this isn't the best way but i figured id try something different
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SolidPets getInstance() {
        return instance;
    }
}
