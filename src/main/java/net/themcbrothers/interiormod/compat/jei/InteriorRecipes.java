package net.themcbrothers.interiormod.compat.jei;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.themcbrothers.interiormod.init.InteriorRecipeTypes;

import java.util.List;

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
        return this.recipeManager.getAllRecipesFor(InteriorRecipeTypes.FURNITURE_CRAFTING.get());
    }
}
