package io.github.experionplanet.init;

import io.github.experionplanet.entities.SoulZombieEntity;
import io.github.experionplanet.entities.SporeEntity;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MPLEntities {

    public static final EntityType<SporeEntity> SPORES = Registry.register(
            Registries.ENTITY_TYPE,
            ExperionUtils.newId("spores"),
            EntityType.Builder.create(SporeEntity::new, SpawnGroup.MISC).dimensions(1.5f, 0.5f).build("spores")
    );

    public static final EntityType<SoulZombieEntity> SOUL_ZOMBIE = Registry.register(
            Registries.ENTITY_TYPE,
            ExperionUtils.newId("soul_zombie"),
            EntityType.Builder.create(SoulZombieEntity::new, SpawnGroup.MISC).dimensions(0.6f, 1.95f).build("soul_zombie")
    );

    public static void init() {}
}
