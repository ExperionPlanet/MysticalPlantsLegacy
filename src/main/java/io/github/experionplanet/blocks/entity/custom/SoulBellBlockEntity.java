package io.github.experionplanet.blocks.entity.custom;

import io.github.experionplanet.blocks.custom.SoulBellBlock;
import io.github.experionplanet.blocks.entity.LastTickedBlockEntity;
import io.github.experionplanet.entities.SoulZombieEntity;
import io.github.experionplanet.init.MPLBlockEntities;
import io.github.experionplanet.init.MPLEntities;
import io.github.experionplanet.utils.ExperionLogger;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SoulBellBlockEntity extends LastTickedBlockEntity {
    public static final int T_SOUL = 1;

    public List<UUID> targetList = new ArrayList<>();

    public SoulBellBlockEntity(BlockPos pos, BlockState state) {
        super(MPLBlockEntities.SOUL_BELL, pos, state);
        addTick();
        addTick();
    }

    private static void startRound(int round, World world, BlockPos pos, SoulBellBlockEntity blockEntity) {
        int normal = 0;

        if (round == 1) {
            normal = 3;
        }

        for (int i = 0; i < normal; i++) {
            ExperionLogger.Print("A " + i);
            SoulZombieEntity soulZombieEntity = new SoulZombieEntity(MPLEntities.SOUL_ZOMBIE, world);
            spawnEntity(world, pos, soulZombieEntity, blockEntity);
        }
    }

    private static void spawnEntity(World world, BlockPos pos, Entity entity, SoulBellBlockEntity blockEntity) {
        Random rand = world.getRandom();
        /*


        double x = pos.getX() + (rand.nextDouble() - rand.nextDouble()) * 4.0 + 0.5;
        double y = pos.getY() + rand.nextInt(3) - 1;
        double z = pos.getZ() + (rand.nextDouble() - rand.nextDouble()) * 4.0 + 0.5;

        entity.refreshPositionAndAngles(x, y, z, rand.nextFloat() * 360F, 0);

        if (!world.isSpaceEmpty(entity.getBoundingBox())) return;*/


        Vec3d v = ExperionUtils.v3dConvert(pos, true);

        entity.refreshPositionAndAngles(v.getX(), v.getY(), v.getZ(), rand.nextFloat() * 360F, 0);
        blockEntity.targetList.add(entity.getUuid());

        world.spawnEntity(entity);

    }

    public static void onServerTick(World a, BlockPos pos, BlockState state, SoulBellBlockEntity blockEntity) {
        ServerWorld world = (ServerWorld) a;
        if (blockEntity.getTicked(T_SOUL) == NULL_CLOCK) {
            blockEntity.triggerTick(T_SOUL);
            startRound(1, world, pos, blockEntity);
            blockEntity.markDirty();
        } else {
            if (world.getTime() % 40L == 0) {
               int size = blockEntity.targetList.size();
               int dies = 0;

               for (int i = 0; i < size; i++) {
                   UUID uuid = blockEntity.targetList.get(i);
                   if (world.getEntity(uuid) == null) {
                       dies++;
                   }
               }

               if (dies >= size) {
                   world.setBlockState(pos, state.with(SoulBellBlock.ON_GOING, false));
                   world.setBlockState(pos, state.with(SoulBellBlock.ROUND, 1));
                   blockEntity.setTicked(T_SOUL, NULL_CLOCK);
                   blockEntity.targetList.clear();
                   blockEntity.markDirty();
               }
            }
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        targetList.clear();
        if (nbt.contains("target_list")) {
            NbtCompound compound = nbt.getCompound("target_list");

            int i = 0;

            while (true) {
                String str = "uuid_" + i;
                if (compound.contains(str)) {
                    UUID uuid = compound.getUuid(str);
                    targetList.add(uuid);
                }else {
                    break;
                }
            }
        }

    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        NbtCompound compound = new NbtCompound();
        for (int i = 0; i < targetList.size(); i++) {
            compound.putUuid("uuid_" + i, targetList.get(i));
        }
        nbt.put("target_list", compound);
    }

}
