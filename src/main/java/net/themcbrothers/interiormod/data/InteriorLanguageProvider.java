package net.themcbrothers.interiormod.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.themcbrothers.interiormod.init.InteriorBlocks;
import net.themcbrothers.interiormod.init.InteriorEntities;

/**
 * @author TheMCBrothers
 */
public class InteriorLanguageProvider extends LanguageProvider {

    public InteriorLanguageProvider(DataGenerator gen, String modId) {
        super(gen, modId, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add(InteriorBlocks.CHAIR, "Chair");
        this.add(InteriorBlocks.TABLE, "Table");
        this.add(InteriorBlocks.FRIDGE, "Fridge");
        this.add(InteriorBlocks.FRIDGE.getDescriptionId() + ".tooltip", "Can store food in it");
        this.add(InteriorBlocks.LAMP, "Nightlight Lamp");
        this.add(InteriorBlocks.LAMP.getDescriptionId() + ".tooltip", "A lamp with an integrated\ninverted daylight detector");
        this.add(InteriorBlocks.LAMP_ON_A_STICK, "Lamp on a Stick");
        this.add(InteriorBlocks.LAMP_ON_A_STICK.getDescriptionId() + ".tooltip", "The Nightlight Lamp on two sticks");
        this.add(InteriorBlocks.TRASH_CAN, "Trash Can");
        this.add(InteriorBlocks.TRASH_CAN.getDescriptionId() + ".tooltip", "Destroys any item that is put in");
        this.add(InteriorBlocks.MODERN_DOOR, "Modern Door");
        this.add(InteriorBlocks.MODERN_DOOR.getDescriptionId() + ".tooltip", "A modern variant of the vanilla door");
        this.add(InteriorBlocks.FURNITURE_WORKBENCH, "Furniture Workbench");
        this.add(InteriorBlocks.FURNITURE_WORKBENCH.getDescriptionId() + ".tooltip", "Construct your customized furniture");

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

        this.add("config.waila.plugin_interiormod", "TheMCBrothers Interior Mod");
        this.add("config.waila.plugin_interiormod.display_furniture_materials", "Furniture Materials");

        this.add(InteriorEntities.SEAT, "Seat");
        this.add("itemGroup.interiormod", "TheMCBrothers Interior Mod");
    }

    @Override
    public String getName() {
        return "Interior Mod Language: en_us";
    }
}
