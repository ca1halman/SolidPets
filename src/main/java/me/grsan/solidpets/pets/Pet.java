package me.grsan.solidpets.pets;

import me.grsan.solidpets.SolidPets;
import me.grsan.solidpets.pets.entity.PetEntity;
import me.grsan.solidpets.pets.entity.PigPetEntity;
import me.grsan.solidpets.util.PlayerUtil;
import me.grsan.solidpets.util.Rarity;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import org.bukkit.OfflinePlayer;
import org.json.simple.JSONObject;

import java.util.UUID;

public class Pet {

    private PetEntity petEntity;
    private OfflinePlayer owner;
    private Rarity rarity;

    private UUID uuid;

    private boolean active = true;

    /**
     * The base pet constructor
     * You shouldn't be using this ctor outside of testing
     * @param owner The owner of the pet
     * @param rarity The pet rarity, will determine certain factors
     * @param uuid The pet uuid
     */
    public Pet(OfflinePlayer owner, Rarity rarity, UUID uuid) {
        EntityPlayer ep = PlayerUtil.toEntityPlayer(owner);

        this.petEntity = new PigPetEntity(ep.getWorld());
        this.owner = owner;
        this.rarity = rarity;
        this.uuid = uuid;
    }

    /**
     * For loading a pet from a config file - pass pet uuid, this will fill in other info
     * @param uuid The UUID
     */
    public Pet(UUID uuid) {
        //note that this ctor is not implemented yet, and will obtain info from a json file
        this(SolidPets.getInstance().getServer().getOfflinePlayer(UUID.randomUUID()), Rarity.COMMON, uuid);
    }

    /**
     * Create a brand new pet
     * @param owner The owner of the pet
     * @param rarity The pet rarity, will determine certain factors
     */
    public Pet(OfflinePlayer owner, Rarity rarity) {
        this(owner, rarity, UUID.randomUUID());
    }

    public void toggleActive() {
        active = !active;
        if (active)
            petEntity = new PigPetEntity(PlayerUtil.toEntityPlayer(owner).getWorld());
        else
            petEntity.getBukkitEntity().remove();
    }

    public PetEntity getPetEntity() {
        return petEntity;
    }

    public OfflinePlayer getOwner() {
        return owner;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public boolean isActive() {
        return active;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setOwner(OfflinePlayer owner) {
        this.owner = owner;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    @SuppressWarnings("unchecked")
    public JSONObject serialize() {
        JSONObject json = new JSONObject();
        json.put("player-uuid", owner.getUniqueId().toString());
        json.put("pet-rarity", rarity.toString());

        return json;
    }
}
