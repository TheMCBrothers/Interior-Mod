package tk.themcbros.interiormod.init;

import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import tk.themcbros.interiormod.InteriorMod;

/**
 * @author TheMCBrothers
 */
public class InteriorRecipeTypes {
    public static void init() {}
    public static final IRecipeType<ICraftingRecipe> FURNITURE_CRAFTING = IRecipeType.register(InteriorMod.getId("furniture_crafting").toString());
}
