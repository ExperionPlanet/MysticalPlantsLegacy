package io.github.experionplanet.entities;

import io.github.experionplanet.init.MPLEntities;
import io.github.experionplanet.init.MPLParticles;
import io.github.experionplanet.init.MPLStatusEffects;
import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;

public class SporeEntity extends Entity {
    private static final TrackedData<String> SPORE_TYPE = DataTracker.registerData(SporeEntity.class, TrackedDataHandlerRegistry.STRING);
    private int ticking = 0;
    private int duration = 200;

    public SporeEntity(EntityType<? extends SporeEntity> entityType,World world) {
        super(entityType, world);
    }

    public static SporeEntity createSpore(World world, double x, double y, double z, String sporeType) {
        SporeEntity entity = new SporeEntity(MPLEntities.SPORES, world);
        entity.dataTracker.set(SPORE_TYPE, sporeType);
        entity.setPosition(x, y, z);
        return entity;
    }


    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();
        Random rand = world.getRandom();
        Box myBox = this.getBoundingBox();
        if (!world.isClient()) {
            if (this.age % 5 == 0) {
                List<LivingEntity> listLiving = world.getNonSpectatingEntities(LivingEntity.class, myBox);
                if (!listLiving.isEmpty()) {
                    for (LivingEntity target : listLiving) {
                        if (!target.hasStatusEffect(StatusEffects.POISON)) {
                            target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0));
                        }
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
                double m = myBox.maxY/2;
                Vec3d partiPos = new Vec3d(
                        getX() + MysticalUtils.doubleInRange(rand, -0.75, 0.75),
                        getY() + MysticalUtils.doubleInRange(rand, -0.25, 0.25),
                        getZ() + MysticalUtils.doubleInRange(rand, -0.75, 0.75)
                );

                world.addParticle(particleSpore(this.dataTracker.get(SPORE_TYPE)), partiPos.getX(), partiPos.getY(), partiPos.getZ(), 0, 0,0);
            }
        }

    }

    private static ParticleEffect particleSpore(String type) {
        if (type.equals("bog")) {
            return MPLParticles.BOG_SPORE;
        } else if (type.equals("void")) {
            return MPLParticles.VOID_SPORE;
        }

        return null;
    }

    private static RegistryEntry<StatusEffect> effectSpore(String type) {
        if (type.equals("bog")) {
            return StatusEffects.POISON;
        } else if (type.equals("void")) {
            return MPLStatusEffects.VOID;
        }
        return null;
    }



    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(SPORE_TYPE, "bog");
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.age = nbt.getInt("Age");
        this.dataTracker.set(SPORE_TYPE, nbt.getString("SporeType"));
        this.ticking = nbt.getInt("Ticking");
        if (nbt.contains("Duration")) {
            this.duration = nbt.getInt("Duration");
        }

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("Age", this.age);
        nbt.putString("SporeType", this.dataTracker.get(SPORE_TYPE));
        nbt.putInt("Ticking", this.ticking);
        nbt.putInt("Duration", this.duration);
    }
}
