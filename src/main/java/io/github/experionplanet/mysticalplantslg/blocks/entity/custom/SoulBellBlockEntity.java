package io.github.experionplanet.mysticalplantslg.blocks.entity.custom;

import io.github.experionplanet.mysticalplantslg.blocks.custom.SoulBellBlock;
import io.github.experionplanet.mysticalplantslg.blocks.entity.LastTickedBlockEntity;
import io.github.experionplanet.mysticalplantslg.entities.SoulZombieEntity;
import io.github.experionplanet.mysticalplantslg.init.*;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SoulBellBlockEntity extends LastTickedBlockEntity {
    public static final int T_SOUL = 1;

    private List<ItemStack> rewardStacks = new ArrayList<>();

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


        Vec3d v = MysticalUtils.v3dConvert(pos, true);

        entity.refreshPositionAndAngles(v.getX(), v.getY(), v.getZ(), rand.nextFloat() * 360F, 0);
        entity.addVelocity(
                MysticalUtils.floatInRange(rand, -.5f, .5f),
                MysticalUtils.floatInRange(rand, 0f, .5f),
                MysticalUtils.floatInRange(rand, -.5f, .5f)
        );
        blockEntity.targetList.add(entity.getUuid());

        world.spawnEntity(entity);

    }

    public static void onServerTick(World a, BlockPos pos, BlockState state, SoulBellBlockEntity blockEntity) {
        ServerWorld world = (ServerWorld) a;
        Random rand = world.getRandom();
        Vec3d v = pos.toCenterPos();
        if (blockEntity.getTicked(T_SOUL) == NULL_CLOCK && !state.get(SoulBellBlock.ON_REWARD)) {
            if (world.getDifficulty() != Difficulty.PEACEFUL) {
                blockEntity.triggerTick(T_SOUL);

                int currRound = state.get(SoulBellBlock.ROUND);

                world.spawnParticles(MPLParticles.SOUL_BELL_BLASTWAVE, v.getX(), v.getY(), v.getZ(), 1, 0,0,0,0);
                world.spawnParticles(MPLParticles.SOUL_DUST, v.getX(), v.getY(), v.getZ(), 8, 0,0,0,0);
                world.spawnParticles(ParticleTypes.SCULK_SOUL, v.getX(), v.getY(), v.getZ(), 10, 1,1,1,0.1);
                if (currRound == 2) {
                    world.playSound(null, pos, MPLSoundEvents.SOUL_BELL_STAGE_2, SoundCategory.BLOCKS);
                } else if (currRound == 3) {
                    world.playSound(null, pos, MPLSoundEvents.SOUL_BELL_STAGE_3, SoundCategory.BLOCKS);
                } else {
                    world.playSound(null, pos, MPLSoundEvents.SOUL_BELL_STAGE_1, SoundCategory.BLOCKS);
                }

                startRound(currRound, world, pos, blockEntity);

            } else {
                world.setBlockState(pos, state.with(SoulBellBlock.ROUND, 1).with(SoulBellBlock.ON_GOING, false).with(SoulBellBlock.REWARD_COUNT, 0).with(SoulBellBlock.ON_REWARD, false));
            }
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
                        int currRound = state.get(SoulBellBlock.ROUND);
                        if (currRound < 3) {
                            world.setBlockState(pos, state.with(SoulBellBlock.ROUND, state.get(SoulBellBlock.ROUND) + 1));

                        }else {
                            if (world.getDifficulty() != Difficulty.PEACEFUL) {
                                LootTable loot = world.getServer().getReloadableRegistries().getLootTable(MPLLootables.SOUL_BELL_LOOT);
                                LootContextParameterSet set = new LootContextParameterSet.Builder(world).add(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos)).build(LootContextTypes.CHEST);
                                blockEntity.rewardStacks.clear();
                                blockEntity.rewardStacks = loot.generateLoot(set, world.getRandom());

                                world.setBlockState(pos, state.with(SoulBellBlock.ON_REWARD, true).with(SoulBellBlock.ROUND, 1).with(SoulBellBlock.REWARD_COUNT, blockEntity.rewardStacks.size()));
                            }else {
                                world.playSound(null, pos, SoundEvents.ENTITY_GHAST_DEATH, SoundCategory.BLOCKS);
                                world.spawnParticles(ParticleTypes.SCULK_SOUL, v.getX(), v.getY(), v.getZ(), 12, 0,0,0,0.2);
                                world.setBlockState(pos, state.with(SoulBellBlock.ON_REWARD, false).with(SoulBellBlock.REWARD_COUNT, 0).with(SoulBellBlock.ON_GOING, false).with(SoulBellBlock.ROUND, 1));
                            }

                        }

                        blockEntity.targetList.clear();
                        blockEntity.markDirty();
                    }
                }
            } else {
                if (world.getTime() % 20L == 0) {
                    ItemStack reward = blockEntity.rewardStacks.get(state.get(SoulBellBlock.REWARD_COUNT) - 1);

                    ItemEntity itemEntity = new ItemEntity(world, v.getX(), v.getY(), v.getZ(), reward.copy());
                    itemEntity.addVelocity(
                            MysticalUtils.floatInRange(rand, -.1f, .1f),
                            MysticalUtils.floatInRange(rand, 0f, .3f),
                            MysticalUtils.floatInRange(rand, -.1f, .1f)
                    );
                    world.spawnEntity(itemEntity);

                    blockEntity.triggerTick(0);

                    world.setBlockState(pos, state.with(SoulBellBlock.REWARD_COUNT, state.get(SoulBellBlock.REWARD_COUNT) - 1));
                    world.playSound(null, pos, SoundEvents.BLOCK_TRIAL_SPAWNER_SPAWN_ITEM, SoundCategory.BLOCKS);

                    if (world.getBlockState(pos).get(SoulBellBlock.REWARD_COUNT) == 0) {
                        world.setBlockState(pos, world.getBlockState(pos).with(SoulBellBlock.ON_REWARD, false).with(SoulBellBlock.ON_GOING, false));
                        blockEntity.rewardStacks.clear();
                        blockEntity.markDirty();
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

        if (nbt.contains("reward_stacks")) {
            NbtCompound comp = nbt.getCompound("reward_stacks");
            rewardStacks.clear();
            for (int i = 0; i < 5; i++) {
                if (comp.contains("i_" + i)) {
                    rewardStacks.add(ItemStack.fromNbtOrEmpty(registryLookup, comp.getCompound("i_" + i)));
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
        nbt.putLong("round_tick", getTicked(T_SOUL));

        if (!rewardStacks.isEmpty()) {
            NbtCompound rewardComp = new NbtCompound();
            for (int i = 0; i < rewardStacks.size(); i++) {
                rewardComp.put("i_" + i, rewardStacks.get(i).encode(registryLookup));
            }
            nbt.put("reward_stacks", rewardComp);
        }
    }

}
