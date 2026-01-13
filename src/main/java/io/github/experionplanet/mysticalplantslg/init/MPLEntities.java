package io.github.experionplanet.mysticalplantslg.init;

import io.github.experionplanet.mysticalplantslg.entities.PermafrostSnowballEntity;
import io.github.experionplanet.mysticalplantslg.entities.SoulZombieEntity;
import io.github.experionplanet.mysticalplantslg.entities.SporeEntity;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MPLEntities {

    public static final EntityType<SporeEntity> SPORES = Registry.register(
            Registries.ENTITY_TYPE,
            MysticalUtils.newId("spores"),
            EntityType.Builder.create(SporeEntity::new, SpawnGroup.MISC).dimensions(1.5f, 0.5f).build("spores")
    );

    public static final EntityType<SoulZombieEntity> SOUL_ZOMBIE = Registry.register(
            Registries.ENTITY_TYPE,
            MysticalUtils.newId("soul_zombie"),
            EntityType.Builder.create(SoulZombieEntity::new, SpawnGroup.MISC).dimensions(0.6f, 1.95f).build("soul_zombie")
    );

    public static final EntityType<PermafrostSnowballEntity> PERMAFROST_SNOWBALL = Registry.register(
            Registries.ENTITY_TYPE,
            MysticalUtils.newId("permafrost_snowball"),
            EntityType.Builder.<PermafrostSnowballEntity>create(PermafrostSnowballEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f)
                    .trackingTickInterval(4)

                    .build("permafrost_snowball")
    );

    public static void init() {}
}
