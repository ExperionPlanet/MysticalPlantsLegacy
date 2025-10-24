package io.github.experionplanet.blocks.entity.custom;

import io.github.experionplanet.blocks.entity.ContainerBlockEntity;
import io.github.experionplanet.init.MPLBlockEntities;
import io.github.experionplanet.utils.ExperionLogger;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PermafrostLogBlockEntity extends ContainerBlockEntity {
    private long currClock = -1l;
    public int ticking = 0;
    public int maxTick = 1;
    public Entity breakingEntity;

    public PermafrostLogBlockEntity(BlockPos pos, BlockState state) {
        super(MPLBlockEntities.PERMAFROST_LOG, pos, state, 1);
    }

    public void startDefrost(World world, Entity breakingEntity) {
        this.maxTick = world.getRandom().nextBetween(10, 40);
        this.breakingEntity = breakingEntity;
        this.world.addSyncedBlockEvent(pos, this.getCachedState().getBlock(), 1, 0);
    }

    public static void onTick(World world, BlockPos pos, BlockState state, PermafrostLogBlockEntity entity) {
        entity.ticking++;

        if (entity.ticking >= entity.maxTick) {
            world.breakBlock(pos, false, entity.breakingEntity);

            Vec3d vec = ExperionUtils.v3dConvert(pos, true);

            world.spawnEntity(new ItemEntity(world, vec.x, vec.y, vec.z, entity.getCurrentStack().copy()));
        }
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            this.currClock = this.getWorld().getTime();
            return true;
        }
        return super.onSyncedBlockEvent(type, data);
    }


}
