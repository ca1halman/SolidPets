package me.grsan.solidpets.pets.entity;

import net.minecraft.server.v1_15_R1.Entity;
import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.World;

public class PigPetEntity extends PetEntity {

    public PigPetEntity(World world) {
        super(EntityTypes.PIG,world);

        addAction(entity -> {
            System.out.println("Executed Action!");
            entity.setVelocity(entity.getLocation().getDirection().setY(0.15));
        });
    }

    public PigPetEntity(EntityTypes<Entity> entityEntityTypes, World world) {
        this(world);
    }

    @Override
    public String getRegistryName() {
        return "pig_pet";
    }

    @Override
    public String getName() {
        return "PigPet";
    }
}
