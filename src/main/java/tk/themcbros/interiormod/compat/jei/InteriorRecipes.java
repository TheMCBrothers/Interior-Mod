package tk.themcbros.interiormod.compat.jei;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import tk.themcbros.interiormod.init.InteriorRecipeTypes;

import java.util.*;

/**
 * @author TheMCBrothers
 */
public class InteriorRecipes {
    private final RecipeManager recipeManager;

    public InteriorRecipes() {
        ClientWorld world = Minecraft.getInstance().world;
        assert world != null;
        this.recipeManager = world.getRecipeManager();
    }

    public List<ICraftingRecipe> getFurnitureCraftingRecipes() {
        return new ArrayList<>(getRecipes(recipeManager, InteriorRecipeTypes.FURNITURE_CRAFTING));
    }

    @SuppressWarnings("unchecked")
    private <C extends IInventory, T extends IRecipe<C>> Collection<T> getRecipes(RecipeManager recipeManager, IRecipeType<T> recipeType) {
        Map<ResourceLocation, IRecipe<?>> recipes = recipeManager.recipes.getOrDefault(recipeType, Collections.emptyMap());
        return (Collection<T>) recipes.values();
    }
}
