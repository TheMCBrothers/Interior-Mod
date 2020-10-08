package tk.themcbros.interiormod.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.api.furniture.InteriorRegistries;
import tk.themcbros.interiormod.init.FurnitureMaterials;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author TheMCBrothers
 */
@ParametersAreNonnullByDefault
public class FurnitureRecipeManager implements ISelectiveResourceReloadListener {

    private final RecipeManager recipeManager;

    public FurnitureRecipeManager(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        // FIXME: Remove this. We need this patch in order to prevent trying to load ReloadRequirements.
        this.onResourceManagerReload(resourceManager, x -> true);
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        recipeManager.recipes = new HashMap<>(recipeManager.recipes);
        recipeManager.recipes.replaceAll((t, v) -> new HashMap<>(recipeManager.recipes.get(t)));

        Map<ResourceLocation, IRecipe<?>> recipes = recipeManager.recipes.get(IRecipeType.CRAFTING);

        for (FurnitureMaterial primary : InteriorRegistries.FURNITURE_MATERIALS) {
            for (FurnitureMaterial secondary : InteriorRegistries.FURNITURE_MATERIALS) {
                if (primary.isValidForType(FurnitureType.CHAIR) && secondary.isValidForType(FurnitureType.CHAIR)) {
                    ShapedRecipe chair = createChairRecipe(primary, secondary);
                    recipes.put(chair.getId(), chair);
                }
                if (primary.isValidForType(FurnitureType.TABLE) && secondary.isValidForType(FurnitureType.TABLE)) {
                    ShapedRecipe table = createTableRecipe(primary, secondary);
                    recipes.put(table.getId(), table);
                }
            }
        }
    }

    private static ShapedRecipe createChairRecipe(FurnitureMaterial primary, FurnitureMaterial secondary) {
        Ingredient primaryIngredient = primary.getIngredient();
        Ingredient secondaryIngredient = secondary.getIngredient();
        NonNullList<Ingredient> inputs = NonNullList.from(Ingredient.EMPTY,
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
        NonNullList<Ingredient> inputs = NonNullList.from(Ingredient.EMPTY,
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
