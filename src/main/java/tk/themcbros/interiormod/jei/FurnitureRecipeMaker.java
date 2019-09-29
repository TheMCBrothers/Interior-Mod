package tk.themcbros.interiormod.jei;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IShapedRecipe;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.furniture.FurnitureRegistry;
import tk.themcbros.interiormod.furniture.FurnitureType;
import tk.themcbros.interiormod.furniture.IFurnitureMaterial;

public class FurnitureRecipeMaker {

	public static List<IShapedRecipe<? extends IInventory>> createChairRecipes() {
        List<IShapedRecipe<? extends IInventory>> recipes = Lists.newArrayList();
        String group = "interiormod.chair";
        for(IFurnitureMaterial secondary : FurnitureRegistry.MATERIALS.getKeys()) {
            for(IFurnitureMaterial primary : FurnitureRegistry.MATERIALS.getKeys()) {
                
                Ingredient primaryIngredient = secondary.getIngredient();
                Ingredient secondaryIngredient = primary.getIngredient();
                NonNullList<Ingredient> inputs = NonNullList.from(Ingredient.EMPTY,
                		primaryIngredient, Ingredient.EMPTY, Ingredient.EMPTY,
                		primaryIngredient, secondaryIngredient, secondaryIngredient,
                		primaryIngredient, Ingredient.EMPTY, primaryIngredient
                );
                ItemStack output = FurnitureRegistry.createItemStack(FurnitureType.CHAIR, primary, secondary);
                
                ResourceLocation id = new ResourceLocation(InteriorMod.MOD_ID, output.getTranslationKey());
                ShapedRecipe recipe = new ShapedRecipe(id, group, 3, 3, inputs, output);
                recipes.add(recipe);
            }
        }
        return recipes;
    }
	
}
