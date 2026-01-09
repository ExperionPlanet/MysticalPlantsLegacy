package io.github.experionplanet.mysticalplantslg.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.mysticalplantslg.blocks.BouncingPlantBlock;
import io.github.experionplanet.mysticalplantslg.entities.SporeEntity;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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
    protected void onStepped(BlockState state, World world, BlockPos pos, LivingEntity entity) {
        int currentCap = state.get(CAP_REMAINING);

        Vec3d v = MysticalUtils.v3dConvert(pos, true);

        SporeEntity spore = SporeEntity.createSpore(world, v.x, v.y, v.z,"bog");

        world.spawnEntity(spore);


        world.setBlockState(pos, state.with( CAP_REMAINING,currentCap - 1));
    }
}
