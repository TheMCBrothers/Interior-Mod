package net.themcbrothers.interiormod.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.themcbrothers.interiormod.init.InteriorBlocks;
import net.themcbrothers.interiormod.util.RecipeUtil;

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
        this.localizedText = Component.translatable("container.interiormod.furniture_crafting");
        this.background = guiHelper.createDrawable(CRAFTING_TABLE_GUI_TEXTURES, 29, 16, width, height);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(InteriorBlocks.FURNITURE_WORKBENCH.get()));
        this.craftingGridHelper = guiHelper.createCraftingGridHelper();
    }

    @Override
    public RecipeType<CraftingRecipe> getRecipeType() {
        return InteriorRecipeCategories.FURNITURE_CRAFTING;
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
    public void setRecipe(IRecipeLayoutBuilder builder, CraftingRecipe recipe, IFocusGroup focuses) {
        List<List<ItemStack>> inputs = recipe.getIngredients().stream()
                .map(ingredient -> List.of(ingredient.getItems()))
                .toList();
        ItemStack resultItem = RecipeUtil.getResultItem(recipe);

        int width = recipe instanceof IShapedRecipe<?> shapedRecipe ? shapedRecipe.getRecipeWidth() : 0;
        int height = recipe instanceof IShapedRecipe<?> shapedRecipe ? shapedRecipe.getRecipeHeight() : 0;

        craftingGridHelper.createAndSetOutputs(builder, List.of(resultItem));
        craftingGridHelper.createAndSetInputs(builder, inputs, width, height);
    }
}