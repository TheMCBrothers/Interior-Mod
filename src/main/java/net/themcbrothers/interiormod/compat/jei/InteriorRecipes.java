package net.themcbrothers.interiormod.compat.jei;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.themcbrothers.interiormod.init.InteriorRecipeTypes;

import java.util.*;

/**
 * @author TheMCBrothers
 */
public class InteriorRecipes {
    private final RecipeManager recipeManager;

    public InteriorRecipes() {
        ClientLevel world = Minecraft.getInstance().level;
        assert world != null;
        this.recipeManager = world.getRecipeManager();
    }

    public List<CraftingRecipe> getFurnitureCraftingRecipes() {
        return new ArrayList<>(getRecipes(recipeManager, InteriorRecipeTypes.FURNITURE_CRAFTING));
    }

    @SuppressWarnings("unchecked")
    private <C extends Container, T extends Recipe<C>> Collection<T> getRecipes(RecipeManager recipeManager, RecipeType<T> recipeType) {
        Map<ResourceLocation, Recipe<?>> recipes = recipeManager.recipes.getOrDefault(recipeType, Collections.emptyMap());
        return (Collection<T>) recipes.values();
    }
}
