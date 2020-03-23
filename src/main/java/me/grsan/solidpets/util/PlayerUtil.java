package me.grsan.solidpets.util;

import me.grsan.solidpets.pets.Pet;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;

import java.util.ArrayList;

public class PlayerUtil {

    @SuppressWarnings("ConstantConditions")
    public static EntityPlayer toEntityPlayer(OfflinePlayer player) {
        return ((CraftPlayer) player.getPlayer()).getHandle();
    }

    public static ArrayList<Pet> getPlayerPets(OfflinePlayer player) {
        ArrayList<Pet> pets = new ArrayList<>();
        return pets;
    }

}
