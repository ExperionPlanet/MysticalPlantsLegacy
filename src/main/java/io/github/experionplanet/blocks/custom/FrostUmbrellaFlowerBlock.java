package io.github.experionplanet.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.blocks.BouncingPlantBlock;
import io.github.experionplanet.init.MPLSoundEvents;
import io.github.experionplanet.utils.MysticalUtils;
import io.github.experionplanet.utils.SnowableBlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import static io.github.experionplanet.init.MPLBlockProperties.SNOW;

public class FrostUmbrellaFlowerBlock extends BouncingPlantBlock {
    public FrostUmbrellaFlowerBlock(Settings settings) {
        super(settings, 20);
        setDefaultState(getDefaultState().with(SNOW, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SNOW);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(FrostUmbrellaFlowerBlock::new);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return SnowableBlockUtils.getPlacementState(ctx, super.getPlacementState(ctx));
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        SnowableBlockUtils.neighborUpdate(state, world, pos);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SnowableBlockUtils.getVoxelShape(state, super.getOutlineShape(state, world, pos, context));
    }

    @Override
    protected void onStepped(BlockState state, World world, BlockPos pos, LivingEntity entity) {
        double push = MysticalUtils.doubleInRange(world.getRandom(),0.1, 0.4);

        entity.addVelocity((entity.getX( ) - pos.getX() - 0.5) * push, (entity.getY( ) - pos.getY() - 0.5) * push, (entity.getZ() - pos.getZ() - 0.5) * push);
        entity.velocityModified = true;

        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 80, 1, false, false));

        world.playSound(null, pos, MPLSoundEvents.FROST_UMBRELLA_FLOWER_BOINK, SoundCategory.BLOCKS);

    }
}
