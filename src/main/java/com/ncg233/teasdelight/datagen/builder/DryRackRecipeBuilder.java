package com.ncg233.teasdelight.datagen.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ncg233.teasdelight.TeaDelight;
import com.ncg233.teasdelight.common.registry.TDRecipeSerializers;
import com.ncg233.teasdelight.common.registry.TDRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.List;
import java.util.function.Consumer;

public class DryRackRecipeBuilder {
    private final Ingredient ingredient;
    private final Item result;
    private final int time;

    private DryRackRecipeBuilder(Ingredient ingredient, Item result, int time) {
        this.ingredient = ingredient;
        this.result = result;
        this.time = time;
    }
    public static DryRackRecipeBuilder dryingRecipe(Ingredient ingredient,Item result){
        return new DryRackRecipeBuilder(ingredient,result,20 * 2 * 60);
    }
    public static DryRackRecipeBuilder dryingRecipe(Ingredient ingredient,Item result, int time){
        return new DryRackRecipeBuilder(ingredient,result,time);
    }
    public void build(Consumer<FinishedRecipe> consumerIn) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(result);
        this.build(consumerIn, TeaDelight.MODID + ":drying/" + location.getPath());
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String save) {
        ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(this.ingredient.getItems()[0].getItem());
        if ((new ResourceLocation(save)).equals(resourcelocation)) {
            throw new IllegalStateException("Cutting Recipe " + save + " should remove its 'save' argument");
        } else {
            this.build(consumerIn, new ResourceLocation(save));
        }
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new Result(id, this.ingredient,this.result,this.time));
    }
    public static class Result implements FinishedRecipe{
        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final Item result;
        private final int time;
        public Result(ResourceLocation idIn, Ingredient ingredientIn, Item resultsIn, int time) {
            this.id = idIn;
            this.ingredient = ingredientIn;
            this.result = resultsIn;
            this.time = time;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray arrayIngredients = new JsonArray();
            arrayIngredients.add(this.ingredient.toJson());
            json.add("ingredients", arrayIngredients);

            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", ForgeRegistries.ITEMS.getKey(result).toString());
            json.add("output", jsonobject);

            json.addProperty("drying_time",time);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return TDRecipeSerializers.DRYING.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }

}
