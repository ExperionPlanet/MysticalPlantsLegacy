package io.github.experionplanet.mysticalplantslg.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.mysticalplantslg.blocks.BouncingPlantBlock;
import io.github.experionplanet.mysticalplantslg.entities.SporeEntity;
import io.github.experionplanet.mysticalplantslg.init.MPLItems;
import io.github.experionplanet.mysticalplantslg.init.MPLSoundEvents;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.github.experionplanet.mysticalplantslg.init.MPLBlockProperties.CAP_REMAINING;

public class BogsporeCapBlock extends BouncingPlantBlock {
    public BogsporeCapBlock(Settings settings) {
        super(settings, 20);
        setDefaultState(getDefaultState().with(CAP_REMAINING, 3));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CAP_REMAINING);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(BogsporeCapBlock::new);
    }

    @Override
    protected boolean allowStepped(BlockState state, ServerWorld world, Entity entity) {
        return state.get(CAP_REMAINING) > 0;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(CAP_REMAINING, 0);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return state.get(CAP_REMAINING) < 3;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextFloat() <= 0.3f) {
            world.setBlockState(pos, state.with(CAP_REMAINING,state.get(CAP_REMAINING) + 1));
        }
    }

    protected void onSpore(World world, BlockPos pos, BlockState state) {
        int currentCap = state.get(CAP_REMAINING);

        Vec3d v = MysticalUtils.v3dConvert(pos, true);

        SporeEntity spore = new SporeEntity(world, v.getX(), v.getY(), v.getZ(), MysticalUtils.newId("bog"));
        world.spawnEntity(spore);
        world.playSound(null, pos, MPLSoundEvents.SPORE, SoundCategory.BLOCKS);
        world.setBlockState(pos, state.with( CAP_REMAINING,currentCap - 1));
    }

    @Override
    protected void onStepped(BlockState state, World world, BlockPos pos, LivingEntity entity) {
        onSpore(world, pos, state);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            if (stack.isOf(Items.SHEARS) && state.get(CAP_REMAINING) > 0) {

                Random rand = world.getRandom();
                if (rand.nextBoolean()) {
                    onSpore(world,  pos, state);
                    bounceThePlant(world, pos);
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 40, 1));
                }else {
                    Vec3d v = pos.toCenterPos();
                    world.setBlockState(pos, state.with(CAP_REMAINING,state.get(CAP_REMAINING) - 1));
                    bounceThePlant(world, pos);
                    world.spawnEntity(new ItemEntity(world, v.getX(), v.getY(), v.getZ(), new ItemStack(MPLItems.BOG_CAP),
                            MysticalUtils.doubleInRange(rand,-0.1,0.1),
                            MysticalUtils.doubleInRange(rand,0,0.1),
                            MysticalUtils.doubleInRange(rand,-0.1,0.1)
                    ));
                    world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS);
                }

                if (!player.isCreative()) {
                    stack.damage(1, player, LivingEntity.getSlotForHand(hand));
                }


                return ItemActionResult.SUCCESS;
            }
        }

        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}
