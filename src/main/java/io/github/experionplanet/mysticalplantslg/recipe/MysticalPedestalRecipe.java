package io.github.experionplanet.mysticalplantslg.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.experionplanet.mysticalplantslg.init.MPLRecipes;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class MysticalPedestalRecipe implements Recipe<PedestalRecipeInput>{
    private final List<Ingredient> ingredientList;
    private final Ingredient lastIngredient;
    private final ItemStack bindedStack;
    private final ItemStack resultStack;
    public static final Identifier id = MysticalUtils.newId("mystical_pedestal");

    public MysticalPedestalRecipe(List<Ingredient> list, Ingredient lastIngredient, ItemStack bindedStack, ItemStack outputStack) {

        this.bindedStack = bindedStack;
        this.ingredientList = list;
        this.lastIngredient = lastIngredient;
        this.resultStack = outputStack;
    }

    public List<Ingredient> ingredientList() {
        return this.ingredientList;
    }

    public Ingredient getIngredient(int slot) {
        Ingredient res = this.ingredientList.get(slot);
        if (res != null) {
            return res;
        }
        return Ingredient.EMPTY;
    }

    public Ingredient lastIngredient() {
        return this.lastIngredient;
    }

    public ItemStack getBindedStack() {
        return this.bindedStack;
    }

    @Override
    public boolean matches(PedestalRecipeInput input, World world) {
        if(world.isClient()) {
            return false;
        }



        if (!this.bindedStack.isOf(input.getBindedStack().getItem())) {
            return false;
        }

        List<Ingredient> required = new ArrayList<>(List.copyOf(this.ingredientList));

        for (ItemStack stack : input.getStackList()) {
            boolean succ = false;
            for (int i = 0; i < required.size(); i++) {
                Ingredient v = required.get(i);
                if (v.test(stack)) {
                    required.remove(i);
                    succ = true;
                    break;
                }
            }
            if (!succ) {
                return false;
            }
        }

        return this.lastIngredient.test(input.getLastStack());
    }

    @Override
    public ItemStack craft(PedestalRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return resultStack.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return this.resultStack;
    }

    public ItemStack getOutput() {
        return this.resultStack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MPLRecipes.MYSTICAL_PEDESTAL_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return MPLRecipes.MYSTICAL_PEDESTAL_TYPE;
    }

    public static class Serializer implements RecipeSerializer<MysticalPedestalRecipe> {


        public static final MapCodec<MysticalPedestalRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(

                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("input1").forGetter(r -> r.getIngredient(0)),
                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("input2").forGetter(r -> r.getIngredient(1)),
                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("input3").forGetter(r -> r.getIngredient(2)),
                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("input4").forGetter(r -> r.getIngredient(3)),
                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("input5").forGetter(r -> r.getIngredient(4)),
                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("input6").forGetter(r -> r.getIngredient(5)),
                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("input7").forGetter(r -> r.getIngredient(6)),
                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("input8").forGetter(r -> r.getIngredient(7)),
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("last_ingredient").forGetter(MysticalPedestalRecipe::lastIngredient),
                ItemStack.VALIDATED_CODEC.fieldOf("binded").forGetter(MysticalPedestalRecipe::getBindedStack),
                ItemStack.VALIDATED_CODEC.fieldOf("result_item").forGetter(MysticalPedestalRecipe::getOutput)
                ).apply(inst, (
                        i1,
                        i2,
                        i3,
                        i4,
                        i5,
                        i6,
                        i7,
                        i8,
                        last,
                        binded,
                        stack
                ) -> new MysticalPedestalRecipe(List.of(i1,i2,i3,i4,i5,i6,i7,i8), last, binded,stack))
        );

        public static final PacketCodec<RegistryByteBuf, MysticalPedestalRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                Serializer::write, Serializer::read
        );

        @Override
        public MapCodec<MysticalPedestalRecipe> codec() {

            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, MysticalPedestalRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static MysticalPedestalRecipe read(RegistryByteBuf buf) {
            Ingredient v1 = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient v2 = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient v3 = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient v4 = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient v5 = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient v6 = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient v7 = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient v8 = Ingredient.PACKET_CODEC.decode(buf);

            Ingredient lastIngredient = Ingredient.PACKET_CODEC.decode(buf);

            ItemStack binded = ItemStack.PACKET_CODEC.decode(buf);

            ItemStack result = ItemStack.PACKET_CODEC.decode(buf);

            return new MysticalPedestalRecipe(List.of(v1,v2,v3,v4,v5,v6,v7,v8), lastIngredient, binded, result);
        }

        private static void write(RegistryByteBuf buf, MysticalPedestalRecipe recipe) {
            Ingredient.PACKET_CODEC.encode(buf, recipe.getIngredient(0));
            Ingredient.PACKET_CODEC.encode(buf, recipe.getIngredient(1));
            Ingredient.PACKET_CODEC.encode(buf, recipe.getIngredient(2));
            Ingredient.PACKET_CODEC.encode(buf, recipe.getIngredient(0));
            Ingredient.PACKET_CODEC.encode(buf, recipe.getIngredient(4));
            Ingredient.PACKET_CODEC.encode(buf, recipe.getIngredient(5));
            Ingredient.PACKET_CODEC.encode(buf, recipe.getIngredient(6));
            Ingredient.PACKET_CODEC.encode(buf, recipe.getIngredient(7));



            Ingredient.PACKET_CODEC.encode(buf, recipe.lastIngredient());

            ItemStack.PACKET_CODEC.encode(buf, recipe.getBindedStack());

            ItemStack.PACKET_CODEC.encode(buf, recipe.resultStack);
        }


    }
}
