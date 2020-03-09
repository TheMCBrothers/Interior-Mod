package tk.themcbros.interiormod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import tk.themcbros.interiormod.container.FridgeContainer;
import tk.themcbros.interiormod.init.InteriorTileEntities;

public class FridgeTileEntity extends TileEntity implements IInventory, INamedContainerProvider {

	private final NonNullList<ItemStack> stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
	
	public FridgeTileEntity() {
		super(InteriorTileEntities.FRIDGE);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		ItemStackHelper.saveAllItems(compound, this.stacks);
		return super.write(compound);
	}
	
	@Override
	public void read(CompoundNBT compound) {
		ItemStackHelper.loadAllItems(compound, this.stacks);
		super.read(compound);
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.stacks, index, count);
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.stacks, index);
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		return this.stacks.get(index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.stacks.set(index, stack);
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemStack : stacks) {
			if (!itemStack.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void clear() {
		this.stacks.clear();
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		assert this.world != null;
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return !(player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
					(double) this.pos.getZ() + 0.5D) > 64.0D);
		}
	}

	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new FridgeContainer(id, this, playerInventory);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.interiormod.fridge");
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && !this.removed) {
			return LazyOptional.of(() -> new InvWrapper(this)).cast();
		}
		return super.getCapability(cap, side);
	}

}
