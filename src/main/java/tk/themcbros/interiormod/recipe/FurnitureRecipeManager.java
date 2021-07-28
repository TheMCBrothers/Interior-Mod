package tk.themcbros.interiormod.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.ShapedRecipe;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.api.furniture.InteriorRegistries;
import tk.themcbros.interiormod.init.FurnitureMaterials;
import tk.themcbros.interiormod.init.InteriorRecipeTypes;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TheMCBrothers
 */
@ParametersAreNonnullByDefault
public class FurnitureRecipeManager implements ResourceManagerReloadListener {

    private final RecipeManager recipeManager;

    public FurnitureRecipeManager(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        recipeManager.recipes = new HashMap<>(recipeManager.recipes);
        recipeManager.recipes.replaceAll((t, v) -> new HashMap<>(recipeManager.recipes.get(t)));

        Map<ResourceLocation, Recipe<?>> recipes = new HashMap<>();

        for (FurnitureMaterial primary : InteriorRegistries.FURNITURE_MATERIALS) {
            for (FurnitureMaterial secondary : InteriorRegistries.FURNITURE_MATERIALS) {
                if (primary.isValidForType(FurnitureType.CHAIR) && secondary.isValidForType(FurnitureType.CHAIR)) {
                    FurnitureShapedRecipe chair = new FurnitureShapedRecipe(createChairRecipe(primary, secondary));
                    recipes.put(chair.getId(), chair);
                }
                if (primary.isValidForType(FurnitureType.TABLE) && secondary.isValidForType(FurnitureType.TABLE)) {
                    FurnitureShapedRecipe table = new FurnitureShapedRecipe(createTableRecipe(primary, secondary));
                    recipes.put(table.getId(), table);
                }
            }
        }

        recipeManager.recipes.put(InteriorRecipeTypes.FURNITURE_CRAFTING, recipes);
    }

    private static ShapedRecipe createChairRecipe(FurnitureMaterial primary, FurnitureMaterial secondary) {
        Ingredient primaryIngredient = primary.getIngredient();
        Ingredient secondaryIngredient = secondary.getIngredient();
        NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
                primaryIngredient, Ingredient.EMPTY, Ingredient.EMPTY,
                primaryIngredient, secondaryIngredient, secondaryIngredient,
                primaryIngredient, Ingredient.EMPTY, primaryIngredient
        );
        ItemStack output = FurnitureMaterials.createItemStack(FurnitureType.CHAIR, primary, secondary);

        String saveId = "chair/" + String.valueOf(primary.getRegistryName()).replace(':', '_') + "/" +
                String.valueOf(secondary.getRegistryName()).replace(':', '_');
        ResourceLocation name = new ResourceLocation(InteriorMod.MOD_ID, saveId);
        return new ShapedRecipe(name, "interiormod:chairs", 3, 3, inputs, output);
    }

    private static ShapedRecipe createTableRecipe(FurnitureMaterial primary, FurnitureMaterial secondary) {
        Ingredient primaryIngredient = primary.getIngredient();
        Ingredient secondaryIngredient = secondary.getIngredient();
        NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
                primaryIngredient, primaryIngredient, primaryIngredient,
                secondaryIngredient, Ingredient.EMPTY, secondaryIngredient,
                secondaryIngredient, Ingredient.EMPTY, secondaryIngredient
        );
        ItemStack output = FurnitureMaterials.createItemStack(FurnitureType.TABLE, primary, secondary);

        String saveId = "table/" + String.valueOf(primary.getRegistryName()).replace(':', '_') + "/" +
                String.valueOf(secondary.getRegistryName()).replace(':', '_');
        ResourceLocation name = new ResourceLocation(InteriorMod.MOD_ID, saveId);
        return new ShapedRecipe(name, "interiormod:tables", 3, 3, inputs, output);
    }

}
