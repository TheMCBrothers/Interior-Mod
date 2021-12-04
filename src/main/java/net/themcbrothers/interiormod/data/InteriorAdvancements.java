package net.themcbrothers.interiormod.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.PlacedBlockTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.themcbrothers.interiormod.init.FurnitureMaterials;
import net.themcbrothers.interiormod.init.InteriorBlocks;
import net.themcbrothers.interiormod.api.InteriorAPI;
import net.themcbrothers.interiormod.api.furniture.FurnitureMaterial;
import net.themcbrothers.interiormod.api.furniture.FurnitureType;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class InteriorAdvancements implements Consumer<Consumer<Advancement>> {
    @Override
    public void accept(Consumer<Advancement> advancementConsumer) {
        Advancement advancement = Advancement.Builder.advancement().display(this.rootIconStack(), this.title("root"), this.description("root"), new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), FrameType.TASK, false, false, false).addCriterion("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRAFTING_TABLE)).save(advancementConsumer, this.id("root"));
        Advancement.Builder.advancement().display(this.furnitureWithType(FurnitureType.CHAIR, FurnitureMaterials.CRIMSON_PLANKS), this.title("chair"), this.description("chair"), null, FrameType.TASK, true, true, false).addCriterion("chair", InventoryChangeTrigger.TriggerInstance.hasItems(InteriorBlocks.CHAIR)).parent(advancement).save(advancementConsumer, this.id("chair"));
        Advancement.Builder.advancement().display(this.furnitureWithType(FurnitureType.TABLE, FurnitureMaterials.WARPED_PLANKS), this.title("table"), this.description("table"), null, FrameType.TASK, true, true, false).addCriterion("table", InventoryChangeTrigger.TriggerInstance.hasItems(InteriorBlocks.TABLE)).parent(advancement).save(advancementConsumer, this.id("table"));
        Advancement.Builder.advancement().display(InteriorBlocks.FRIDGE, this.title("fridge"), this.description("fridge"), null, FrameType.TASK, true, true, false).addCriterion("fridge", InventoryChangeTrigger.TriggerInstance.hasItems(InteriorBlocks.FRIDGE)).parent(advancement).save(advancementConsumer, this.id("fridge"));
        Advancement advancement1 = Advancement.Builder.advancement().display(InteriorBlocks.LAMP, this.title("lamp"), this.description("lamp"), null, FrameType.TASK, true, true, false).addCriterion("lamp", InventoryChangeTrigger.TriggerInstance.hasItems(InteriorBlocks.LAMP)).parent(advancement).save(advancementConsumer, this.id("lamp"));
        Advancement.Builder.advancement().display(InteriorBlocks.LAMP_ON_A_STICK, this.title("lamp_on_a_stick"), this.description("lamp_on_a_stick"), null, FrameType.TASK, true, true, false).addCriterion("lamp_on_a_stick", PlacedBlockTrigger.TriggerInstance.placedBlock(InteriorBlocks.LAMP_ON_A_STICK)).parent(advancement1).save(advancementConsumer, this.id("lamp_on_a_stick"));
        Advancement.Builder.advancement().display(InteriorBlocks.TRASH_CAN, this.title("trash_can"), this.description("trash_can"), null, FrameType.TASK, true, true, false).addCriterion("trash_can", PlacedBlockTrigger.TriggerInstance.placedBlock(InteriorBlocks.TRASH_CAN)).parent(advancement).save(advancementConsumer, this.id("trash_can"));
        Advancement.Builder.advancement().display(InteriorBlocks.MODERN_DOOR, this.title("modern_door"), this.description("modern_door"), null, FrameType.TASK, true, true, false).addCriterion("modern_door", InventoryChangeTrigger.TriggerInstance.hasItems(InteriorBlocks.MODERN_DOOR)).parent(advancement).save(advancementConsumer, this.id("modern_door"));
    }

    private ItemStack furnitureWithType(FurnitureType type, Supplier<FurnitureMaterial> material) {
        return FurnitureMaterials.createItemStack(type, material.get(), material.get());
    }

    private ItemStack rootIconStack() {
        return FurnitureMaterials.createItemStack(FurnitureType.CHAIR, FurnitureMaterials.BIRCH_PLANKS.get(), FurnitureMaterials.DARK_OAK_PLANKS.get());
    }

    private String id(String name) {
        return InteriorAPI.MOD_ID + ":" + InteriorAPI.MOD_ID + "/" + name;
    }

    private Component title(String name) {
        return new TranslatableComponent("advancement." + InteriorAPI.MOD_ID + "." + name + ".title");
    }

    private Component description(String name) {
        return new TranslatableComponent("advancement." + InteriorAPI.MOD_ID + "." + name + ".description");
    }
}
