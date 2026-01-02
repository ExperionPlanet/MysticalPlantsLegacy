package io.github.experionplanet.entities;

import io.github.experionplanet.init.MPLEntities;
import io.github.experionplanet.utils.ExperionLogger;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class SoulZombieEntity extends ZombieEntity {
    private static final TrackedData<BlockPos> ORIGIN_POS = DataTracker.registerData(SoulZombieEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
    private static final TrackedData<Boolean> HAS_ORIGIN = DataTracker.registerData(SoulZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final int maxDistance = 20;

    public SoulZombieEntity(EntityType<? extends SoulZombieEntity> entityType, World world) {
        super(entityType,world);
    }

    public static SoulZombieEntity of(World world, BlockPos targetPos) {
        SoulZombieEntity entity = new SoulZombieEntity(MPLEntities.SOUL_ZOMBIE, world);
        entity.dataTracker.set(ORIGIN_POS, targetPos);
        entity.dataTracker.set(HAS_ORIGIN, true);
        return entity;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ORIGIN_POS,  BlockPos.ORIGIN);
        builder.add(HAS_ORIGIN, false);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, (double)35.0F).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, (double)0.23F).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, (double)5.0F).add(EntityAttributes.GENERIC_ARMOR, (double)4F).add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }

    @Override
    protected boolean burnsInDaylight() {
        return false;
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        if (random.nextFloat() <= 0.35f) {
            int choosen = random.nextBetween(1, 3);

            if (choosen == 1) {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
            } else if (choosen == 2) {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_AXE));
            } else {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SHOVEL));
            }
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return damageSource.isOf(DamageTypes.LAVA) || damageSource.isOf(DamageTypes.ON_FIRE) || damageSource.isOf(DamageTypes.IN_FIRE) || super.isInvulnerableTo(damageSource);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.dataTracker.get(HAS_ORIGIN)) {
            BlockPos origin = this.dataTracker.get(ORIGIN_POS);

            double dist = this.squaredDistanceTo(origin.getX() + 0.5, origin.getY() + 0.5, origin.getZ() + 0.5);
            if (dist > (double) (maxDistance * maxDistance)) {
                Vec3d v = ExperionUtils.v3dConvert(origin, true);
                this.teleport(v.getX(), v.getY(), v.getZ(), false);
            }
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("origin_pos")) {
            NbtCompound c = nbt.getCompound("origin_pos");
            this.dataTracker.set(ORIGIN_POS, new BlockPos(c.getInt("x"), c.getInt("y"), c.getInt("z")));
            this.dataTracker.set(HAS_ORIGIN, true);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.dataTracker.get(HAS_ORIGIN)) {
            NbtCompound compound = new NbtCompound();
            BlockPos v = this.dataTracker.get(ORIGIN_POS);
            compound.putInt("x", v.getX());
            compound.putInt("y", v.getX());
            compound.putInt("z", v.getX());
            nbt.put("origin_pos", compound);
        }
    }

    @Override
    protected void dropLoot(DamageSource damageSource, boolean causedByPlayer) {}

    @Override
    protected int getXpToDrop() {
        return 0;
    }

    @Override
    protected boolean canConvertInWater() {
        return false;
    }

    @Override
    public Set<EquipmentSlot> dropEquipment(Predicate<ItemStack> dropPredicate) {
        return new HashSet<>();
    }

}
