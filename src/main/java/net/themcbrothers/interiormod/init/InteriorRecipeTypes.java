package net.themcbrothers.interiormod.init;

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.themcbrothers.interiormod.InteriorMod;

/**
 * @author TheMCBrothers
 */
public class InteriorRecipeTypes {
    public static void init() {
    }

    public static final DeferredRegister<RecipeType<?>> REGISTER = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, InteriorMod.MOD_ID);

    public static final RegistryObject<RecipeType<CraftingRecipe>> FURNITURE_CRAFTING = REGISTER.register("furniture_crafting", () -> new RecipeType<>() {
    });
}
