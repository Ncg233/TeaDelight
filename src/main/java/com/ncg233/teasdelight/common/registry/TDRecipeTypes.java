package com.ncg233.teasdelight.common.registry;

import com.ncg233.teasdelight.TeaDelight;
import com.ncg233.teasdelight.common.recipe.DryingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TDRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES,
            TeaDelight.MODID);
    public static final RegistryObject<RecipeType<DryingRecipe>> DRYING = RECIPE_TYPES.register("drying", () -> registerRecipeType("drying"));

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<>()
        {
            public String toString() {
                return TeaDelight.MODID + ":" + identifier;
            }
        };
    }

}
