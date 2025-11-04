package io.github.experionplanet.entities;

import io.github.experionplanet.init.MPLEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;

public class SoulZombieEntity extends ZombieEntity {
    public SoulZombieEntity(EntityType<? extends SoulZombieEntity> entityType, World world) {
        super(entityType,world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, (double)35.0F).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, (double)0.23F).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, (double)5.0F).add(EntityAttributes.GENERIC_ARMOR, (double)4F).add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }
}
