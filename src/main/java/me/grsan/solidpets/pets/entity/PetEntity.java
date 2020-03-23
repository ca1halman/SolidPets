package me.grsan.solidpets.pets.entity;

import net.minecraft.server.v1_15_R1.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;

//There is no reason for this class being abstract other than for it to stand out in my ide better
public abstract class PetEntity extends EntityCreature {

    interface Action {
        void execute(LivingEntity entity);
    }

    private boolean active = false;
    private ArrayList<Action> actions = new ArrayList<>();

    private EntityTypes<?> type;

    private OfflinePlayer owner;

    public PetEntity(EntityTypes<? extends EntityCreature> type, World world) {
        super(type, world);
        this.type = type;

        this.setCustomName(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + getName() + "\"}"));
        this.setCustomNameVisible(true);
        this.attachedToPlayer = true;
        this.setNoAI(false);
    }

    public void doAction(int actionIndex){
        Action action = actions.get(actionIndex);
        action.execute((LivingEntity)this);
    }

    public void addAction(Action a) {
        actions.add(a);
    }

    @Override
    public void b(NBTTagCompound var0) {
        super.b(var0);
        var0.setString("id",getRegistryName());
        if (owner != null)
            var0.setString("owner",owner.getUniqueId().toString());
    }

    public String getRegistryName() {
        return "basic_pet";
    }

    public EntityTypes<?> getType() {
        return type;
    }

    public void setOwner(OfflinePlayer owner) {
        this.owner = owner;
    }
}
