package tk.themcbros.interiormod.data;

import java.util.function.Consumer;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraftforge.common.Tags;
import tk.themcbros.interiormod.init.InteriorItems;

public class RecipeDataProvider extends RecipeProvider {

	public RecipeDataProvider(DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapedRecipeBuilder.shapedRecipe(InteriorItems.LAMP).patternLine("L").patternLine("S").patternLine("S").key('L', InteriorItems.TABLE)
				.key('S', Tags.Items.RODS_WOODEN).addCriterion("hasLamp", InventoryChangeTrigger.Instance.forItems(InteriorItems.TABLE));
	}
	
	@Override
	public String getName() {
		return "Interior Mod Recipes";
	}

}
