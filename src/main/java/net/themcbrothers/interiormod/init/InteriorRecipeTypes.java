package net.themcbrothers.interiormod.init;

import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.RegistryObject;

import static net.themcbrothers.interiormod.init.Registration.RECIPE_TYPES;

/**
 * @author TheMCBrothers
 */
public class InteriorRecipeTypes {
    public static final RegistryObject<RecipeType<CraftingRecipe>> FURNITURE_CRAFTING = RECIPE_TYPES.register("furniture_crafting", () -> new RecipeType<>() {
    });

    static void init() {
    }
}
