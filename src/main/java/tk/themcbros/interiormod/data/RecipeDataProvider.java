package tk.themcbros.interiormod.data;

import java.util.function.Consumer;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import tk.themcbros.interiormod.init.InteriorItems;

public class RecipeDataProvider extends RecipeProvider {

	public RecipeDataProvider(DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapedRecipeBuilder.shapedRecipe(InteriorItems.FRIDGE)
				.patternLine("II")
				.patternLine("II")
				.patternLine("BC")
				.key('I', Tags.Items.STORAGE_BLOCKS_IRON).key('B', Items.IRON_BARS).key('C', Tags.Items.CHESTS_WOODEN)
				.addCriterion("hasIronBlock", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(Tags.Items.STORAGE_BLOCKS_IRON).build()))
				.build(consumer);

		ShapedRecipeBuilder.shapedRecipe(InteriorItems.LAMP)
				.patternLine("D")
				.patternLine("L")
				.key('D', Items.DAYLIGHT_DETECTOR).key('L', Items.REDSTONE_LAMP)
				.addCriterion("hasItems", InventoryChangeTrigger.Instance.forItems(Items.REDSTONE_LAMP, Items.DAYLIGHT_DETECTOR))
				.build(consumer);

		ShapedRecipeBuilder.shapedRecipe(InteriorItems.LAMP_ON_A_STICK)
				.patternLine("L")
				.patternLine("S")
				.patternLine("S")
				.key('L', InteriorItems.LAMP).key('S', Tags.Items.RODS_WOODEN)
				.addCriterion("hasLamp", InventoryChangeTrigger.Instance.forItems(InteriorItems.LAMP))
				.build(consumer);

		ShapedRecipeBuilder.shapedRecipe(InteriorItems.TRASH_CAN)
				.patternLine("ICI")
				.patternLine(" B ")
				.key('I', Tags.Items.INGOTS_IRON).key('C', Tags.Items.CHESTS_WOODEN).key('B', Items.IRON_BARS)
				.addCriterion("hasMaterial", InventoryChangeTrigger.Instance.forItems(
						ItemPredicate.Builder.create().tag(Tags.Items.INGOTS_IRON).build(),
						ItemPredicate.Builder.create().tag(Tags.Items.CHESTS_WOODEN).build(),
						ItemPredicate.Builder.create().item(Items.IRON_BARS).build()
				))
				.build(consumer);

		ShapedRecipeBuilder.shapedRecipe(InteriorItems.MODERN_DOOR)
				.patternLine("#G")
				.patternLine("##")
				.patternLine("##")
				.key('#', Items.WHITE_CONCRETE).key('G', Tags.Items.GLASS_PANES_COLORLESS)
				.addCriterion("hasConcrete", InventoryChangeTrigger.Instance.forItems(Items.WHITE_CONCRETE))
				.build(consumer);
	}
	
	@Override
	public String getName() {
		return "Interior Mod Recipes";
	}

}
