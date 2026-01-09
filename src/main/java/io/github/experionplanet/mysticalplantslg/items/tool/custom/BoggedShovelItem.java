package io.github.experionplanet.mysticalplantslg.items.tool.custom;

import io.github.experionplanet.mysticalplantslg.compat.MPLConfig;
import io.github.experionplanet.mysticalplantslg.init.MPLBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;


import java.util.List;

import static io.github.experionplanet.mysticalplantslg.init.MPLComponentTypes.SOIL_FILLINGS;
import static io.github.experionplanet.mysticalplantslg.init.MPLComponentTypes.SOIL_MODE;

public class BoggedShovelItem extends ShovelItem {
    public static final int MAX_FILLS = 128;

    public BoggedShovelItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    public static int getSoilFillings(ItemStack stack) {
        if (stack.contains(SOIL_FILLINGS)) {
            return stack.get(SOIL_FILLINGS);
        }
        return 0;
    }

    public static int getMode(ItemStack stack) {
        if (stack.contains(SOIL_MODE)) {
            return stack.get(SOIL_MODE);
        }
        return 1;
    }


    // CREDIT: https://github.com/nanite/JustHammers/blob/mc/1.21.1/common/src/main/java/pro/mikey/justhammers/HammerItem.java
    public static Iterable<BlockPos> getAreaOfEffect(BlockPos blockPos, Direction direction, int radius, int depth) {
        var size = (radius / 2);
        var offset = size - 1;

        /*
          return switch (direction) {
            case DOWN, UP -> BlockPos.iterate(blockPos.getX() - size, blockPos.getY() - (direction == Direction.UP ? depth - 1 : 0), blockPos.getZ() - size, blockPos.getX() + size, blockPos.getY() + (direction == Direction.DOWN ? depth - 1 : 0), blockPos.getZ() + size);
            case NORTH, SOUTH -> BlockPos.iterate(blockPos.getX() - size, blockPos.getY() - size + offset, blockPos.getZ() - (direction == Direction.SOUTH ? depth - 1 : 0), blockPos.getX() + size, blockPos.getY() + size + offset, blockPos.getZ() + (direction == Direction.NORTH ? depth - 1 : 0));
            case WEST, EAST -> BlockPos.iterate(blockPos.getX() - (direction == Direction.EAST ? depth - 1 : 0), blockPos.getY() - size + offset, blockPos.getZ() - size, blockPos.getX() + (direction == Direction.WEST ? depth - 1 : 0), blockPos.getY() + size + offset, blockPos.getZ() + size);
        }; BlockPos.iterate(blockPos.getX() - size, blockPos.getY() - (direction == Direction.UP ? depth - 1 : 0), blockPos.getZ() - size, blockPos.getX() + size, blockPos.getY() + (direction == Direction.DOWN ? depth - 1 : 0), blockPos.getZ() + size)
         */

        return switch (direction) {
            case DOWN, UP -> getAreaOfEffect(blockPos, Direction.NORTH, radius, depth);
            case NORTH, SOUTH -> BlockPos.iterate(blockPos.getX() - size, blockPos.getY(), blockPos.getZ() - (direction == Direction.SOUTH ? depth - 1 : 0), blockPos.getX() + size, blockPos.getY(), blockPos.getZ() + (direction == Direction.NORTH ? depth - 1 : 0));
            case WEST, EAST -> BlockPos.iterate(blockPos.getX() - (direction == Direction.EAST ? depth - 1 : 0), blockPos.getY(), blockPos.getZ() - size, blockPos.getX() + (direction == Direction.WEST ? depth - 1 : 0), blockPos.getY(), blockPos.getZ() + size);
        };
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        ItemStack stack = context.getStack();
        PlayerEntity plr = context.getPlayer();
        BlockState state = world.getBlockState(pos);

        if (state.isIn(MPLBlockTags.SOIL_CHANGING) && !plr.isSneaking()) {

            int mode = getMode(stack);
            Block block= getModeAsBlock(mode);

            if (!state.isOf(block)) {
                if (!world.isClient()) {
                    world.breakBlock(pos, false);
                    world.setBlockState(pos, block.getDefaultState());
                }

                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    private static Block getModeAsBlock(int mode) {
        Block block;
        if (mode == 2) {
            block = Blocks.GRASS_BLOCK;
        } else if (mode == 3) {
            block = Blocks.COARSE_DIRT;
        } else if (mode == 4) {
            block = Blocks.MUD;
        } else {
            block = Blocks.DIRT;
        }

        return block;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && user.isSneaking()) {
            ItemStack stack = user.getStackInHand(hand);
            int total = getMode(stack) + 1;

            if (total > 4) {
                total = 1;
            }

            stack.set(SOIL_MODE, total);
        }
        return super.use(world, user, hand);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean res = super.postMine(stack, world, state, pos, miner);

        if (!world.isClient()) {
            if (res && getSoilFillings(stack) > 0) {
                if (miner instanceof ServerPlayerEntity plr) {
                    if (!plr.isSneaking()) {
                        HitResult rayRes = miner.raycast(20D, 0.0F, false);
                        if (rayRes instanceof BlockHitResult blockHitResult) {
                            Iterable<BlockPos> blockIterate = getAreaOfEffect(pos, blockHitResult.getSide(), 2, 1);
                            boolean firstConsume = false;
                            for (BlockPos v : blockIterate) {
                                if (!v.equals(pos)) {
                                    BlockState targetState = world.getBlockState(v);
                                    if (this.canMine(targetState, world, pos, plr) && stack.getDamage() > 0 && targetState.isSolidBlock(world, pos)) {
                                        world.breakBlock(v, true, miner);
                                        stack.damage(1,plr, EquipmentSlot.MAINHAND);

                                        boolean consumeFill;

                                        if (!firstConsume) {
                                            firstConsume = true;
                                            consumeFill = true;
                                        }else {
                                            consumeFill = world.getRandom().nextBoolean();
                                        }

                                        if (consumeFill) {
                                            stack.set(SOIL_FILLINGS, getSoilFillings(stack) - world.getRandom().nextBetween(1, 2));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        }

        }

        return res;
    }

    public static void fillSoil(ItemStack stack, ItemStack fuel) {
        int curr = getSoilFillings(stack);
        if (curr < MAX_FILLS) {
            int numFuel = fuel.getCount();
            int emptyFills = MAX_FILLS - curr;
            int total = Math.min(numFuel, emptyFills);
            fuel.decrement(total);
            stack.set(SOIL_FILLINGS, curr + total);
        }
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        if (getSoilFillings(stack) > 0 && MPLConfig.show_fillings_durability) {
            return ColorHelper.Argb.getArgb(137, 101, 77);
        }
        return super.getItemBarColor(stack);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        if (getSoilFillings(stack) > 0 && MPLConfig.show_fillings_durability) {
            return true;
        }
        return super.isItemBarVisible(stack);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        int curr = getSoilFillings(stack);

        if (curr > 0 && MPLConfig.show_fillings_durability) {
            return MathHelper.clamp(Math.round(13.0F - (float)(MAX_FILLS - curr) * 13.0F / (float) MAX_FILLS), 0, 13);
        }

        return super.getItemBarStep(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("Soils: " + getSoilFillings(stack) + "/" + MAX_FILLS));
        tooltip.add(Text.literal("Mode: ").append(Text.translatable(getModeAsBlock(getMode(stack)).getTranslationKey())));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
