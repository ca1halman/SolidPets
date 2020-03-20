package me.grsan.solidpets.pets.entity;

import net.minecraft.server.v1_15_R1.*;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;

public abstract class PetEntity extends EntityCreature {

    interface Action {
        void execute(LivingEntity entity);
    }

    private boolean sitting = false;
    private boolean active = false;
    private ArrayList<Action> actions = new ArrayList<>();

    private String registryName;
    private EntityTypes<?> type;
    private World world;

    //TODO: Make setting this easier & cleaner
    EntityTypes.b<Entity> b_types;

    public PetEntity(String registryName, EntityTypes<? extends EntityCreature> type, World world) {
        super(type, world);
        this.registryName = registryName;
        this.type = type;
        this.world = world;

        this.setCustomName(IChatBaseComponent.ChatSerializer.a(this.getRegistryName()));
        this.setCustomNameVisible(true);
        this.attachedToPlayer = true;
    }

    public abstract void toggleSitting();
    public abstract void toggleActive();

    public void doAction(int actionIndex){
        Action action = actions.get(actionIndex);
        action.execute((LivingEntity)this);
    }

    public void addAction(Action a) {
        actions.add(a);
    }

    public String getRegistryName() {
        return registryName;
    }

    public EntityTypes<?> getType() {
        return type;
    }

    public EntityTypes.b<Entity> getB_types() {
        return b_types;
    }
}
