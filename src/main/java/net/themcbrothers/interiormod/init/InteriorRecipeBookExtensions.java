package net.themcbrothers.interiormod.init;

import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.RecipeBookRegistry;

import java.util.List;

/**
 * @author TheMCBrothers
 */
public class InteriorRecipeBookExtensions {
    public static final RecipeBookCategories FURNITURE_WORKBENCH_CATEGORY = RecipeBookCategories.create("FURNITURE_WORKBENCH", new ItemStack(InteriorItems.FURNITURE_WORKBENCH));
    public static final RecipeBookType FURNITURE_WORKBENCH_TYPE = RecipeBookType.create("FURNITURE_WORKBENCH");

    public static void init() {
        RecipeBookRegistry.addCategoriesToType(FURNITURE_WORKBENCH_TYPE, List.of(FURNITURE_WORKBENCH_CATEGORY));
        RecipeBookRegistry.addCategoriesFinder(InteriorRecipeTypes.FURNITURE_CRAFTING.get(), recipe -> FURNITURE_WORKBENCH_CATEGORY);
    }
}
