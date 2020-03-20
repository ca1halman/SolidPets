package me.grsan.solidpets.pets;

import me.grsan.solidpets.util.Rarity;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;

public class Pet<E extends LivingEntity> {

    private E entityType;
    private Class<E> entityClass;
    private OfflinePlayer owner;
    private Rarity rarity;

    public Pet(E entityType, OfflinePlayer owner, Rarity rarity) {
        this.entityType = entityType;
        this.owner = owner;
        this.rarity = rarity;
    }

    public E getEntityType() {
        return entityType;
    }

    public OfflinePlayer getOwner() {
        return owner;
    }

    public void setOwner(OfflinePlayer owner) {
        this.owner = owner;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }
}
