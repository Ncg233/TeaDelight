package com.ncg233.teasdelight.integration;

import com.ncg233.teasdelight.common.recipe.DryingRecipe;
import com.ncg233.teasdelight.common.registry.TDRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

public class JEIRecipes {
    private final RecipeManager recipeManager;

    public JEIRecipes() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;

        if (level != null) {
            this.recipeManager = level.getRecipeManager();
        } else {
            throw new NullPointerException("minecraft world must not be null.");
        }
    }

    public List<DryingRecipe> getDryingPotRecipes() {
        return recipeManager.getAllRecipesFor(TDRecipeTypes.DRYING.get()).stream().toList();
    }
}
