package net.themcbrothers.interiormod.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.RegistryObject;
import net.themcbrothers.interiormod.recipe.FurnitureShapedRecipe;

import static net.themcbrothers.interiormod.init.Registration.RECIPE_SERIALIZERS;

/**
 * @author TheMCBrothers
 */
public class InteriorRecipeSerializers {
    public static final RegistryObject<RecipeSerializer<FurnitureShapedRecipe>> FURNITURE_SHAPED = RECIPE_SERIALIZERS.register("furniture_shaped", FurnitureShapedRecipe.Serializer::new);

    static void init() {
    }
}
