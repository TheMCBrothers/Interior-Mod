package tk.themcbros.interiormod.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import tk.themcbros.interiormod.init.InteriorBlocks;
import tk.themcbros.interiormod.init.InteriorEntities;

public class InteriorLanguageProvider extends LanguageProvider {
	
	public InteriorLanguageProvider(DataGenerator gen, String modid) {
		super(gen, modid, "en_us");
	}

	@Override
	protected void addTranslations() {
		
		this.add("mat.interiormod.null", "Null");
		this.add("mat.interiormod.minecraft.oak_planks", "Oak");
		this.add("mat.interiormod.minecraft.spruce_planks", "Spruce");
		this.add("mat.interiormod.minecraft.birch_planks", "Birch");
		this.add("mat.interiormod.minecraft.jungle_planks", "Jungle");
		this.add("mat.interiormod.minecraft.acacia_planks", "Acacia");
		this.add("mat.interiormod.minecraft.dark_oak_planks", "Dark Oak");
		this.add("mat.interiormod.biomesoplenty.cherry_planks", "Cherry");
		this.add("mat.interiormod.biomesoplenty.dead_planks", "Dead");
		this.add("mat.interiormod.biomesoplenty.ethereal_planks", "Ethereal");
		this.add("mat.interiormod.biomesoplenty.fir_planks", "Fir");
		this.add("mat.interiormod.biomesoplenty.hellbark_planks", "Hellbark");
		this.add("mat.interiormod.biomesoplenty.jacaranda_planks", "Jacaranda");
		this.add("mat.interiormod.biomesoplenty.magic_planks", "Magic");
		this.add("mat.interiormod.biomesoplenty.mahogany_planks", "Mahogany");
		this.add("mat.interiormod.biomesoplenty.palm_planks", "Palm");
		this.add("mat.interiormod.biomesoplenty.redwood_planks", "Redwood");
		this.add("mat.interiormod.biomesoplenty.umbran_planks", "Umbran");
		this.add("mat.interiormod.biomesoplenty.willow_planks", "Willow");
		this.add("mat.interiormod.uselessmod.useless_planks", "Useless");
		
		this.add(InteriorBlocks.CHAIR, "Chair");
		this.add(InteriorBlocks.CHAIR.getTranslationKey() + ".name", "%s Chair");
		this.add(InteriorBlocks.TABLE, "Table");
		this.add(InteriorBlocks.TABLE.getTranslationKey() + ".name", "%s Table");
		this.add(InteriorBlocks.FRIDGE, "Fridge");
		this.add(InteriorBlocks.FRIDGE.getTranslationKey() + ".desc0", "description");
		this.add(InteriorBlocks.LAMP, "Nightlight Lamp");
		this.add(InteriorBlocks.LAMP.getTranslationKey() + ".desc0", "description");
		this.add(InteriorBlocks.LAMP_ON_A_STICK, "Lamp on a Stick");
		this.add(InteriorBlocks.LAMP_ON_A_STICK.getTranslationKey() + ".desc0", "description");
		this.add(InteriorBlocks.TRASH_CAN, "Trash Can");
		this.add(InteriorBlocks.TRASH_CAN.getTranslationKey() + ".desc0", "description");
		this.add(InteriorBlocks.MODERN_DOOR, "Modern Door");
		this.add(InteriorBlocks.MODERN_DOOR.getTranslationKey() + ".desc0", "description");
		
		this.add(InteriorEntities.SEAT, "Seat");
		this.add("itemGroup.interiormod", "TheMCBrothers Interior Mod");
	}

}
