package io.github.experionplanet.blocks.entity.custom;

import io.github.experionplanet.blocks.custom.SoulBellBlock;
import io.github.experionplanet.blocks.entity.LastTickedBlockEntity;
import io.github.experionplanet.entities.SoulZombieEntity;
import io.github.experionplanet.init.MPLBlockEntities;
import io.github.experionplanet.init.MPLEntities;
import io.github.experionplanet.init.MPLItems;
import io.github.experionplanet.utils.ExperionLogger;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

    private static final List<CustomChance> REWARD_LIST = List.of(
            CustomChance.of(0.05f, new ItemStack(Items.DIAMOND)),
            CustomChance.of(0.1f, new ItemStack(Items.BLAZE_POWDER)),
            CustomChance.of(0.4f, new ItemStack(MPLItems.SOUL_ESSENCE)),
            CustomChance.of(0.6f, new ItemStack(Items.GLOWSTONE_DUST)),
            CustomChance.of(0.7f, new ItemStack(Items.LEATHER)),
            CustomChance.of(0.9f, new ItemStack(Items.QUARTZ)),
            CustomChance.of(1f, new ItemStack(Items.DIAMOND))
    );

    private record CustomChance(float chance, ItemStack reward) {
        public static CustomChance of(float chance, ItemStack reward) {
            return new CustomChance(chance, reward);
        }
    }

    public List<UUID> targetList = new ArrayList<>();

    public SoulBellBlockEntity(BlockPos pos, BlockState state) {
        super(MPLBlockEntities.SOUL_BELL, pos, state);
        addTick();
        addTick();
    }

    private static void startRound(int round, World world, BlockPos pos, SoulBellBlockEntity blockEntity) {
        int normal = 3;

        if (round == 1) {
            normal = 3;
        }

        for (int i = 0; i < normal; i++) {
            SoulZombieEntity soulZombieEntity = SoulZombieEntity.of(world, pos);
            spawnEntity(world, pos, soulZombieEntity, blockEntity);
        }

        blockEntity.triggerTick(0);
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
        entity.addVelocity(
                ExperionUtils.floatInRange(rand, -.5f, .5f),
                ExperionUtils.floatInRange(rand, 0f, .5f),
                ExperionUtils.floatInRange(rand, -.5f, .5f)
        );
        blockEntity.targetList.add(entity.getUuid());

        world.spawnEntity(entity);

    }

    public static void onServerTick(World a, BlockPos pos, BlockState state, SoulBellBlockEntity blockEntity) {
        ServerWorld world = (ServerWorld) a;
        Random rand = world.getRandom();
        if (blockEntity.getTicked(T_SOUL) == NULL_CLOCK && !state.get(SoulBellBlock.ON_REWARD)) {
            blockEntity.triggerTick(T_SOUL);
            startRound(state.get(SoulBellBlock.ROUND), world, pos, blockEntity);
            blockEntity.markDirty();
        } else {
            if (!state.get(SoulBellBlock.ON_REWARD)) {
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
                        blockEntity.setTicked(T_SOUL, NULL_CLOCK);
                        if (state.get(SoulBellBlock.ROUND) < 3) {
                            world.setBlockState(pos, state.with(SoulBellBlock.ROUND, state.get(SoulBellBlock.ROUND) + 1));

                        }else {
                            world.setBlockState(pos, state.with(SoulBellBlock.ON_REWARD, true).with(SoulBellBlock.ROUND, 1).with(SoulBellBlock.REWARD_COUNT, rand.nextBetween(1, 5)));
                        }

                        blockEntity.targetList.clear();
                        blockEntity.markDirty();
                    }
                }
            } else {
                if (world.getTime() % 20L == 0) {
                    float n = rand.nextFloat();
                    Vec3d v = ExperionUtils.v3dConvert(pos, true);

                    for (int i = REWARD_LIST.size() - 1; i >= 0; i--) {
                        CustomChance c = REWARD_LIST.get(i);

                        if (c.chance <= n) {
                            ItemEntity itemEntity = new ItemEntity(world, v.getX(), v.getY(), v.getZ(), c.reward.copy());
                            itemEntity.addVelocity(
                                    ExperionUtils.floatInRange(rand, -.1f, .1f),
                                    ExperionUtils.floatInRange(rand, 0f, .3f),
                                    ExperionUtils.floatInRange(rand, -.1f, .1f)
                            );
                            world.spawnEntity(itemEntity);
                            break;
                        }
                    }

                    blockEntity.triggerTick(0);

                    world.setBlockState(pos, state.with(SoulBellBlock.REWARD_COUNT, state.get(SoulBellBlock.REWARD_COUNT) - 1));

                    if (world.getBlockState(pos).get(SoulBellBlock.REWARD_COUNT) == 0) {
                        world.setBlockState(pos, world.getBlockState(pos).with(SoulBellBlock.ON_REWARD, false).with(SoulBellBlock.ON_GOING, false));
                    }
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
                i++;
            }
        }

        if (nbt.contains("round_tick")) {
            setTicked(T_SOUL, nbt.getLong("round_tick"));
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
        nbt.putLong("round_tick", getTicked(T_SOUL));
    }

}
