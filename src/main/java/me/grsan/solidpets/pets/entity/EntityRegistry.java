package me.grsan.solidpets.pets.entity;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import net.minecraft.server.v1_15_R1.*;

import java.util.Map;

public enum EntityRegistry {
    PIG_PET("pig_pet",EntityTypes.PIG, PigPetEntity.class, PigPetEntity::new);

    private String registryName;
    private EntityTypes<? extends EntityCreature> entityType;
    private Class<? extends PetEntity> entityClass;
    private EntityTypes.b<?> b_types;

    EntityRegistry(String registryName, EntityTypes<? extends EntityCreature> entityType, Class<? extends PetEntity> entityClass, EntityTypes.b<?> b_types) {
        this.registryName = registryName;
        this.entityType = entityType;
        this.entityClass = entityClass;
        this.b_types = b_types;
    }

    public static void registerEntities() {
        for (EntityRegistry entity : values())
            registerEntity(entity);
    }

    @SuppressWarnings({"unchecked","ConstantConditions"})
    public static void registerEntity(EntityRegistry entity) {
        //Thanks to the following thread for this code, very helpful
        //modified to fit with my system & updated to 1.15.2
        //https://www.spigotmc.org/threads/registering-custom-entities-in-1-14-2.381499/
        MinecraftKey key =  MinecraftKey.a(entity.getRegistryName());

        Map<Object, Type<?>> typeMap = (Map<Object, Type<?>>) DataConverterRegistry.a().getSchema(DataFixUtils.makeKey(SharedConstants.getGameVersion().getWorldVersion())).findChoiceType(DataConverterTypes.ENTITY).types();
        typeMap.put(key.toString(), typeMap.get("minecraft:" + MinecraftKey.a(entity.getType().h().getKey()).getKey().split("/")[1]));

        EntityTypes.a<Entity> e = EntityTypes.a.a(entity.getB_types(), EnumCreatureType.CREATURE);
        IRegistry.a(IRegistry.ENTITY_TYPE, entity.getRegistryName(), e.a(entity.getRegistryName()));

        System.out.println("Registered: " + entity.getRegistryName());
    }

    public String getRegistryName() {
        return registryName;
    }

    public EntityTypes<? extends EntityCreature> getType() {
        return entityType;
    }

    public Class<? extends PetEntity> getEntityClass() {
        return entityClass;
    }

    public EntityTypes.b<?> getB_types() {
        return b_types;
    }
}
