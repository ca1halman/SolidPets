package me.grsan.solidpets.pets.entity;

import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;

//There is no reason for this class being abstract other than for it to stand out in my ide better
public abstract class PetEntity extends EntityInsentient {

    interface Action {
        void execute(LivingEntity entity);
    }

    private ArrayList<Action> actions = new ArrayList<>();

    private EntityTypes<?> type;

    private OfflinePlayer owner;

    public PetEntity(World world) {
        //this should be overridden
        super(EntityTypes.BAT,world);
    }

    public PetEntity(EntityTypes<? extends EntityCreature> type, World world) {
        super(type, world);
        this.type = type;
    }

    @SuppressWarnings("rawtypes")
    protected void clearGoals() {

        try {
            Field dField;
            dField = PathfinderGoalSelector.class.getDeclaredField("d");
            ((LinkedHashSet) dField.get(goalSelector)).clear();
            ((LinkedHashSet) dField.get(targetSelector)).clear();
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("Cleared Goals");
    }

    public abstract void applyPathfinders();

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

    public OfflinePlayer getOwner() {
        return owner;
    }
}
