package io.github.experionplanet.blocks.entity.custom;

import io.github.experionplanet.blocks.entity.LastTickedBlockEntity;
import io.github.experionplanet.init.MPLBlockEntities;
import io.github.experionplanet.init.MPLItems;
import io.github.experionplanet.init.MPLStatusEffects;
import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static io.github.experionplanet.blocks.custom.VoidStrawflowerBlock.IS_TRAPPED;

public class VoidStrawflowerBlockEntity extends LastTickedBlockEntity {
    private LivingEntity victim = null;

    public static final long MAX_TIME_TRAPPED = 120L;
    public static final int T_STEPPED = 0;
    public static final int T_TRAPPED = 1;

    public VoidStrawflowerBlockEntity(BlockPos pos, BlockState state) {
        super(MPLBlockEntities.VOID_STRAWFLOWER, pos, state);
        addTick();
        addTick();
    }

    public void trapEntity(LivingEntity entity, BlockPos pos, BlockState state, ServerWorld serverWorld) {
        if (!state.get(IS_TRAPPED)) {

            Vec3d vec = MysticalUtils.v3dConvert(pos, true);

            triggerTick(T_TRAPPED);
            world.setBlockState(pos,state.with(IS_TRAPPED, true));

            entity.teleport(vec.getX(), entity.getY(), vec.getZ(), true);

            victim = entity;

            entity.addStatusEffect(new StatusEffectInstance(MPLStatusEffects.ROOTED, 200, 0, false, false));


        }
    }

    public static void onServerTick(World world, BlockPos pos, BlockState state, VoidStrawflowerBlockEntity blockEntity) {
        if (state.get(IS_TRAPPED)) {
            if (blockEntity.victim == null) {
                world.setBlockState(pos, state.with(IS_TRAPPED, false));
                return;
            }

            Vec3d vec = MysticalUtils.v3dConvert(pos, true);

            blockEntity.victim.teleport(vec.getX(), blockEntity.victim.getY(), vec.getZ(), false);

            if (blockEntity.getTickedAsSeconds(T_TRAPPED) >= MAX_TIME_TRAPPED) {
                blockEntity.victim.removeStatusEffect(MPLStatusEffects.ROOTED);
                world.breakBlock(pos, false, blockEntity.victim);

                if (world.getRandom().nextBetween(1, 10) <= 1) {


                    world.spawnEntity(new ItemEntity(world, vec.getX(), vec.getY(), vec.getZ(), new ItemStack(MPLItems.VOID_ESSENCE)));
                }
            }
        }
    }


}
