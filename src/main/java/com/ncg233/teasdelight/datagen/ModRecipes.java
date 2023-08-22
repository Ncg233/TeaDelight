package com.ncg233.teasdelight.datagen;

import com.ncg233.teasdelight.common.registry.TDItems;
import com.ncg233.teasdelight.datagen.builder.DryRackRecipeBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class ModRecipes extends RecipeProvider {
    public ModRecipes(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        DryRackRecipeBuilder.dryingRecipe(Ingredient.of(TDItems.GREEN_TEA.get()),TDItems.DRIED_GREEN_TEA.get(),200)
                .build(consumer);
    }
}
