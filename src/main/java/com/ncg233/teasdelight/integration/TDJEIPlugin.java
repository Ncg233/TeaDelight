package com.ncg233.teasdelight.integration;

import com.ncg233.teasdelight.TeaDelight;
import com.ncg233.teasdelight.common.registry.TDItems;
import com.ncg233.teasdelight.integration.category.DryingRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class TDJEIPlugin implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(TeaDelight.MODID, "jei_plugin");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new DryingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        JEIRecipes modRecipes = new JEIRecipes();
        registration.addRecipes(JEIRecipeTypes.DRYING, modRecipes.getDryingPotRecipes());
    }
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(TDItems.DRY_RACK.get()), JEIRecipeTypes.DRYING);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

}
