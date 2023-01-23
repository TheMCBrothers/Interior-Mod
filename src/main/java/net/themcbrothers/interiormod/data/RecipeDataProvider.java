package net.themcbrothers.interiormod.data;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.themcbrothers.interiormod.init.InteriorItems;

import java.util.function.Consumer;

/**
 * @author TheMCBrothers
 */
public class RecipeDataProvider extends RecipeProvider {

    public RecipeDataProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, InteriorItems.FRIDGE.get())
                .pattern("II")
                .pattern("II")
                .pattern("BC")
                .define('I', Tags.Items.STORAGE_BLOCKS_IRON).define('B', Items.IRON_BARS).define('C', Tags.Items.CHESTS_WOODEN)
                .unlockedBy("hasIronBlock", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(Tags.Items.STORAGE_BLOCKS_IRON).build()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, InteriorItems.LAMP.get())
                .pattern("D")
                .pattern("L")
                .define('D', Items.DAYLIGHT_DETECTOR).define('L', Items.REDSTONE_LAMP)
                .unlockedBy("hasItems", InventoryChangeTrigger.TriggerInstance.hasItems(Items.REDSTONE_LAMP, Items.DAYLIGHT_DETECTOR))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, InteriorItems.LAMP_ON_A_STICK.get())
                .pattern("L")
                .pattern("S")
                .pattern("S")
                .define('L', InteriorItems.LAMP.get()).define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("hasLamp", InventoryChangeTrigger.TriggerInstance.hasItems(InteriorItems.LAMP.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, InteriorItems.TRASH_CAN.get())
                .pattern("ICI")
                .pattern(" B ")
                .define('I', Tags.Items.INGOTS_IRON).define('C', Tags.Items.CHESTS_WOODEN).define('B', Items.IRON_BARS)
                .unlockedBy("hasMaterial", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(Tags.Items.INGOTS_IRON).build(),
                        ItemPredicate.Builder.item().of(Tags.Items.CHESTS_WOODEN).build(),
                        ItemPredicate.Builder.item().of(Items.IRON_BARS).build()
                ))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, InteriorItems.MODERN_DOOR.get())
                .pattern("#G")
                .pattern("##")
                .pattern("##")
                .define('#', Items.WHITE_CONCRETE).define('G', Tags.Items.GLASS_PANES_COLORLESS)
                .unlockedBy("hasConcrete", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WHITE_CONCRETE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, InteriorItems.FURNITURE_WORKBENCH.get())
                .pattern("X")
                .pattern("#")
                .define('#', Items.CRAFTING_TABLE).define('X', Tags.Items.FEATHERS)
                .unlockedBy("hasFeather", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(Tags.Items.FEATHERS).build())
                ).save(consumer);
    }
}
