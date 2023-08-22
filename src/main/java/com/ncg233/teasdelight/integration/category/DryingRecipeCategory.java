package com.ncg233.teasdelight.integration.category;

import com.ncg233.teasdelight.TeaDelight;
import com.ncg233.teasdelight.common.recipe.DryingRecipe;
import com.ncg233.teasdelight.common.registry.TDItems;
import com.ncg233.teasdelight.common.registry.TDRecipeTypes;
import com.ncg233.teasdelight.integration.JEIRecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.utility.ClientRenderUtils;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DryingRecipeCategory implements IRecipeCategory<DryingRecipe> {
    protected final IDrawable heatIndicator;
    protected final IDrawable timeIcon;
    protected final IDrawable expIcon;
    protected final IDrawableAnimated arrow;
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public DryingRecipeCategory(IGuiHelper helper) {
        title = Component.translatable("teasdelight.jei.drying");
        ResourceLocation backgroundImage = new ResourceLocation(TeaDelight.MODID, "textures/gui/jei/dry_rack.png");
        background = helper.createDrawable(backgroundImage, 0, 0, 116, 56);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(TDItems.DRY_RACK.get()));
        heatIndicator = helper.createDrawable(backgroundImage, 176, 0, 17, 15);
        timeIcon = helper.createDrawable(backgroundImage, 176, 32, 8, 11);
        expIcon = helper.createDrawable(backgroundImage, 176, 43, 9, 9);
        arrow = helper.drawableBuilder(backgroundImage, 176, 15, 24, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }
    @Override
    public RecipeType<DryingRecipe> getRecipeType() {
        return JEIRecipeTypes.DRYING;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DryingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 16, 8).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 82, 22).addItemStack(recipe.getResultItem());
    }
    @Override
    public List<Component> getTooltipStrings(DryingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (ClientRenderUtils.isCursorInsideBounds(45, 22, 27, 18, mouseX, mouseY)) {
            List<Component> tooltipStrings = new ArrayList<>();

            int cookTime = recipe.getDryingTime();
            if (cookTime > 0) {
                int cookTimeSeconds = cookTime / 20;
                tooltipStrings.add(Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds));
            }
            return tooltipStrings;
        }
        return Collections.emptyList();
    }
    @Override
    public void draw(DryingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        arrow.draw(guiGraphics, 50, 22);
    }

}
