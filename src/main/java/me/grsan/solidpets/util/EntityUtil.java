package me.grsan.solidpets.util;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import me.grsan.solidpets.pets.entity.PetEntity;
import net.minecraft.server.v1_15_R1.*;

import java.util.Map;

public class EntityUtil {

    public static void registerEntity(PetEntity entity) {
        MinecraftKey key =  MinecraftKey.a(entity.getRegistryName());

        Map<Object, Type<?>> typeMap = (Map<Object, Type<?>>) DataConverterRegistry.a().getSchema(DataFixUtils.makeKey(SharedConstants.getGameVersion().getWorldVersion())).findChoiceType(DataConverterTypes.ENTITY).types();
        typeMap.put(key.toString(), typeMap.get("minecraft:" + MinecraftKey.a(entity.getType().h().getKey()).getKey().split("/")[1]));

        EntityTypes.a<Entity> e = EntityTypes.a.a(entity.getB_types(), EnumCreatureType.CREATURE);
        IRegistry.a(IRegistry.ENTITY_TYPE, entity.getRegistryName(), e.a(entity.getRegistryName()));
    }

}
