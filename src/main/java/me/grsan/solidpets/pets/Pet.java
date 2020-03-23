package me.grsan.solidpets.pets;

import me.grsan.solidpets.SolidPets;
import me.grsan.solidpets.pets.entity.PetEntity;
import me.grsan.solidpets.pets.entity.PigPetEntity;
import me.grsan.solidpets.util.PlayerUtil;
import me.grsan.solidpets.util.Rarity;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.World;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.json.simple.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class Pet {

    private PetEntity petEntity;
    private OfflinePlayer owner;
    private Rarity rarity;

    private String petClassName;

    private UUID uuid;

    private boolean active = false;

    /**
     * The base pet constructor
     * You shouldn't be using this ctor outside of testing
     * This is what is used to load the pets
     * @param owner The owner of the pet
     * @param rarity The pet rarity, will determine certain factors
     * @param uuid The pet uuid
     */
    public Pet(OfflinePlayer owner, Rarity rarity, UUID uuid, String petClassName) {
        this.owner = owner;
        this.rarity = rarity;
        this.uuid = uuid;

        this.petClassName = petClassName;
    }

    /**
     * Create a brand new pet
     * @param owner The owner of the pet
     * @param rarity The pet rarity, will determine certain factors
     */
    public Pet(OfflinePlayer owner, Rarity rarity, String petClass) {
        this(owner, rarity, UUID.randomUUID(), petClass);
    }

    public void createPetEntity() {
        EntityPlayer ep = PlayerUtil.toEntityPlayer(owner);

        try {
            Class<?> petClass = Class.forName("me.grsan.solidpets.pets.entity." + petClassName);
            Constructor<?> petClassConstructor = petClass.getConstructor(World.class, OfflinePlayer.class);
            this.petEntity = (PetEntity) petClassConstructor.newInstance(ep.getWorld(), owner);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            this.petEntity = new PigPetEntity(ep.getWorld(), owner);
        }

        petEntity.setCustomName(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + petEntity.getName() + "\"}"));
        petEntity.setCustomNameVisible(true);
    }

    public void updateVisbility() {
        if (petEntity == null && owner.getPlayer() != null)
            createPetEntity();

        if (active) {
            if (owner.getPlayer() != null) {
                owner.getPlayer().playSound(owner.getPlayer().getLocation(), Sound.ENTITY_ITEM_PICKUP, 1,1);
                Location ownerLocaiton = owner.getPlayer().getLocation();
                petEntity.setPosition(ownerLocaiton.getX(), ownerLocaiton.getY(), ownerLocaiton.getZ());
            }
            this.petEntity.dead = false;
            PlayerUtil.toEntityPlayer(owner).getWorld().addEntity(this.petEntity);
        } else
            petEntity.getBukkitEntity().remove();
    }

    public void toggleActive() {
        active = !active;

        updateVisbility();
    }

    public void setActive(boolean active) {
        this.active = active;

        updateVisbility();
    }

    public PetEntity getPetEntity() {
        if (petEntity == null && owner.getPlayer() != null)
            createPetEntity();
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
        json.put("rarity", rarity.toString());
        json.put("type", petEntity.getClass().getName());

        return json;
    }
}
