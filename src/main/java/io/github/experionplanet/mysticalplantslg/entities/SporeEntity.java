package io.github.experionplanet.mysticalplantslg.entities;

import io.github.experionplanet.mysticalplantslg.init.MPLEntities;
import io.github.experionplanet.mysticalplantslg.mysticalcontent.MysticalContents;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;

public class SporeEntity extends Entity {
    private static final TrackedData<String> SPORE_ID = DataTracker.registerData(SporeEntity.class, TrackedDataHandlerRegistry.STRING);
    private int ticking = 0;
    private int duration = 200;

    public SporeEntity(EntityType<? extends SporeEntity> entityType,World world) {
        super(entityType, world);
    }

    public SporeEntity(World world, double x, double y, double z, Identifier sporeID) {
        super(MPLEntities.SPORES, world);
        this.setPosition(x,y,z);
        this.dataTracker.set(SPORE_ID, sporeID.toString());
        duration = MysticalContents.SPORE_CONTENT.get(sporeID).isShort ? 40 : 200;
    }


    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();
        Random rand = world.getRandom();
        Box myBox = this.getBoundingBox();

        SporeValue sporeVal = getSporeValue();
        StatusEffectInstance statusInst = sporeVal.statusEffect();

        if (!world.isClient()) {
            if (this.age % 5 == 0) {
                List<LivingEntity> listLiving = world.getNonSpectatingEntities(LivingEntity.class, myBox);
                if (!listLiving.isEmpty()) {
                    for (LivingEntity target : listLiving) {
                        target.addStatusEffect(new StatusEffectInstance(statusInst.getEffectType(), statusInst.getDuration(), statusInst.getAmplifier()));
                    }
                }
            }

            if (this.ticking >= this.duration) {
                this.discard();
            }

            if (this.isAlive()) {
                this.ticking++;
            }
        }else {
            if (this.age % 2 == 0) {
                Vec3d partiPos = new Vec3d(
                        getX() + MysticalUtils.doubleInRange(rand, -0.75, 0.75),
                        getY() + MysticalUtils.doubleInRange(rand, -0.25, 0.25),
                        getZ() + MysticalUtils.doubleInRange(rand, -0.75, 0.75)
                );

                world.addParticle((ParticleEffect) sporeVal.getParticle(), partiPos.getX(), partiPos.getY(), partiPos.getZ(), 0, 0,0);
            }
        }

    }

    private SporeValue getSporeValue() {
        return MysticalContents.SPORE_CONTENT.get(Identifier.of(this.dataTracker.get(SPORE_ID)));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(SPORE_ID, MysticalUtils.newId("bog").toString());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.age = nbt.getInt("Age");
        this.dataTracker.set(SPORE_ID, nbt.getString("SporeType"));
        this.ticking = nbt.getInt("Ticking");
        if (nbt.contains("Duration")) {
            this.duration = nbt.getInt("Duration");
        }

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("Age", this.age);
        nbt.putString("SporeType", this.dataTracker.get(SPORE_ID));
        nbt.putInt("Ticking", this.ticking);
        nbt.putInt("Duration", this.duration);
    }

    public record SporeValue(StatusEffectInstance statusEffect, Identifier particleID, boolean isShort) {
        public static SporeValue of(StatusEffectInstance statusEffect, ParticleType<?> a) {
            return of(statusEffect, a, false);
        }
        public static SporeValue of(StatusEffectInstance statusEffect, ParticleType<?> a, boolean isShort) {
            return new SporeValue(statusEffect, Registries.PARTICLE_TYPE.getId(a), isShort);
        }

        public ParticleType<? extends ParticleEffect> getParticle() {
            return Registries.PARTICLE_TYPE.get(particleID);
        }
    }
}
