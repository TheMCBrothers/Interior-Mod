package tk.themcbros.interiormod.data;

import java.awt.geom.RectangularShape;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import tk.themcbros.interiormod.api.InteriorAPI;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.api.furniture.InteriorRegistries;
import tk.themcbros.interiormod.init.FurnitureMaterials;
import tk.themcbros.interiormod.init.InteriorItems;

/**
 * @author TheMCBrothers
 */
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

        // TODO remove when dynamic recipes work
        for (FurnitureMaterial material : InteriorRegistries.FURNITURE_MATERIALS) {
            ItemStack chair = FurnitureMaterials.createItemStack(FurnitureType.CHAIR, material, material);
            ItemStack table = FurnitureMaterials.createItemStack(FurnitureType.TABLE, material, material);

            CustomShapedRecipeBuilder.shapedRecipe(chair)
                    .patternLine("#  ")
                    .patternLine("###")
                    .patternLine("# #")
                    .key('#', material.getIngredient())
                    .addCriterion("has_material", InventoryChangeTrigger.Instance.forItems(Arrays.stream(material.getIngredient().getMatchingStacks()).map(ItemStack::getItem).toArray(Item[]::new)))
                    .build(consumer, new ResourceLocation(InteriorAPI.MOD_ID, "chair_" + String.valueOf(material.getRegistryName()).replace(':', '_')));
            CustomShapedRecipeBuilder.shapedRecipe(table)
                    .patternLine("###")
                    .patternLine("# #")
                    .patternLine("# #")
                    .key('#', material.getIngredient())
                    .addCriterion("has_material", InventoryChangeTrigger.Instance.forItems(Arrays.stream(material.getIngredient().getMatchingStacks()).map(ItemStack::getItem).toArray(Item[]::new)))
                    .build(consumer, new ResourceLocation(InteriorAPI.MOD_ID, "table_" + String.valueOf(material.getRegistryName()).replace(':', '_')));
        }
    }

    @Override
    public String getName() {
        return "Interior Mod Recipes";
    }

}
