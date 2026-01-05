package io.github.experionplanet.blocks.entity.custom;

import io.github.experionplanet.init.MPLBlockEntities;
import io.github.experionplanet.init.MPLParticles;
import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ExpMushroomBlockEntity extends BlockEntity {
    public int STEP_STATUS = 0;
    public long LAST_TICK = 0;

    public ExpMushroomBlockEntity(BlockPos pos, BlockState state) {
        super(MPLBlockEntities.EXP_MUSHROOMS, pos, state);
    }

    public void Stepped(ServerWorld serverWorld, BlockPos pos) {
        this.STEP_STATUS = 1;
        Vec3d vec = MysticalUtils.v3dConvert(pos, true).add(0, -0.5, 0);
        serverWorld.spawnParticles(MPLParticles.EXP_PIECES, vec.getX(), vec.getY(), vec.getZ(), 5, 0.1d, 0.1d, 0.1d, 1);
        this.world.addSyncedBlockEvent(pos, this.getCachedState().getBlock(), 1, this.STEP_STATUS);

    }

    public void Unstepped(ServerWorld serverWorld) {
        this.STEP_STATUS = 2;
        this.world.addSyncedBlockEvent(pos, this.getCachedState().getBlock(), 1, this.STEP_STATUS);
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            this.STEP_STATUS = data;
            this.LAST_TICK = this.getWorld().getTime();
            return true;
        }
        return super.onSyncedBlockEvent(type, data);
    }

}
