package me.grsan.solidpets;

import me.grsan.solidpets.pets.Pet;
import me.grsan.solidpets.pets.entity.EntityRegistry;
import me.grsan.solidpets.util.Config;
import me.grsan.solidpets.util.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public final class SolidPets extends JavaPlugin {

    private static SolidPets instance;
    private static Config conf;

    private HashMap<OfflinePlayer, ArrayList<Pet>> playerPets;

    @Override
    public void onEnable() {
        //Create configs
        instance = this;
        try {
            conf = new Config(new File(getDataFolder(),"SolidPets/config.json"));
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        playerPets = new HashMap<>();
        playerPets = conf.getPlayerPets();

        //This is a temporary test command
        getCommand("summonTestPet").setExecutor((sender, command, label, args) -> {

            if (!(sender instanceof Player)) {
                getLogger().log(Level.INFO, ChatColor.RED + "You must be a player to execute this command!");
                return true;
            }

            Player p = (Player) sender;
            if (!playerPets.containsKey(p))
                playerPets.put(p, new ArrayList<>());
            playerPets.get(p).add(new Pet(p, Rarity.COMMON, "PigPetEntity"));

            return true;

        });

        getCommand("togglePet").setExecutor((sender, command, label, args) -> {

            if (!(sender instanceof Player)) {
                getLogger().log(Level.INFO, ChatColor.RED + "You must be a player to execute this command!");
                return true;
            }

            Player p = (Player) sender;
            if (playerPets.get(p) == null)
                return true;

            if (args.length == 0) {
                p.sendMessage(ChatColor.GREEN + "You can toggle the following pets: ");
                for (Pet pet : playerPets.get(p)) {
                    p.sendMessage(ChatColor.DARK_GREEN + "    " + playerPets.get(p).indexOf(pet)
                            + " with type: " + ChatColor.WHITE + pet.getPetEntity().getClass().getSimpleName());
                }
            } else {
                if (Integer.parseInt(args[0]) > playerPets.get(p).size() || Integer.parseInt(args[0]) < 0) {
                    p.sendMessage(ChatColor.RED + "Please enter a valid id !");
                    return true;
                }

                playerPets.get(p).get(Integer.parseInt(args[0])).toggleActive();
            }

            return true;
        });
    }

    @Override
    public void onLoad() {
        //this isn't the best way but i figured id try something different
        EntityRegistry.registerEntities();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        for(ArrayList<Pet> petList : playerPets.values()) {
            for (Pet p : petList) {
                p.setActive(false);
            }
        }

        conf.savePlayerPets();
        conf.saveConfig();
    }

    public static SolidPets getInstance() {
        return instance;
    }

    public static Config getConf() {
        return conf;
    }

    public HashMap<OfflinePlayer, ArrayList<Pet>> getPlayerPets() {
        return playerPets;
    }
}
