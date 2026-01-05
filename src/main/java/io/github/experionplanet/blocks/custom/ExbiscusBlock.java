package io.github.experionplanet.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.blocks.BloomingFlowerBlock;
import io.github.experionplanet.blocks.entity.custom.BloomingFlowerBlockEntity;
import io.github.experionplanet.init.MPLItems;
import io.github.experionplanet.init.MPLSoundEvents;
import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class ExbiscusBlock extends BloomingFlowerBlock {
    public ExbiscusBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(ExbiscusBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BloomingFlowerBlockEntity(pos,state);
    }

    @Override
    protected void onHarvest(BlockState state, ServerWorld world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Random rand =  world.getRandom();
        Vec3d vec = MysticalUtils.v3dConvert(pos, true);
        if (rand.nextBetween(1, 15) <= 1) {
            world.spawnEntity(new ItemEntity(world, vec.getX(), vec.y, vec.z, new ItemStack(MPLItems.EXPERIENCE_ESSENCE, 1)));
            world.playSound(null, pos, MPLSoundEvents.EXBISCUS_BLOOMING_PICK_ESSENCE, SoundCategory.BLOCKS);
        }else {
            ExperienceOrbEntity.spawn((ServerWorld) world, vec, rand.nextBetween(2, 5));
            world.playSound(null, pos, MPLSoundEvents.EXBISCUS_BLOOMING_PICK, SoundCategory.BLOCKS);
        }
    }
}
