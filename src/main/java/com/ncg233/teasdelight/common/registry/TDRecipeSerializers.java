package com.ncg233.teasdelight.common.registry;

import com.ncg233.teasdelight.TeaDelight;
import com.ncg233.teasdelight.common.recipe.DryingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TDRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TeaDelight.MODID);

    public static final RegistryObject<RecipeSerializer<?>> DRYING = RECIPE_SERIALIZERS.register("drying", DryingRecipe.Serializer::new);

}
