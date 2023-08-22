package com.ncg233.teasdelight.integration;

import com.ncg233.teasdelight.TeaDelight;
import com.ncg233.teasdelight.common.recipe.DryingRecipe;
import mezz.jei.api.recipe.RecipeType;

public class JEIRecipeTypes {
    public static final RecipeType<DryingRecipe> DRYING = RecipeType.create(TeaDelight.MODID, "drying", DryingRecipe.class);
}
