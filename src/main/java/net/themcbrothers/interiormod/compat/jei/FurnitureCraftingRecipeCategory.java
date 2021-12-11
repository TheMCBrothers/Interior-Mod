package net.themcbrothers.interiormod.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.themcbrothers.interiormod.InteriorMod;
import net.themcbrothers.interiormod.init.InteriorBlocks;

import java.util.List;

/**
 * @author TheMCBrothers
 */
public class FurnitureCraftingRecipeCategory implements IRecipeCategory<CraftingRecipe> {
    private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/crafting_table.png");

    public static final int width = 116;
    public static final int height = 54;

    private final Component localizedText;
    private final IDrawable background, icon;
    private final ICraftingGridHelper craftingGridHelper;

    public FurnitureCraftingRecipeCategory(IGuiHelper guiHelper) {
        this.localizedText = new TranslatableComponent("container.interiormod.furniture_crafting");
        this.background = guiHelper.createDrawable(CRAFTING_TABLE_GUI_TEXTURES, 29, 16, width, height);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(InteriorBlocks.FURNITURE_WORKBENCH));
        this.craftingGridHelper = guiHelper.createCraftingGridHelper(1);
    }

    @Override
    public ResourceLocation getUid() {
        return InteriorMod.getId("furniture_crafting");
    }

    @Override
    public Class<? extends CraftingRecipe> getRecipeClass() {
        return CraftingRecipe.class;
    }

    @Override
    public Component getTitle() {
        return this.localizedText;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(CraftingRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CraftingRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, false, 94, 18);

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int index = 1 + x + (y * 3);
                guiItemStacks.init(index, true, x * 18, y * 18);
            }
        }

        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);

        if (recipe instanceof ShapedRecipe) {
            craftingGridHelper.setInputs(guiItemStacks, inputs, ((ShapedRecipe) recipe).getRecipeWidth(), ((ShapedRecipe) recipe).getRecipeHeight());
        } else {
            craftingGridHelper.setInputs(guiItemStacks, inputs);
            recipeLayout.setShapeless();
        }

        guiItemStacks.set(0, outputs.get(0));
    }
}