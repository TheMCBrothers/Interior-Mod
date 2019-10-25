package tk.themcbros.interiormod.recipe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.google.common.collect.Lists;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.resources.IResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.api.furniture.IFurnitureMaterial;
import tk.themcbros.interiormod.furniture.FurnitureRegistry;

public class FurnitureRecipeManager implements ISelectiveResourceReloadListener {

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        RecipeManager recipeManager = server.getRecipeManager();
        recipeManager.recipes = new HashMap<>(recipeManager.recipes);
        recipeManager.recipes.replaceAll((t, v) -> new HashMap<>(recipeManager.recipes.get(t)));

        Map<ResourceLocation, IRecipe<?>> recipes = recipeManager.recipes.get(IRecipeType.CRAFTING);
        
        for (IFurnitureMaterial primary : FurnitureRegistry.MATERIALS) {
        	for (IFurnitureMaterial secondary : FurnitureRegistry.MATERIALS) {
            	ShapedRecipe chair = createChairRecipe(primary, secondary);
            	ShapedRecipe table = createTableRecipe(primary, secondary);
            	
            	if (chair != null)
            		recipes.put(chair.getId(), chair);
            	if (table != null)
            		recipes.put(table.getId(), table); 
    		}
		}
	}
	
	private static ShapedRecipe createChairRecipe(IFurnitureMaterial primary, IFurnitureMaterial secondary) {
		Ingredient primaryIngredient = primary.getIngredient();
		Ingredient secondaryIngredient = secondary.getIngredient();
		NonNullList<Ingredient> inputs = NonNullList.from(Ingredient.EMPTY,
				primaryIngredient, Ingredient.EMPTY, Ingredient.EMPTY,
				primaryIngredient, secondaryIngredient, secondaryIngredient,
				primaryIngredient, Ingredient.EMPTY, primaryIngredient
		);
		ItemStack output = FurnitureRegistry.createItemStack(FurnitureType.CHAIR, primary, secondary);
		
		ResourceLocation name = new ResourceLocation(InteriorMod.MOD_ID, "chair_" + primary.getSaveId().replace(':', '.') + "_" + secondary.getSaveId().replace(':', '.'));
		return new ShapedRecipe(name, "interiormod:chairs", 3, 3, inputs, output);
	}
	
	private static ShapedRecipe createTableRecipe(IFurnitureMaterial primary, IFurnitureMaterial secondary) {
		Ingredient primaryIngredient = primary.getIngredient();
		Ingredient secondaryIngredient = secondary.getIngredient();
		NonNullList<Ingredient> inputs = NonNullList.from(Ingredient.EMPTY,
				primaryIngredient, primaryIngredient, primaryIngredient,
				secondaryIngredient, Ingredient.EMPTY, secondaryIngredient,
				secondaryIngredient, Ingredient.EMPTY, secondaryIngredient
		);
		ItemStack output = FurnitureRegistry.createItemStack(FurnitureType.TABLE, primary, secondary);
	
		ResourceLocation name = new ResourceLocation(InteriorMod.MOD_ID, "table_" + primary.getSaveId().replace(':', '.') + "_" + secondary.getSaveId().replace(':', '.'));
		return new ShapedRecipe(name, "interiormod:tables", 3, 3, inputs, output);
	}
	
	public static List<IShapedRecipe<? extends IInventory>> createChairRecipes() {
        List<IShapedRecipe<? extends IInventory>> recipes = Lists.newArrayList();
        for(IFurnitureMaterial secondary : FurnitureRegistry.MATERIALS.getKeys()) {
            for(IFurnitureMaterial primary : FurnitureRegistry.MATERIALS.getKeys()) {
            	ShapedRecipe recipe = createChairRecipe(primary, secondary);
                recipes.add(recipe);
            }
        }
        return recipes;
    }
	
	public static List<IShapedRecipe<? extends IInventory>> createTableRecipes() {
        List<IShapedRecipe<? extends IInventory>> recipes = Lists.newArrayList();
        for(IFurnitureMaterial secondary : FurnitureRegistry.MATERIALS.getKeys()) {
            for(IFurnitureMaterial primary : FurnitureRegistry.MATERIALS.getKeys()) {
                ShapedRecipe recipe = createTableRecipe(primary, secondary);
                recipes.add(recipe);
            }
        }
        return recipes;
    }

}
