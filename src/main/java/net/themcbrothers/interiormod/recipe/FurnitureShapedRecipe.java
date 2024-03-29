package net.themcbrothers.interiormod.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.themcbrothers.interiormod.init.InteriorBlocks;
import net.themcbrothers.interiormod.init.InteriorRecipeSerializers;
import net.themcbrothers.interiormod.init.InteriorRecipeTypes;

import javax.annotation.Nullable;

/**
 * @author TheMCBrothers
 */
public class FurnitureShapedRecipe extends ShapedRecipe {
    private static final RecipeSerializer<ShapedRecipe> BASE_SERIALIZER = RecipeSerializer.SHAPED_RECIPE;

    private final ShapedRecipe recipe;

    public FurnitureShapedRecipe(ShapedRecipe recipe) {
        super(recipe.getId(), recipe.getGroup(), recipe.category(), recipe.getRecipeWidth(), recipe.getRecipeHeight(), recipe.getIngredients(), recipe.getResultItem(null)); // TODO
        this.recipe = recipe;
    }

    public ShapedRecipe getBaseRecipe() {
        return recipe;
    }

    @Override
    public RecipeType<?> getType() {
        return InteriorRecipeTypes.FURNITURE_CRAFTING.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InteriorRecipeSerializers.FURNITURE_SHAPED.get();
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(InteriorBlocks.FURNITURE_WORKBENCH.get());
    }

    public static class Serializer implements RecipeSerializer<FurnitureShapedRecipe> {
        @Override
        public FurnitureShapedRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            return new FurnitureShapedRecipe(BASE_SERIALIZER.fromJson(recipeId, json));
        }

        @Nullable
        @Override
        public FurnitureShapedRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            final ShapedRecipe recipe = BASE_SERIALIZER.fromNetwork(recipeId, buffer);
            return recipe != null ? new FurnitureShapedRecipe(recipe) : null;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, FurnitureShapedRecipe recipe) {
            BASE_SERIALIZER.toNetwork(buffer, recipe.getBaseRecipe());
        }
    }

}
