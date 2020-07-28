package tk.themcbros.interiormod.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.PlacedBlockTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import tk.themcbros.interiormod.api.InteriorAPI;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.init.FurnitureMaterials;
import tk.themcbros.interiormod.init.InteriorBlocks;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class InteriorAdvancements implements Consumer<Consumer<Advancement>> {
    @Override
    public void accept(Consumer<Advancement> advancementConsumer) {
        Advancement advancement = Advancement.Builder.builder().withDisplay(this.rootIconStack(), this.title("root"), this.description("root"), new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), FrameType.TASK, false, false, false).withCriterion("crafting_table", InventoryChangeTrigger.Instance.forItems(Blocks.CRAFTING_TABLE)).register(advancementConsumer, this.id("root"));
        Advancement.Builder.builder().withDisplay(this.furnitureWithType(FurnitureType.CHAIR, FurnitureMaterials.CRIMSON_PLANKS), this.title("chair"), this.description("chair"), null, FrameType.TASK, true, true, false).withCriterion("chair", InventoryChangeTrigger.Instance.forItems(InteriorBlocks.CHAIR)).withParent(advancement).register(advancementConsumer, this.id("chair"));
        Advancement.Builder.builder().withDisplay(this.furnitureWithType(FurnitureType.TABLE, FurnitureMaterials.WARPED_PLANKS), this.title("table"), this.description("table"), null, FrameType.TASK, true, true, false).withCriterion("table", InventoryChangeTrigger.Instance.forItems(InteriorBlocks.TABLE)).withParent(advancement).register(advancementConsumer, this.id("table"));
        Advancement.Builder.builder().withDisplay(InteriorBlocks.FRIDGE, this.title("fridge"), this.description("fridge"), null, FrameType.TASK, true, true, false).withCriterion("fridge", InventoryChangeTrigger.Instance.forItems(InteriorBlocks.FRIDGE)).withParent(advancement).register(advancementConsumer, this.id("fridge"));
        Advancement advancement1 = Advancement.Builder.builder().withDisplay(InteriorBlocks.LAMP, this.title("lamp"), this.description("lamp"), null, FrameType.TASK, true, true, false).withCriterion("lamp", InventoryChangeTrigger.Instance.forItems(InteriorBlocks.LAMP)).withParent(advancement).register(advancementConsumer, this.id("lamp"));
        Advancement.Builder.builder().withDisplay(InteriorBlocks.LAMP_ON_A_STICK, this.title("lamp_on_a_stick"), this.description("lamp_on_a_stick"), null, FrameType.TASK, true, true, false).withCriterion("lamp_on_a_stick", PlacedBlockTrigger.Instance.placedBlock(InteriorBlocks.LAMP_ON_A_STICK)).withParent(advancement1).register(advancementConsumer, this.id("lamp_on_a_stick"));
        Advancement.Builder.builder().withDisplay(InteriorBlocks.TRASH_CAN, this.title("trash_can"), this.description("trash_can"), null, FrameType.TASK, true, true, false).withCriterion("trash_can", PlacedBlockTrigger.Instance.placedBlock(InteriorBlocks.TRASH_CAN)).withParent(advancement).register(advancementConsumer, this.id("trash_can"));
        Advancement.Builder.builder().withDisplay(InteriorBlocks.MODERN_DOOR, this.title("modern_door"), this.description("modern_door"), null, FrameType.TASK, true, true, false).withCriterion("modern_door", InventoryChangeTrigger.Instance.forItems(InteriorBlocks.MODERN_DOOR)).withParent(advancement).register(advancementConsumer, this.id("modern_door"));
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

    private ITextComponent title(String name) {
        return new TranslationTextComponent("advancement." + InteriorAPI.MOD_ID + "." + name + ".title");
    }

    private ITextComponent description(String name) {
        return new TranslationTextComponent("advancement." + InteriorAPI.MOD_ID + "." + name + ".description");
    }
}
