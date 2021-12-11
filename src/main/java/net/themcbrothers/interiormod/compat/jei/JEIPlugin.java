package net.themcbrothers.interiormod.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.themcbrothers.interiormod.InteriorMod;
import net.themcbrothers.interiormod.client.screen.FurnitureWorkbenchScreen;
import net.themcbrothers.interiormod.container.FurnitureWorkbenchMenu;
import net.themcbrothers.interiormod.init.InteriorBlocks;
import net.themcbrothers.interiormod.init.InteriorItems;

/**
 * @author TheMCBrothers
 */
@JeiPlugin
public class JEIPlugin implements IModPlugin {

    private final ResourceLocation PLUGIN_UID = new ResourceLocation(InteriorMod.MOD_ID, "jeiplugin");

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_UID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.useNbtForSubtypes(InteriorItems.CHAIR, InteriorItems.TABLE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        InteriorRecipes recipes = new InteriorRecipes();
        registration.addRecipes(recipes.getFurnitureCraftingRecipes(), InteriorRecipeCategoryUid.FURNITURE_CRAFTING);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registration.addRecipeCategories(
                new FurnitureCraftingRecipeCategory(guiHelper)
        );
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(FurnitureWorkbenchScreen.class, 88, 32, 28, 23, InteriorRecipeCategoryUid.FURNITURE_CRAFTING);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(FurnitureWorkbenchMenu.class, InteriorRecipeCategoryUid.FURNITURE_CRAFTING, 1, 9, 10, 36);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(InteriorBlocks.FURNITURE_WORKBENCH), InteriorRecipeCategoryUid.FURNITURE_CRAFTING);
    }
}