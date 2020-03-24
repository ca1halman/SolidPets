package me.grsan.solidpets.pets.entity;

import me.grsan.solidpets.util.PlayerUtil;
import me.grsan.solidpets.util.pathfinder.PathfinderGoalFollowPlayer;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public class PigPetEntity extends PetEntity {

    public PigPetEntity(World world) {
        super(EntityTypes.PIG,world);
        setHealth(6);

        addAction(entity -> {
            System.out.println("Executed Action!");
            jump();
        });

        setPersistent();
        clearGoals();
    }
    public PigPetEntity(World world, OfflinePlayer owner) {
        this(world);

        setOwner(owner);

        if (owner.getPlayer() != null) {
            Location ownerLocation = owner.getPlayer().getLocation();
            setPosition(ownerLocation.getX(), ownerLocation.getY(), ownerLocation.getZ());
        }

        applyPathfinders();
    }

    public PigPetEntity(EntityTypes<Entity> entityEntityTypes, World world) {
        this(world);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(6);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25);
    }

    @Override
    public void applyPathfinders() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalRandomLookaround(this));

        this.targetSelector.a(0, new PathfinderGoalFollowPlayer(this,PlayerUtil.toEntityPlayer(getOwner()), 1.4f, 2f, 3.5f));
    }

    @Override
    public String getRegistryName() {
        return "pig_pet";
    }

    @Override
    public String getName() {
        return getOwner().getName() + "'s Pig Pet";
    }
}
