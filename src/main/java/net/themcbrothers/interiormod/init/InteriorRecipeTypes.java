package net.themcbrothers.interiormod.init;

import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.themcbrothers.interiormod.InteriorMod;

/**
 * @author TheMCBrothers
 */
public class InteriorRecipeTypes {
    public static void init() {
    }

    public static final RecipeType<CraftingRecipe> FURNITURE_CRAFTING = RecipeType.register(InteriorMod.getId("furniture_crafting").toString());
}
