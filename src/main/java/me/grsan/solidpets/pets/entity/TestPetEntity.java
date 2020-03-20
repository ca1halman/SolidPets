package me.grsan.solidpets.pets.entity;

import net.minecraft.server.v1_15_R1.Entity;
import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.World;

public class TestPetEntity extends PetEntity {

    public TestPetEntity(World world) {
        super("test_pet", EntityTypes.PIG,world);

        this.b_types = TestPetEntity::new;
        addAction(entity -> {
            entity.setVelocity(entity.getLocation().getDirection().setY(0.15));
        });
    }

    public TestPetEntity(EntityTypes<Entity> entityEntityTypes, World world) {
        this(world);
    }

    @Override
    public void toggleSitting() {

    }

    @Override
    public void toggleActive() {

    }

    @Override
    public String getName() {
        return "PigPet";
    }
}
