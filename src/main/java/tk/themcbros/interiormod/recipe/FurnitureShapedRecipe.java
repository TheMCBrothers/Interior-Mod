package tk.themcbros.interiormod.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.registries.ForgeRegistryEntry;
import tk.themcbros.interiormod.init.InteriorBlocks;
import tk.themcbros.interiormod.init.InteriorRecipeSerializers;
import tk.themcbros.interiormod.init.InteriorRecipeTypes;

import javax.annotation.Nullable;

/**
 * @author TheMCBrothers
 */
public class FurnitureShapedRecipe extends ShapedRecipe {
    private static final RecipeSerializer<ShapedRecipe> BASE_SERIALIZER = RecipeSerializer.SHAPED_RECIPE;

    private final ShapedRecipe recipe;

    public FurnitureShapedRecipe(ShapedRecipe recipe) {
        super(recipe.getId(), recipe.getGroup(), recipe.getRecipeWidth(), recipe.getRecipeHeight(), recipe.getIngredients(), recipe.getResultItem());
        this.recipe = recipe;
    }

    public ShapedRecipe getBaseRecipe() {
        return recipe;
    }

    @Override
    public RecipeType<?> getType() {
        return InteriorRecipeTypes.FURNITURE_CRAFTING;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InteriorRecipeSerializers.FURNITURE_SHAPED;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(InteriorBlocks.FURNITURE_WORKBENCH);
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<FurnitureShapedRecipe> {
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
