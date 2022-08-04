package net.themcbrothers.interiormod.compat.jei;

import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.themcbrothers.interiormod.api.InteriorAPI;

/**
 * @author TheMCBrothers
 */
public class InteriorRecipeCategories {
    static final RecipeType<CraftingRecipe> FURNITURE_CRAFTING = RecipeType.create(InteriorAPI.MOD_ID, "furniture_crafting", CraftingRecipe.class);
}
