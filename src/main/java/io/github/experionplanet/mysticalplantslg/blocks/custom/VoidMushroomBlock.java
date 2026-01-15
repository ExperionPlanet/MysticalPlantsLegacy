package io.github.experionplanet.mysticalplantslg.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.mysticalplantslg.blocks.BouncingPlantBlock;
import io.github.experionplanet.mysticalplantslg.entities.SporeEntity;
import io.github.experionplanet.mysticalplantslg.init.MPLItems;
import io.github.experionplanet.mysticalplantslg.init.MPLSoundEvents;
import io.github.experionplanet.mysticalplantslg.init.MPLStatusEffects;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import static io.github.experionplanet.mysticalplantslg.init.MPLBlockProperties.CAP_REMAINING;

public class VoidMushroomBlock extends BouncingPlantBlock {
    public VoidMushroomBlock(Settings settings) {
        super(settings, 60);
        this.setDefaultState(getDefaultState().with(CAP_REMAINING, 5));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CAP_REMAINING);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(VoidMushroomBlock::new);
    }

    @Override
    protected boolean allowStepped(BlockState state, ServerWorld world, Entity entity) {
        return state.get(CAP_REMAINING) > 0;
    }

    @Override
    protected void onStepped(BlockState state, World world, BlockPos pos, LivingEntity entity) {
        int total = state.get(CAP_REMAINING) - 1;
        Vec3d v = MysticalUtils.v3dConvert(pos, true);
        SporeEntity spore = new SporeEntity(world, v.x, v.y, v.z,MysticalUtils.newId("void"));

        world.spawnEntity(spore);
        world.setBlockState(pos, state.with(CAP_REMAINING, total));
        world.playSound(null, pos, MPLSoundEvents.VOID_SWORD_EFFECT, SoundCategory.BLOCKS);
        entity.damage(entity.getDamageSources().magic(), 2f);

    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            if (stack.isOf(Items.SHEARS) && state.get(CAP_REMAINING) > 0) {

                Random rand = world.getRandom();

                Vec3d v = pos.toCenterPos();
                world.setBlockState(pos, state.with(CAP_REMAINING,state.get(CAP_REMAINING) - 1));
                bounceThePlant(world, pos);
                world.spawnEntity(new ItemEntity(world, v.getX(), v.getY(), v.getZ(), new ItemStack(MPLItems.VOID_CAP),
                        MysticalUtils.doubleInRange(rand,-0.1,0.1),
                        MysticalUtils.doubleInRange(rand,0,0.1),
                        MysticalUtils.doubleInRange(rand,-0.1,0.1)
                ));
                world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS);
                int lvl = 0;
                if (player.hasStatusEffect(MPLStatusEffects.VOID)) {
                    lvl = Math.clamp(player.getStatusEffect(MPLStatusEffects.VOID).getAmplifier() + 1, 0, 2);
                }
                player.addStatusEffect(new StatusEffectInstance(MPLStatusEffects.VOID, 80, lvl , false, true));
                player.damage(player.getDamageSources().magic(), 1f);

                if (!player.isCreative()) {
                    stack.damage(1, player, LivingEntity.getSlotForHand(hand));
                }


                return ItemActionResult.SUCCESS;
            }
        }

        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return super.canPlantOnTop(floor, world, pos) || floor.isOf(Blocks.END_STONE);
    }
}
