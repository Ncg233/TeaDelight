package com.ncg233.teasdelight.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ncg233.teasdelight.TeaDelight;
import com.ncg233.teasdelight.common.registry.TDRecipeSerializers;
import com.ncg233.teasdelight.common.registry.TDRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class DryingRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final int dryingTime;
    private final NonNullList<Ingredient> recipeItems;
    public DryingRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, int dryingTime) {
        this.id = id;
        this.output= output;
        this.recipeItems = recipeItems;
        this.dryingTime = dryingTime;
    }
    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if(level.isClientSide()) {
            return false;
        }
        return recipeItems.get(0).test(container.getItem(0));
    }

    public int getDryingTime() {
        return dryingTime;
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess access) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return output.copy();
    }
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TDRecipeSerializers.DRYING.get();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    @Override
    public RecipeType<?> getType() {
        return TDRecipeTypes.DRYING.get();
    }
    public static class Serializer implements RecipeSerializer<DryingRecipe> {
        public static final DryingRecipe.Serializer INSTANCE = new DryingRecipe.Serializer();
        public static final ResourceLocation ID = new ResourceLocation(TeaDelight.MODID,"drying");

        @Override
        public DryingRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json,"output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json,"ingredients");
            int times = GsonHelper.getAsInt(json,"drying_time");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1,Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i,Ingredient.fromJson(ingredients.get(i)));
            }
            return new DryingRecipe(id,output,inputs,times);
        }
        @Nullable
        @Override
        public DryingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            int times = buf.readableBytes();
            return new DryingRecipe(id, output,inputs,times);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, DryingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing :recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItem(recipe.output);
        }

        private static<G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}
