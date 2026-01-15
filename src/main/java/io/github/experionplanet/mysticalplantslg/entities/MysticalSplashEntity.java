package io.github.experionplanet.mysticalplantslg.entities;

import io.github.experionplanet.mysticalplantslg.init.MPLEntities;
import io.github.experionplanet.mysticalplantslg.init.MPLItems;
import io.github.experionplanet.mysticalplantslg.init.MPLSoundEvents;
import io.github.experionplanet.mysticalplantslg.mysticalcontent.MysticalContents;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class MysticalSplashEntity extends ThrownItemEntity {
    private static final TrackedData<String> SPORE_ID = DataTracker.registerData(MysticalSplashEntity.class, TrackedDataHandlerRegistry.STRING);

    public MysticalSplashEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public MysticalSplashEntity(World world, LivingEntity owner, Identifier sporeId) {
        super(MPLEntities.MYSTICAL_SPLASH, owner, world);
        this.dataTracker.set(SPORE_ID,sporeId.toString());
    }

    public MysticalSplashEntity(World world, double x, double y, double z, Identifier sporeId) {
        super(MPLEntities.MYSTICAL_SPLASH, x, y, z, world);
        this.dataTracker.set(SPORE_ID,sporeId.toString());
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SPORE_ID, MysticalUtils.newId("void").toString());
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == 3) {
            ItemStack itemStack = this.getStack();
            World world = this.getWorld();
            Vec3d v = this.getPos();

            double r = 0.4;
            Random rand = world.getRandom();

            for (int i = 0; i < 24; i++) {
                world.addParticle(MysticalContents.SPORE_CONTENT.get(Identifier.of(this.dataTracker.get(SPORE_ID))).statusEffect().createParticle(), v.getX(), v.getY(), v.getZ(), MysticalUtils.doubleInRange(rand, -r, r), MysticalUtils.doubleInRange(rand, 0, r), MysticalUtils.doubleInRange(rand, -r, r));

            }
            for (int i = 0; i < 8; i++) {
                world.addParticle(itemStack.isEmpty() ? new ItemStackParticleEffect(ParticleTypes.ITEM,MPLItems.MYSTICAL_SPLASH.getDefaultStack()) : new ItemStackParticleEffect(ParticleTypes.ITEM,itemStack), v.getX(), v.getY(), v.getZ(), MysticalUtils.doubleInRange(rand, -r, r), MysticalUtils.doubleInRange(rand, 0, r), MysticalUtils.doubleInRange(rand, -r, r));
            }

        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
            StatusEffectInstance inst = MysticalContents.SPORE_CONTENT.get(Identifier.of(this.dataTracker.get(SPORE_ID))).statusEffect();
            livingEntity.addStatusEffect(MysticalUtils.copyStatusEffect(inst));
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if (!this.getWorld().isClient()) {
            ServerWorld world = (ServerWorld) getWorld();

            Vec3d v = hitResult.getPos();

            world.sendEntityStatus(this, (byte) 3);


            world.spawnEntity(new SporeEntity(world, getX(), getY(), getZ(), Identifier.of(this.dataTracker.get(SPORE_ID))));
            world.playSound(null, v.getX(), v.getY(), v.getZ(), MPLSoundEvents.SPORE, SoundCategory.PLAYERS);
            world.playSound(null, v.getX(), v.getY(), v.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return MPLItems.MYSTICAL_SPLASH;
    }


}
