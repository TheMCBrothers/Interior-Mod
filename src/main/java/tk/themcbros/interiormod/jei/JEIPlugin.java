package tk.themcbros.interiormod.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.furniture.FurnitureRegistry;
import tk.themcbros.interiormod.furniture.IFurnitureMaterial;
import tk.themcbros.interiormod.init.InteriorItems;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

	private final ResourceLocation PLUGIN_UID = new ResourceLocation(InteriorMod.MOD_ID, "jeiplugin");
	
	@Override
	public ResourceLocation getPluginUid() {
		return PLUGIN_UID;
	}
	
	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		registration.registerSubtypeInterpreter(InteriorItems.CHAIR, stack -> {
			if(!stack.hasTag() && !stack.getTag().contains("textures", Constants.NBT.TAG_COMPOUND)) return stack.getTranslationKey();
			CompoundNBT tag = stack.getTag().getCompound("textures");
			IFurnitureMaterial primary = FurnitureRegistry.MATERIALS.get(tag.getString("primary"));
			IFurnitureMaterial secondary = FurnitureRegistry.MATERIALS.get(tag.getString("secondary"));
			return primary.getSaveId() + secondary.getSaveId();
		});
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addRecipes(FurnitureRecipeMaker.createChairRecipes(), VanillaRecipeCategoryUid.CRAFTING);
	}

}
