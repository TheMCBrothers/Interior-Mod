package tk.themcbros.interiormod.init;

import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import tk.themcbros.interiormod.InteriorMod;

/**
 * @author TheMCBrothers
 */
public class InteriorRecipeTypes {
    public static void init() {
    }

    public static final RecipeType<CraftingRecipe> FURNITURE_CRAFTING = RecipeType.register(InteriorMod.getId("furniture_crafting").toString());
}
