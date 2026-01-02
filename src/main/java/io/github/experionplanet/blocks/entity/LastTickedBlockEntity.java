package io.github.experionplanet.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class LastTickedBlockEntity extends BlockEntity {
    public static final long NULL_CLOCK = -1l;

    private List<Long> tickedList = new ArrayList<>();

    public LastTickedBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    protected void addTick() {
        tickedList.add(NULL_CLOCK);
    }

    public long getTicked(int index) {
        return tickedList.get(index);
    }

    public long getTickedAsSeconds(int index) {
        long t = getTicked(index);
        if (t == NULL_CLOCK) {
            return 0;
        }
        return world.getTime() - getTicked(index);
    }

    public void triggerTick(int index) {
        this.world.addSyncedBlockEvent(pos, this.getCachedState().getBlock(), 1, index);
    }

    public void setTicked(int index, long tick) {
        tickedList.set(index, tick);
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            tickedList.set(data, this.getWorld().getTime());
            return true;
        }
        return super.onSyncedBlockEvent(type, data);
    }
}
