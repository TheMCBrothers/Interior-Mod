package tk.themcbros.interiormod.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import tk.themcbros.interiormod.init.InteriorBlocks;
import tk.themcbros.interiormod.init.InteriorContainers;
import tk.themcbros.interiormod.init.InteriorRecipeTypes;

import java.util.Objects;
import java.util.Optional;

/**
 * @author TheMCBrothers
 */
public class FurnitureWorkbenchContainer extends Container {

    private final CraftingInventory craftMatrix = new CraftingInventory(this, 3, 3);
    private final CraftResultInventory craftResult = new CraftResultInventory();
    private final IWorldPosCallable worldPosCallable;
    private final PlayerEntity player;

    public FurnitureWorkbenchContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, IWorldPosCallable.DUMMY);
    }

    public FurnitureWorkbenchContainer(int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable) {
        super(InteriorContainers.FURNITURE_WORKBENCH, id);
        this.worldPosCallable = worldPosCallable;
        this.player = playerInventory.player;
        this.addSlot(new CraftingResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }

        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
        }

    }

    /**
     * From vanilla
     *
     * @param id              Window ID
     * @param world           The world
     * @param player          The player
     * @param inventory       The inventory
     * @param inventoryResult The result inventory
     */
    protected static void updateCraftingResult(int id, World world, PlayerEntity player, CraftingInventory inventory, CraftResultInventory inventoryResult) {
        if (!world.isRemote) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) player;
            ItemStack itemstack = ItemStack.EMPTY;
            Optional<ICraftingRecipe> optional = Objects.requireNonNull(world.getServer())
                    .getRecipeManager().getRecipe(InteriorRecipeTypes.FURNITURE_CRAFTING, inventory, world);
            if (optional.isPresent()) {
                ICraftingRecipe icraftingrecipe = optional.get();
                if (inventoryResult.canUseRecipe(world, serverplayerentity, icraftingrecipe)) {
                    itemstack = icraftingrecipe.getCraftingResult(inventory);
                }
            }

            inventoryResult.setInventorySlotContents(0, itemstack);
            serverplayerentity.connection.sendPacket(new SSetSlotPacket(id, 0, itemstack));
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        this.worldPosCallable.consume((world, blockPos) -> {
            updateCraftingResult(this.windowId, world, this.player, this.craftMatrix, this.craftResult);
        });
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.worldPosCallable.consume((world, blockPos) -> {
            this.clearContainer(playerIn, world, this.craftMatrix);
        });
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.worldPosCallable, playerIn, InteriorBlocks.FURNITURE_WORKBENCH);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack1 = slot.getStack();
            itemStack = slotStack1.copy();
            if (index == 0) {
                this.worldPosCallable.consume((world, blockPos) -> {
                    slotStack1.getItem().onCreated(slotStack1, world, playerIn);
                });
                if (!this.mergeItemStack(slotStack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(slotStack1, itemStack);
            } else if (index >= 10 && index < 46) {
                if (!this.mergeItemStack(slotStack1, 1, 10, false)) {
                    if (index < 37) {
                        if (!this.mergeItemStack(slotStack1, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.mergeItemStack(slotStack1, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.mergeItemStack(slotStack1, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (slotStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemStack2 = slot.onTake(playerIn, slotStack1);
            if (index == 0) {
                playerIn.dropItem(itemStack2, false);
            }
        }

        return itemStack;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }
}
