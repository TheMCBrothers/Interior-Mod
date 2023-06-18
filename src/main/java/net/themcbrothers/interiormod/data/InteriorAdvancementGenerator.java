package net.themcbrothers.interiormod.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.themcbrothers.interiormod.InteriorMod;
import net.themcbrothers.interiormod.api.InteriorAPI;
import net.themcbrothers.interiormod.api.furniture.FurnitureMaterial;
import net.themcbrothers.interiormod.api.furniture.FurnitureType;
import net.themcbrothers.interiormod.init.FurnitureMaterials;
import net.themcbrothers.interiormod.init.InteriorBlocks;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class InteriorAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        Advancement advancement = Advancement.Builder.advancement().display(this.rootIconStack(), this.title("root"), this.description("root"), new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), FrameType.TASK, false, false, false).addCriterion("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRAFTING_TABLE)).save(saver, this.id("root"), existingFileHelper);
        Advancement.Builder.advancement().display(this.furnitureWithType(FurnitureType.CHAIR, FurnitureMaterials.CRIMSON_PLANKS), this.title("chair"), this.description("chair"), null, FrameType.TASK, true, true, false).addCriterion("chair", InventoryChangeTrigger.TriggerInstance.hasItems(InteriorBlocks.CHAIR.get())).parent(advancement).save(saver, this.id("chair"), existingFileHelper);
        Advancement.Builder.advancement().display(this.furnitureWithType(FurnitureType.TABLE, FurnitureMaterials.WARPED_PLANKS), this.title("table"), this.description("table"), null, FrameType.TASK, true, true, false).addCriterion("table", InventoryChangeTrigger.TriggerInstance.hasItems(InteriorBlocks.TABLE.get())).parent(advancement).save(saver, this.id("table"), existingFileHelper);
        Advancement.Builder.advancement().display(InteriorBlocks.FRIDGE.get(), this.title("fridge"), this.description("fridge"), null, FrameType.TASK, true, true, false).addCriterion("fridge", InventoryChangeTrigger.TriggerInstance.hasItems(InteriorBlocks.FRIDGE.get())).parent(advancement).save(saver, this.id("fridge"), existingFileHelper);
        Advancement advancement1 = Advancement.Builder.advancement().display(InteriorBlocks.LAMP.get(), this.title("lamp"), this.description("lamp"), null, FrameType.TASK, true, true, false).addCriterion("lamp", InventoryChangeTrigger.TriggerInstance.hasItems(InteriorBlocks.LAMP.get())).parent(advancement).save(saver, this.id("lamp"), existingFileHelper);
        Advancement.Builder.advancement().display(InteriorBlocks.LAMP_ON_A_STICK.get(), this.title("lamp_on_a_stick"), this.description("lamp_on_a_stick"), null, FrameType.TASK, true, true, false).addCriterion("lamp_on_a_stick", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(InteriorBlocks.LAMP_ON_A_STICK.get())).parent(advancement1).save(saver, this.id("lamp_on_a_stick"), existingFileHelper);
        Advancement.Builder.advancement().display(InteriorBlocks.TRASH_CAN.get(), this.title("trash_can"), this.description("trash_can"), null, FrameType.TASK, true, true, false).addCriterion("trash_can", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(InteriorBlocks.TRASH_CAN.get())).parent(advancement).save(saver, this.id("trash_can"), existingFileHelper);
        Advancement.Builder.advancement().display(InteriorBlocks.MODERN_DOOR.get(), this.title("modern_door"), this.description("modern_door"), null, FrameType.TASK, true, true, false).addCriterion("modern_door", InventoryChangeTrigger.TriggerInstance.hasItems(InteriorBlocks.MODERN_DOOR.get())).parent(advancement).save(saver, this.id("modern_door"), existingFileHelper);
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
        return Component.translatable("advancement." + InteriorAPI.MOD_ID + "." + name + ".title");
    }

    private Component description(String name) {
        return Component.translatable("advancement." + InteriorAPI.MOD_ID + "." + name + ".description");
    }
}
