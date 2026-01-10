package io.github.experionplanet.mysticalplantslg.blocks.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class ContainerBlockEntity extends BlockEntity {
    private final int slotsize;
    private static final String KEY_CONTAINER_STORED = "container_storeditems";
    private final DefaultedList<ItemStack> storedItems;
    public ContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int slotSize) {
        super(type, pos, state);
        this.storedItems = DefaultedList.ofSize(slotSize, ItemStack.EMPTY);
        this.slotsize = slotSize;
    }

    // FOR SINGLE SLOT
    public void emptyStack() {
       emptyStack(0);
    }

    public void emptyStack(int slot) {
        this.storedItems.set(slot, new ItemStack(Items.BARRIER));
        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(getCachedState()));
    }

    public void setStack(Item item) {
        setStack(new ItemStack(item));
    }

    public void setStack(ItemStack stack) {
        setStack(0, stack);
    }

    public void setStack(int slot, ItemStack stack) {
        this.storedItems.set(slot, stack);
        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(getCachedState()));
    }

    public ItemStack getCurrentStack() {
        ItemStack resStack = this.storedItems.get(0);
        if (resStack.isOf(Items.BARRIER)) {
            return ItemStack.EMPTY;
        }
        return resStack;
    }

    public List<ItemStack> getAllStack() {
        return List.copyOf(this.storedItems);
    }

    // FOR MULTIPLES
    public boolean containsItem(ItemStack stack) {
        return storedItems().contains(stack);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound compound = new NbtCompound();

        NbtCompound stored = new NbtCompound();

        Inventories.writeNbt(stored, this.storedItems, registryLookup);

        compound.put(KEY_CONTAINER_STORED, stored);

        return compound;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        if (nbt.contains(KEY_CONTAINER_STORED)) {
            Inventories.readNbt(nbt.getCompound(KEY_CONTAINER_STORED), this.storedItems, registryLookup);
        }

    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        NbtCompound stored = new NbtCompound();

        Inventories.writeNbt(stored, this.storedItems, registryLookup);

        nbt.put(KEY_CONTAINER_STORED, stored);
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public DefaultedList<ItemStack> storedItems() {
        return this.storedItems;
    }

    @Override
    protected void readComponents(BlockEntity.ComponentsAccess components) {
        super.readComponents(components);
        components.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).copyTo(this.storedItems());
    }

    @Override
    protected void addComponents(ComponentMap.Builder componentMapBuilder) {
        super.addComponents(componentMapBuilder);
        componentMapBuilder.add(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(this.storedItems()));
    }
}
