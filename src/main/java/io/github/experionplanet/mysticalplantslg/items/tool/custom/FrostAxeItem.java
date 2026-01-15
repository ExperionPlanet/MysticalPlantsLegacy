package io.github.experionplanet.mysticalplantslg.items.tool.custom;

import io.github.experionplanet.mysticalplantslg.blocks.custom.PermafrostedLogBlock;
import io.github.experionplanet.mysticalplantslg.blocks.entity.custom.PermafrostLogBlockEntity;
import io.github.experionplanet.mysticalplantslg.init.MPLBlocks;
import io.github.experionplanet.mysticalplantslg.init.MPLParticles;
import io.github.experionplanet.mysticalplantslg.init.MPLSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class FrostAxeItem extends AxeItem {
    private static final BlockPos[] ADD_POS = {
        new BlockPos(0, 1, 0),
            new BlockPos(0, -1, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(0, 0, 1),
            new BlockPos(0, 0, -1)
    };

    private static final int MAX_FROST = 20;

    public FrostAxeItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.empty());
        tooltip.add(Text.translatable("mysticalplantslg.tooltip.frost_axe_1"));
        tooltip.add(Text.translatable("mysticalplantslg.tooltip.frost_axe_2"));

        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean bool = super.postMine(stack, world, state, pos, miner);
        if (bool && !world.isClient()) {
            if (world.getRandom().nextFloat() <= 0.4) {
                int total = 0;
                List<BlockPos> prev = List.of(pos);
                List<BlockPos> discovered = new ArrayList<>();

                while (total < MAX_FROST) {
                    boolean succ = false;
                    for (BlockPos p : prev) {
                        for (BlockPos g : ADD_POS) {
                            BlockPos b = p.add(g);
                            BlockState s = world.getBlockState(b);
                            if (!s.isOf(MPLBlocks.PERMAFROSTED_LOG) && s.isIn(BlockTags.LOGS)) {
                                discovered.add(b);
                                total++;
                                ItemStack dropStack = new ItemStack(s.getBlock());
                                world.setBlockState(b, MPLBlocks.PERMAFROSTED_LOG.getDefaultState());
                                BlockState newState = world.getBlockState(b);
                                PermafrostLogBlockEntity blockEntity = (PermafrostLogBlockEntity) world.getBlockEntity(b);
                                blockEntity.setStack(dropStack);
                                blockEntity.startDefrost(world, miner);
                                world.setBlockState(b, newState.with(PermafrostedLogBlock.DEFROSTING, true));
                                Vec3d v = b.toCenterPos();
                                ((ServerWorld) world).spawnParticles(MPLParticles.SNOWDUST, v.getX(), v.getY(), v.getZ(), 5, 0.5,0.5,0.5,0);
                                succ = true;
                            }
                        }
                    }
                    if (!succ) {
                        break;
                    }else {
                        prev = List.copyOf(discovered);
                        discovered.clear();
                    }
                }

                if (total > 0) {
                    world.playSound(null, pos, MPLSoundEvents.FROST_AXE_FROST_LOGS, SoundCategory.PLAYERS);
                }
            }

        }
        return bool;
    }
}
