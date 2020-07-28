package tk.themcbros.interiormod.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.data.LanguageProvider;
import tk.themcbros.interiormod.init.InteriorBlocks;
import tk.themcbros.interiormod.init.InteriorEntities;

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
        this.add(InteriorBlocks.FRIDGE.getTranslationKey() + ".desc0", "Can store food in it");
        this.add(InteriorBlocks.LAMP, "Nightlight Lamp");
        this.add(InteriorBlocks.LAMP.getTranslationKey() + ".desc0", "A lamp with an integrated");
        this.add(InteriorBlocks.LAMP.getTranslationKey() + ".desc1", "inverted daylight detector");
        this.add(InteriorBlocks.LAMP_ON_A_STICK, "Lamp on a Stick");
        this.add(InteriorBlocks.LAMP_ON_A_STICK.getTranslationKey() + ".desc0", "The Nightlight Lamp on two sticks");
        this.add(InteriorBlocks.TRASH_CAN, "Trash Can");
        this.add(InteriorBlocks.TRASH_CAN.getTranslationKey() + ".desc0", "Destroys any item that is put in");
        this.add(InteriorBlocks.MODERN_DOOR, "Modern Door");
        this.add(InteriorBlocks.MODERN_DOOR.getTranslationKey() + ".desc0", "A modern variant of the vanilla door");

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

        this.add("config.waila.plugin_interiormod", "TheMCBrothers Interior Mod");
        this.add("config.waila.plugin_interiormod.display_furniture_materials", "Furniture Materials");

        String s = String.format("%sHold %s%sShift %sfor Details", TextFormatting.GRAY, TextFormatting.YELLOW, TextFormatting.ITALIC, TextFormatting.GRAY);
        this.add("tooltip.interiormod.hold_shift", s);

        this.add(InteriorEntities.SEAT, "Seat");
        this.add("itemGroup.interiormod", "TheMCBrothers Interior Mod");
    }

    @Override
    public String getName() {
        return "Interior Mod Language: en_us";
    }
}
