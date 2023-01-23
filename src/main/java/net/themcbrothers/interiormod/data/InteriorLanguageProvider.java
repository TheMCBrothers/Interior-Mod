package net.themcbrothers.interiormod.data;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.themcbrothers.interiormod.init.InteriorBlocks;
import net.themcbrothers.interiormod.init.InteriorEntityTypes;

/**
 * @author TheMCBrothers
 */
public class InteriorLanguageProvider extends LanguageProvider {

    public InteriorLanguageProvider(PackOutput packOutput, String modId) {
        super(packOutput, modId, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addBlock(InteriorBlocks.CHAIR, "Chair");
        this.addBlock(InteriorBlocks.TABLE, "Table");
        this.addBlock(InteriorBlocks.FRIDGE, "Fridge");
        this.add(InteriorBlocks.FRIDGE.get().getDescriptionId() + ".tooltip", "Can store food in it");
        this.addBlock(InteriorBlocks.LAMP, "Nightlight Lamp");
        this.add(InteriorBlocks.LAMP.get().getDescriptionId() + ".tooltip", "A lamp with an integrated\ninverted daylight detector");
        this.addBlock(InteriorBlocks.LAMP_ON_A_STICK, "Lamp on a Stick");
        this.add(InteriorBlocks.LAMP_ON_A_STICK.get().getDescriptionId() + ".tooltip", "The Nightlight Lamp on two sticks");
        this.addBlock(InteriorBlocks.TRASH_CAN, "Trash Can");
        this.add(InteriorBlocks.TRASH_CAN.get().getDescriptionId() + ".tooltip", "Destroys any item that is put in");
        this.addBlock(InteriorBlocks.MODERN_DOOR, "Modern Door");
        this.add(InteriorBlocks.MODERN_DOOR.get().getDescriptionId() + ".tooltip", "A modern variant of the vanilla door");
        this.addBlock(InteriorBlocks.FURNITURE_WORKBENCH, "Furniture Workbench");
        this.add(InteriorBlocks.FURNITURE_WORKBENCH.get().getDescriptionId() + ".tooltip", "Construct your customized furniture");

        this.add("advancement.interiormod.root.title", "TheMCBrothers Interior Mod");
        this.add("advancement.interiormod.root.description", "Furniture freely customizable");
        this.add("advancement.interiormod.chair.title", "Just sit down");
        this.add("advancement.interiormod.chair.description", "Craft a chair");
        this.add("advancement.interiormod.table.title", "Table alert");
        this.add("advancement.interiormod.table.description", "Craft a table");
        this.add("advancement.interiormod.modern_door.title", "Modernize your Home");
        this.add("advancement.interiormod.modern_door.description", "Craft a modern door for your home");
        this.add("advancement.interiormod.trash_can.title", "Throw away your Stuff");
        this.add("advancement.interiormod.trash_can.description", "Place a trash can to throw items away");
        this.add("advancement.interiormod.fridge.title", "Store your Food");
        this.add("advancement.interiormod.fridge.description", "Craft a fridge for your kitchen");
        this.add("advancement.interiormod.lamp.title", "Automatic Lights");
        this.add("advancement.interiormod.lamp.description", "Craft a Nightlight Lamp");
        this.add("advancement.interiormod.lamp_on_a_stick.title", "Higher Tier Lamp");
        this.add("advancement.interiormod.lamp_on_a_stick.description", "Place the upgraded Nightlight Lamp");

        this.add("container.interiormod.fridge", "Fridge");
        this.add("container.interiormod.trash_can", "Trash Can");
        this.add("container.interiormod.furniture_crafting", "Furniture Crafting");

        this.add("stat.interiormod.sit_down", "Chairs Sat");
        this.add("stat.interiormod.interact_with_furniture_workbench", "Interactions with Furniture Workbench");

        this.addEntityType(InteriorEntityTypes.SEAT, "Seat");
        this.add("itemGroup.interiormod", "TheMCBrothers Interior Mod");
    }

    @Override
    public String getName() {
        return "Interior Mod Language: en_us";
    }
}
