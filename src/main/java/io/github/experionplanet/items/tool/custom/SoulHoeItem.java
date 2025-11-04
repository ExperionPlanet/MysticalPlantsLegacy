package io.github.experionplanet.items.tool.custom;

import io.github.experionplanet.compat.MPLMidnightConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import static io.github.experionplanet.init.MPLComponentTypes.SOIL_FILLINGS;
import static io.github.experionplanet.init.MPLComponentTypes.SOULS;
import static io.github.experionplanet.init.MPLBlockProperties.SOUL_ATTUNED;

public class SoulHoeItem extends HoeItem {
    public static final int MAX_SOULS = 50;

    public SoulHoeItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    public static int getSouls(ItemStack stack) {
        if (stack.contains(SOULS)) {
            return stack.get(SOULS);
        }

        return 0;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        ItemStack stack = context.getStack();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (!world.isClient() && state.getBlock() instanceof FarmlandBlock) {
            if (!state.get(SOUL_ATTUNED)) {
                world.setBlockState(pos, state.with(SOUL_ATTUNED, true));
                return ActionResult.SUCCESS;
            }
        }

        return super.useOnBlock(context);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        if (getSouls(stack) > 0 && MPLMidnightConfig.show_fillings_durability) {
            return true;
        }
        return super.isItemBarVisible(stack);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        int curr = getSouls(stack);
        if (curr > 0 && MPLMidnightConfig.show_fillings_durability) {
            return MathHelper.clamp(Math.round(13.0F - (float)(MAX_SOULS - curr) * 13.0F / (float) MAX_SOULS), 0, 13);
        }
        return super.getItemBarStep(stack);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        if (getSouls(stack) > 0 && MPLMidnightConfig.show_fillings_durability) {
            return ColorHelper.Argb.getArgb(75,246,242);
        }
        return super.getItemBarColor(stack);
    }

    public static int fillings(ItemStack stack, int fuel) {
        int curr = getSouls(stack);
        if (curr < MAX_SOULS) {

            int emptyFills = MAX_SOULS - curr;
            int total = Math.min(fuel, emptyFills);
            stack.set(SOULS, curr + total);

            return total;
        }
        return 0;
    };

    public static void fillings(ItemStack stack, ItemStack fuel) {
        int totalFuel = fillings(stack, fuel.getCount());

        if (totalFuel > 0) {
            fuel.decrement(totalFuel);
        }
    }
}
