package io.github.experionplanet.blocks.entity.custom;

import com.google.common.collect.ImmutableList;
import io.github.experionplanet.blocks.custom.BindingRockBlock;
import io.github.experionplanet.blocks.entity.ContainerBlockEntity;
import io.github.experionplanet.init.MPLBlockEntities;
import io.github.experionplanet.init.MPLRecipes;
import io.github.experionplanet.recipe.MysticalPedestalRecipe;
import io.github.experionplanet.recipe.PedestalRecipeInput;
import io.github.experionplanet.utils.ExperionLogger;
import io.github.experionplanet.utils.MysticalNbt;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BindingRockBlockEntity extends ContainerBlockEntity {


    public BindingRockBlockEntity(BlockPos pos, BlockState state) {
        super(MPLBlockEntities.BINDING_ROCK, pos, state, 1);

    }

    public Optional<RecipeEntry<MysticalPedestalRecipe>> getCurrentRecipe(List<ItemStack> list, ItemStack lastStack) {
        return this.getWorld().getRecipeManager().getFirstMatch(MPLRecipes.MYSTICAL_PEDESTAL_TYPE, new PedestalRecipeInput(lastStack, list, getCurrentStack()), this.getWorld());
    }

    // SOON

    private static final String KEY_STAGE = "stage";
    private static final String KEY_STARTED = "started";
    private static final String KEY_CRAFTED_STACK = "crafted_stack";

    public static final List<Integer> STAGE_TICK = ImmutableList.of(
            60,
            120
    );

    private static final int MAX_STAGE = 2;

    public final MysticalNbt dat = new MysticalNbt()
            .propInt("stage", 0)
            .propBoolean("started", false)
            .build();

    public ItemStack craftedStack = ItemStack.EMPTY;


    public int tickProgress = 0;
    public long currClock = 0l;
    public int maxTick = 0;
    public boolean fullySetup = false;
    public List<PedestalBlockEntity> pedestals = new ArrayList<>();

    private void setupStage(int stage) {
        if (!fullySetup) {
            for (BlockPos v : BindingRockBlock.PEDESTAL_POS_LIST) {
                BlockEntity blockEntity = world.getBlockEntity(v);

                if (blockEntity instanceof PedestalBlockEntity) {
                    pedestals.add((PedestalBlockEntity) blockEntity);
                }
            }

            this.fullySetup = true;
        }
        tickProgress = 0;

        this.maxTick = STAGE_TICK.get(stage - 1);

        if (world.isClient()) {
            this.currClock = world.getTime();
        }

        this.dat.setInt(KEY_STAGE, stage);

        ExperionLogger.Print("Stage " + stage);

        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(getCachedState()));
    }

    public void startCrafting(ItemStack resultStack) {
        if (!this.dat.getBoolean(KEY_STARTED)) {
            setupStage(1);

            this.craftedStack = resultStack.copy();

            dat.setBoolean(KEY_STARTED, true);
        }
    }

    public static void onTick(World world, BlockPos pos, BlockState state, BindingRockBlockEntity blockEntity) {
        if (blockEntity.dat.getBoolean(KEY_STARTED)) {
            if (!blockEntity.fullySetup) {
                blockEntity.setupStage(blockEntity.dat.getInt(KEY_STAGE));
            }
            ExperionLogger.Print("t: " + blockEntity.tickProgress + " CLIENT? " + world.isClient());
            blockEntity.tickProgress++;

            if (blockEntity.tickProgress >= blockEntity.maxTick) {
                if (blockEntity.dat.getInt(KEY_STAGE) + 1 > MAX_STAGE) {
                    blockEntity.dat.setBoolean(KEY_STARTED, false);

                    ExperionLogger.Print("FINISHED!");
                } else {
                    blockEntity.setupStage(blockEntity.dat.getInt(KEY_STAGE) + 1);
                }

            }

        }
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound nbt = super.toInitialChunkDataNbt(registryLookup);
        dat.writeNbt(nbt, registryLookup);
        if (!this.craftedStack.isEmpty()) {
            nbt.put(KEY_CRAFTED_STACK,this.craftedStack.encode(registryLookup));
        }
        return nbt;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        dat.writeNbt(nbt, registryLookup);
        if (!this.craftedStack.isEmpty()) {
            nbt.put(KEY_CRAFTED_STACK,this.craftedStack.encode(registryLookup));
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        dat.readNbt(nbt, registryLookup);
        if (nbt.contains(KEY_CRAFTED_STACK)) {
            this.craftedStack = ItemStack.fromNbtOrEmpty(registryLookup, nbt.getCompound(KEY_CRAFTED_STACK));
        }
    }
}
