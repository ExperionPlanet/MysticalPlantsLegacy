package io.github.experionplanet.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.blocks.BouncingPlantBlock;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FrostUmbrellaFlowerBlock extends BouncingPlantBlock {
    public FrostUmbrellaFlowerBlock(Settings settings) {
        super(settings, 20);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(FrostUmbrellaFlowerBlock::new);
    }

    @Override
    protected void onStepped(BlockState state, World world, BlockPos pos, LivingEntity entity) {
        double push = ExperionUtils.doubleInRange(world.getRandom(),0.9, 1.2);

        entity.addVelocity((entity.getX( ) - pos.getX() - 0.5) * push, (entity.getY( ) - pos.getY() - 0.5) * push, (entity.getZ() - pos.getZ() - 0.5) * push);
        entity.velocityModified = true;

        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 80, 1, false, false));

    }
}
