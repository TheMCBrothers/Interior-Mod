package net.themcbrothers.interiormod.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.PlacedBlockTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.themcbrothers.interiormod.InteriorMod;
import net.themcbrothers.interiormod.api.InteriorAPI;
import net.themcbrothers.interiormod.api.furniture.FurnitureMaterial;
import net.themcbrothers.interiormod.api.furniture.FurnitureType;
import net.themcbrothers.interiormod.init.FurnitureMaterials;
import net.themcbrothers.interiormod.init.InteriorBlocks;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class InteriorAdvancementProvider extends AdvancementProvider {

    public InteriorAdvancementProvider(DataGenerator generatorIn, ExistingFileHelper fileHelper) {
        super(generatorIn, fileHelper);
    }

    @Override
    protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {
        Advancement advancement = Advancement.Builder.advancement().display(this.rootIconStack(), this.title("root"), this.description("root"), new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), FrameType.TASK, false, false, false).addCriterion("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRAFTING_TABLE)).save(consumer, this.id("root"), fileHelper);
        Advancement.Builder.advancement().display(this.furnitureWithType(FurnitureType.CHAIR, FurnitureMaterials.CRIMSON_PLANKS), this.title("chair"), this.description("chair"), null, FrameType.TASK, true, true, false).addCriterion("chair", InventoryChangeTrigger.TriggerInstance.hasItems(InteriorBlocks.CHAIR)).parent(advancement).save(consumer, this.id("chair"), fileHelper);
        Advancement.Builder.advancement().display(this.furnitureWithType(FurnitureType.TABLE, FurnitureMaterials.WARPED_PLANKS), this.title("table"), this.description("table"), null, FrameType.TASK, true, true, false).addCriterion("table", InventoryChangeTrigger.TriggerInstance.hasItems(InteriorBlocks.TABLE)).parent(advancement).save(consumer, this.id("table"), fileHelper);
        Advancement.Builder.advancement().display(InteriorBlocks.FRIDGE, this.title("fridge"), this.description("fridge"), null, FrameType.TASK, true, true, false).addCriterion("fridge", InventoryChangeTrigger.TriggerInstance.hasItems(InteriorBlocks.FRIDGE)).parent(advancement).save(consumer, this.id("fridge"), fileHelper);
        Advancement advancement1 = Advancement.Builder.advancement().display(InteriorBlocks.LAMP, this.title("lamp"), this.description("lamp"), null, FrameType.TASK, true, true, false).addCriterion("lamp", InventoryChangeTrigger.TriggerInstance.hasItems(InteriorBlocks.LAMP)).parent(advancement).save(consumer, this.id("lamp"), fileHelper);
        Advancement.Builder.advancement().display(InteriorBlocks.LAMP_ON_A_STICK, this.title("lamp_on_a_stick"), this.description("lamp_on_a_stick"), null, FrameType.TASK, true, true, false).addCriterion("lamp_on_a_stick", PlacedBlockTrigger.TriggerInstance.placedBlock(InteriorBlocks.LAMP_ON_A_STICK)).parent(advancement1).save(consumer, this.id("lamp_on_a_stick"), fileHelper);
        Advancement.Builder.advancement().display(InteriorBlocks.TRASH_CAN, this.title("trash_can"), this.description("trash_can"), null, FrameType.TASK, true, true, false).addCriterion("trash_can", PlacedBlockTrigger.TriggerInstance.placedBlock(InteriorBlocks.TRASH_CAN)).parent(advancement).save(consumer, this.id("trash_can"), fileHelper);
        Advancement.Builder.advancement().display(InteriorBlocks.MODERN_DOOR, this.title("modern_door"), this.description("modern_door"), null, FrameType.TASK, true, true, false).addCriterion("modern_door", InventoryChangeTrigger.TriggerInstance.hasItems(InteriorBlocks.MODERN_DOOR)).parent(advancement).save(consumer, this.id("modern_door"), fileHelper);
    }

    private ItemStack furnitureWithType(FurnitureType type, Supplier<FurnitureMaterial> material) {
        return FurnitureMaterials.createItemStack(type, material.get(), material.get());
    }

    private ItemStack rootIconStack() {
        return FurnitureMaterials.createItemStack(FurnitureType.CHAIR, FurnitureMaterials.BIRCH_PLANKS.get(), FurnitureMaterials.DARK_OAK_PLANKS.get());
    }

    private ResourceLocation id(String name) {
        return InteriorMod.getId(InteriorAPI.MOD_ID + "/" + name);
    }

    private Component title(String name) {
        return new TranslatableComponent("advancement." + InteriorAPI.MOD_ID + "." + name + ".title");
    }

    private Component description(String name) {
        return new TranslatableComponent("advancement." + InteriorAPI.MOD_ID + "." + name + ".description");
    }

    @Override
    public String getName() {
        return super.getName() + ": interiormod";
    }
}
