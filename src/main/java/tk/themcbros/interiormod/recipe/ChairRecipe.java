package tk.themcbros.interiormod.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IShapedRecipe;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.api.furniture.IFurnitureMaterial;
import tk.themcbros.interiormod.furniture.FurnitureRegistry;
import tk.themcbros.interiormod.init.InteriorRecipeSerializers;

public class ChairRecipe extends SpecialRecipe implements IShapedRecipe<CraftingInventory> {

	public ChairRecipe(ResourceLocation key) {
		super(key);
	}

	@Override
    public boolean matches(CraftingInventory inv, World worldIn) {
		IFurnitureMaterial primaryId = IFurnitureMaterial.NULL;
        IFurnitureMaterial secondaryId = IFurnitureMaterial.NULL;
        
        for(int col = 0; col < 3; ++col) {
            for(int row = 0; row < 3; ++row) {
            	
            	if ((col == 0 && row >= 0 && row < 3) || (col == 2 && row > 0 && row < 3)) {
            		IFurnitureMaterial id = FurnitureRegistry.MATERIALS.getFromStack(inv.getStackInSlot(col + row * inv.getWidth()));
            		if(id == IFurnitureMaterial.NULL || (primaryId != IFurnitureMaterial.NULL && id != primaryId))
            			return false;
            		
            		primaryId = id;
            	} else if (col >= 1 && col < 3 && row == 1) {
            		IFurnitureMaterial id = FurnitureRegistry.MATERIALS.getFromStack(inv.getStackInSlot(col + row * inv.getWidth()));
            		if(id == IFurnitureMaterial.NULL || (secondaryId != IFurnitureMaterial.NULL && id != secondaryId))
            			return false;
            		
            		secondaryId = id;
            	} else {
            		if(!inv.getStackInSlot(col + row * inv.getWidth()).isEmpty())
            			return false;
            	}
            }
        }

        return true;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        IFurnitureMaterial secondaryId = FurnitureRegistry.MATERIALS.getFromStack(inv.getStackInSlot(4));
        IFurnitureMaterial primaryId = FurnitureRegistry.MATERIALS.getFromStack(inv.getStackInSlot(0));
        
        return FurnitureRegistry.createItemStack(FurnitureType.CHAIR, primaryId, secondaryId);
    }
    
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for(int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
        }

        return nonnulllist;
    }

    //Is on a 3x3 grid or bigger
    @Override
    public boolean canFit(int width, int height) {
        return width >= 3 && height >= 3;
    }


    @Override
    public IRecipeSerializer<?> getSerializer() {
        return InteriorRecipeSerializers.CHAIR;
    }

    @Override
    public int getRecipeWidth() {
        return 3;
    }

    @Override
    public int getRecipeHeight() {
        return 3;
    }

}
