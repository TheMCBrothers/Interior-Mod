package tk.themcbros.interiormod.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import tk.themcbros.interiormod.init.InteriorContainers;
import tk.themcbros.interiormod.tileentity.FridgeTileEntity;

public class FridgeContainer extends Container {

	private FridgeTileEntity fridge;
	
	public FridgeContainer(int id, PlayerInventory playerInventory) {
		this(id, new FridgeTileEntity(), playerInventory);
	}
	
	public FridgeContainer(int id, FridgeTileEntity fridge, PlayerInventory playerInventory) {
		super(InteriorContainers.FRIDGE, id);
		this.fridge = fridge;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return fridge.isUsableByPlayer(playerIn);
	}

}
