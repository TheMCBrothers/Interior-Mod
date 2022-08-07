package net.themcbrothers.interiormod.init;

import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;

/**
 * @author TheMCBrothers
 */
public class InteriorRecipeBookExtensions {
    public static final RecipeBookCategories FURNITURE_WORKBENCH_CATEGORY = RecipeBookCategories.create("FURNITURE_WORKBENCH", new ItemStack(InteriorItems.FURNITURE_WORKBENCH.get()));
    public static final RecipeBookType FURNITURE_WORKBENCH_TYPE = RecipeBookType.create("FURNITURE_WORKBENCH");
}
