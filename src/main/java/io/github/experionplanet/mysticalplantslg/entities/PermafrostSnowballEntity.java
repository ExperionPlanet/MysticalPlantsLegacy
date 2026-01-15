package io.github.experionplanet.mysticalplantslg.entities;

import io.github.experionplanet.mysticalplantslg.init.MPLEntities;
import io.github.experionplanet.mysticalplantslg.init.MPLItems;
import io.github.experionplanet.mysticalplantslg.init.MPLStatusEffects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class PermafrostSnowballEntity extends ThrownItemEntity {
    public PermafrostSnowballEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public PermafrostSnowballEntity(World world, LivingEntity owner) {
        super(MPLEntities.PERMAFROST_SNOWBALL, owner, world); // null will be changed later
    }

    public PermafrostSnowballEntity(World world, double x, double y, double z) {
        super(MPLEntities.PERMAFROST_SNOWBALL, x, y, z, world); // null will be changed later
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), 1f);
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect(new StatusEffectInstance(MPLStatusEffects.PERMAFROST, 20, 0));
        }
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() { // Not entirely sure, but probably has do to with the snowball's particles. (OPTIONAL)
        ItemStack itemStack = this.getStack();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handleStatus(byte status) { // Also not entirely sure, but probably also has to do with the particles. This method (as well as the previous one) are optional, so if you don't understand, don't include this one.
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return MPLItems.PERMAFROST_SNOWBALL;
    }
}
