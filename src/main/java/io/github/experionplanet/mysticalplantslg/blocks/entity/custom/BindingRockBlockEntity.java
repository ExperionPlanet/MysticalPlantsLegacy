package io.github.experionplanet.mysticalplantslg.blocks.entity.custom;

import io.github.experionplanet.mysticalplantslg.blocks.custom.BindingRockBlock;
import io.github.experionplanet.mysticalplantslg.blocks.entity.ContainerBlockEntity;
import io.github.experionplanet.mysticalplantslg.init.*;
import io.github.experionplanet.mysticalplantslg.recipe.MysticalPedestalRecipe;
import io.github.experionplanet.mysticalplantslg.recipe.PedestalRecipeInput;
import io.github.experionplanet.mysticalplantslg.utils.ExperionLogger;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import io.github.experionplanet.mysticalplantslg.utils.MysticalNbt;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;

import static io.github.experionplanet.mysticalplantslg.init.MPLBlockProperties.ON_CRAFTING;

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

    private static final String KEY_CURRCLOCK = "curr_clock";
    private static final String KEY_ON_CRAFTING = "on_crafting";

    public final MysticalNbt dat = new MysticalNbt()
            .propLong("curr_clock", -1L)
            .propBoolean("on_crafting", false)
            .build();

    public ItemStack craftedStack = ItemStack.EMPTY;
    public ItemStack ingredientFINAL = ItemStack.EMPTY;
    public DefaultedList<ItemStack> ingredientList = DefaultedList.ofSize(8, ItemStack.EMPTY);
    public List<Integer> ingredientConsumed = new ArrayList<>();
    public int pedestalIndex = 0;
    public List<BlockPos> pedestalsPos = new ArrayList<>();

    public int triggeredIndex = -1;

    public void initializeCrafting(List<PedestalBlockEntity> blockEntities, List<ItemStack> list, ItemStack finalIngredient, ItemStack result, World world, BlockPos center) {
        if (!dat.getBoolean(KEY_ON_CRAFTING)) {
            pedestalsPos.clear();

            for (int index = 0; index < blockEntities.size(); index++) {
                PedestalBlockEntity pedestal = blockEntities.get(index);

                if (!pedestal.getCurrentStack().isEmpty()) {
                    pedestalsPos.add(pedestal.getPos());
                }
            }

            craftedStack = result;
            ingredientFINAL = finalIngredient;

            clearIngredientList();
            for (int i = 0; i < list.size(); i++) {
                ingredientList.set(i, list.get(i));
            }
            pedestalIndex = 0;

            dat.setBoolean(KEY_ON_CRAFTING, true);
            dat.setLong(KEY_CURRCLOCK, world.getTime());

            ExperionLogger.Print("INITIALZIED!");

            markDirty();
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(getCachedState()));
        }

    }

    public void endCrafting() {
        pedestalsPos.clear();
        clearIngredientList();
        craftedStack = ItemStack.EMPTY;
        ingredientFINAL = ItemStack.EMPTY;

        dat.setBoolean(KEY_ON_CRAFTING, false);
        dat.setLong(KEY_CURRCLOCK, -1L);

        pedestalIndex = 0;
        ingredientConsumed.clear();

        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(getCachedState()));
    }

    private void clearIngredientList() {
        for (int i = 0; i < ingredientList.size(); i++) {
            ingredientList.set(i, ItemStack.EMPTY);
        }
    }

    public static void onTickServer(World world, BlockPos pos, BlockState state, BindingRockBlockEntity blockEntity) {
        ServerWorld serverWorld = (ServerWorld) world;

        long clockNow = world.getTime();

        if (clockNow - blockEntity.dat.getLong(KEY_CURRCLOCK) >= 20L && state.get(BindingRockBlock.INITIALIZED)) {
            blockEntity.dat.setLong(KEY_CURRCLOCK, clockNow);

            int index = blockEntity.pedestalIndex;


            boolean succ = false;
            Vec3d v = MysticalUtils.v3dConvert(pos, true);
            if (index >= 0 && index < blockEntity.pedestalsPos.size()) {
                BlockPos pPos = blockEntity.pedestalsPos.get(index);
                ItemStack ingredient = blockEntity.ingredientList.get(index);

                if (world.getBlockEntity(pPos) instanceof PedestalBlockEntity pedestal) {
                    if (pedestal.containsItem(ingredient)) {
                        blockEntity.ingredientConsumed.add(index);
                        pedestal.emptyStack();
                        world.setBlockState(pPos, pedestal.getCachedState().with(ON_CRAFTING, false));
                        serverWorld.spawnParticles(MPLParticles.ENDER_WARP, v.getX(), v.getY(), v.getZ(), 1, 0, 0, 0, 0);
                        blockEntity.pedestalIndex++;



                        succ = true;
                    }
                }
            } else {
                ItemEntity itemEntity = new ItemEntity(world, v.getX(), v.getY() + 0.5d, v.getZ(), blockEntity.craftedStack);
                itemEntity.addVelocity(0, 0.1d, 0);
                world.spawnEntity(itemEntity);
                blockEntity.emptyStack();
                blockEntity.endCrafting();
                world.setBlockState(pos, state.with(ON_CRAFTING, false));
                return;
            }


            if (!succ) {
                for (int i : blockEntity.ingredientConsumed) {
                    ItemStack stack = blockEntity.ingredientList.get(i);
                    ItemEntity entity = new ItemEntity(world, v.getX(), v.getY(), v.getZ(), stack);
                    entity.setVelocity(world.random.nextTriangular((double)0.0F, 0.11485000171139836), world.random.nextTriangular(0.2, 0.11485000171139836), world.random.nextTriangular((double)0.0F, 0.11485000171139836));
                    world.spawnEntity(entity);
                }
                
                blockEntity.endCrafting();
                world.setBlockState(pos, state.with(ON_CRAFTING, false));
            } else {
                blockEntity.markDirty();
                world.updateListeners(pos, blockEntity.getCachedState(), blockEntity.getCachedState(), Block.NOTIFY_ALL);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockEntity.getCachedState()));
            }


        }
    }

    public static void onInitialize(World world, BlockPos pos, BlockState state, BindingRockBlockEntity blockEntity) {
        ItemStack resStack = ItemStack.EMPTY;
        RegistryEntry<Biome> biome = world.getBiome(pos);

        if (biome.isIn(MPLBiomeTags.EXPERIENCE_PICKAXE_SPAWNABLE)) {
            resStack = new ItemStack(MPLItems.BROKEN_EXPERIENCE_PICKAXE);
        }

        if (!resStack.isEmpty()) {
            blockEntity.setStack(resStack);
        }

        world.setBlockState(pos, state.with(BindingRockBlock.INITIALIZED, true));
    }

    private void doWriteNbt(NbtCompound nbt,RegistryWrapper.WrapperLookup registryLookup) {
        dat.writeNbt(nbt, registryLookup);
        if (!craftedStack.isEmpty()) {
            nbt.put("result_stack", craftedStack.encode(registryLookup));
        }

        if (!ingredientFINAL.isEmpty()) {
            nbt.put("ingredient_final", ingredientFINAL.encode(registryLookup));
        }

        if (!ingredientConsumed.isEmpty()) {
            nbt.putIntArray("consumed", ingredientConsumed);
        }

        if (!pedestalsPos.isEmpty()) {
            NbtCompound c = new NbtCompound();
            int total = 0;
            for (int i = 0; i < pedestalsPos.size(); i++) {
                BlockPos p = pedestalsPos.get(i);
                NbtCompound pComp = new NbtCompound();
                pComp.putInt("x", p.getX());
                pComp.putInt("y", p.getY());
                pComp.putInt("z", p.getZ());

                c.put("i_" + (i + 1), pComp);
                total++;
            }

            c.putInt("t", + total);
            nbt.put("pedestal_pos", c);
        }

        nbt.putInt("pedestal_index", pedestalIndex);

        NbtCompound ingList = new NbtCompound();

        Inventories.writeNbt(ingList, ingredientList, registryLookup);

        nbt.put("ingredient_list", ingList);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompound c = super.toInitialChunkDataNbt(registryLookup);

        doWriteNbt(c, registryLookup);

        return c;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        doWriteNbt(nbt, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        dat.readNbt(nbt, registryLookup);
        if (nbt.contains("result_stack")) {
            this.craftedStack = ItemStack.fromNbtOrEmpty(registryLookup, nbt.getCompound("result_stack"));
        }
        if (nbt.contains("ingredient_final")) {
            this.ingredientFINAL = ItemStack.fromNbtOrEmpty(registryLookup, nbt.getCompound("ingredient_final"));
        }
        if (nbt.contains("consumed")) {
            this.ingredientConsumed.clear();
            for (int a : nbt.getIntArray("consumed")) {
                this.ingredientConsumed.add(a);
            }
        }
        if (nbt.contains("pedestal_index")) {
            this.pedestalIndex = nbt.getInt("pedestal_index");
        }
        if (nbt.contains("pedestal_pos")) {
            this.pedestalsPos.clear();
            NbtCompound c = nbt.getCompound("pedestal_pos");
            for (int i = 1; i <= c.getInt("t"); i++) {
                NbtCompound pComp = nbt.getCompound("i_" + i);
                pedestalsPos.add(new BlockPos(pComp.getInt("x"),pComp.getInt("y"),pComp.getInt("z")));
            }
        }
        if (nbt.contains("ingredient_list")) {
            clearIngredientList();
            Inventories.readNbt(nbt.getCompound("ingredient_list"), this.ingredientList, registryLookup);
        }
    }


}
