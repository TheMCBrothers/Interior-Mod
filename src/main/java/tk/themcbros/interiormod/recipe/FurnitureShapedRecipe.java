package tk.themcbros.interiormod.recipe;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import tk.themcbros.interiormod.init.InteriorBlocks;
import tk.themcbros.interiormod.init.InteriorRecipeSerializers;
import tk.themcbros.interiormod.init.InteriorRecipeTypes;

import javax.annotation.Nullable;

/**
 * @author TheMCBrothers
 */
public class FurnitureShapedRecipe extends ShapedRecipe {
    private static final IRecipeSerializer<ShapedRecipe> BASE_SERIALIZER = IRecipeSerializer.CRAFTING_SHAPED;

    private final ShapedRecipe recipe;

    public FurnitureShapedRecipe(ShapedRecipe recipe) {
        super(recipe.getId(), recipe.getGroup(), recipe.getRecipeWidth(), recipe.getRecipeHeight(), recipe.getIngredients(), recipe.getRecipeOutput());
        this.recipe = recipe;
    }

    public ShapedRecipe getBaseRecipe() {
        return recipe;
    }

    @Override
    public IRecipeType<?> getType() {
        return InteriorRecipeTypes.FURNITURE_CRAFTING;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return InteriorRecipeSerializers.FURNITURE_SHAPED;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(InteriorBlocks.FURNITURE_WORKBENCH);
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<FurnitureShapedRecipe> {
        @Override
        public FurnitureShapedRecipe read(ResourceLocation recipeId, JsonObject json) {
            return new FurnitureShapedRecipe(BASE_SERIALIZER.read(recipeId, json));
        }

        @Nullable
        @Override
        public FurnitureShapedRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            final ShapedRecipe recipe = BASE_SERIALIZER.read(recipeId, buffer);
            return recipe != null ? new FurnitureShapedRecipe(recipe) : null;
        }

        @Override
        public void write(PacketBuffer buffer, FurnitureShapedRecipe recipe) {
            BASE_SERIALIZER.write(buffer, recipe.getBaseRecipe());
        }
    }

}
