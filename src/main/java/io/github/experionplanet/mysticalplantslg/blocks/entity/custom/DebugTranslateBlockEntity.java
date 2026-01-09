package io.github.experionplanet.mysticalplantslg.blocks.entity.custom;

import io.github.experionplanet.mysticalplantslg.init.MPLBlockEntities;
import io.github.experionplanet.mysticalplantslg.utils.ExperionLogger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class DebugTranslateBlockEntity extends BlockEntity  {
    private final DefaultedList<ItemStack> showedItem = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public double tx = 0;
    public double ty = 0;
    public double tz = 0;
    public float rx = 0;
    public float ry = 0;
    public float rz = 0;
    public int modeAddings = 1;

    public DebugTranslateBlockEntity(BlockPos pos, BlockState state) {
        super(MPLBlockEntities.DEBUG_TRANSLATE,pos, state);
    }

    public void setStack(ItemStack stack) {
        this.showedItem.set(0, stack);
        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    public void setValues(int Type) {
        double addings = 0.05;
        float faddings = 1f;
        if (this.modeAddings == 2) {
            addings = -0.05;
            faddings = -1f;
        }

        if (Type == 1) {
            this.tx += addings;
        }

        if (Type == 2) {
            this.ty += addings;
        }

        if (Type == 3) {
            this.tz += addings;
        }

        if (Type == 4) {
            this.rx += faddings;
        }

        if (Type == 5) {
            this.ry += faddings;
        }

        if (Type == 6) {
            this.rz += faddings;
        }

        ExperionLogger.Print("x" + tx + " y" + ty + " z" + tz + " rx" + rx + " ry" + ry + " rz" + rz);

        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);

    }

    public void setMode(int mode) {
        this.modeAddings = mode;
        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    public ItemStack getCurrentStack() {
        return this.showedItem.get(0);
    }


    private void sync() {
        BlockEntity bE = world.getBlockEntity(pos);
        if (bE != null) {
            world.getServer().getPlayerManager().sendToAround(
                    null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    64, world.getRegistryKey(), BlockEntityUpdateS2CPacket.create(bE, BlockEntity::createNbt)
            );
        }

    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound nbt = new NbtCompound();
        Inventories.writeNbt(nbt, this.showedItem, registryLookup);
        nbt.putDouble("ppos_x", this.tx);
        nbt.putDouble("ppos_y", this.ty);
        nbt.putDouble("ppos_z", this.tz);
        nbt.putFloat("prot_x", this.rx);
        nbt.putFloat("prot_y", this.ry);
        nbt.putFloat("prot_z", this.rz);
        nbt.putInt("mode_addings", this.modeAddings);

        return nbt;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, this.showedItem, registryLookup);
        this.tx = nbt.getDouble("ppos_x");
        this.ty = nbt.getDouble("ppos_y");
        this.tz = nbt.getDouble("ppos_z");
        this.rx = nbt.getFloat("prot_x");
        this.ry = nbt.getFloat("prot_y");
        this.rz = nbt.getFloat("prot_z");
        this.modeAddings = nbt.getInt("mode_addings");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, this.showedItem, registryLookup);
        nbt.putDouble("ppos_x", this.tx);
        nbt.putDouble("ppos_y", this.ty);
        nbt.putDouble("ppos_z", this.tz);
        nbt.putFloat("prot_x", this.rx);
        nbt.putFloat("prot_y", this.ry);
        nbt.putFloat("prot_z", this.rz);
        nbt.putInt("mode_addings", this.modeAddings);

    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public DefaultedList<ItemStack> showedItem() {
        return this.showedItem;
    }

    @Override
    protected void readComponents(BlockEntity.ComponentsAccess components) {
        super.readComponents(components);
        components.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).copyTo(this.showedItem());
    }

    @Override
    protected void addComponents(ComponentMap.Builder componentMapBuilder) {
        super.addComponents(componentMapBuilder);
        componentMapBuilder.add(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(this.showedItem()));
    }

}
