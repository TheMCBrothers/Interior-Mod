package net.themcbrothers.interiormod.container;

import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.level.Level;
import net.themcbrothers.interiormod.container.slot.FurnitureResultSlot;
import net.themcbrothers.interiormod.init.InteriorBlocks;
import net.themcbrothers.interiormod.init.InteriorMenuTypes;
import net.themcbrothers.interiormod.init.InteriorRecipeTypes;

import java.util.Optional;

/**
 * @author TheMCBrothers
 */
public class FurnitureWorkbenchMenu extends AbstractContainerMenu {

    private final CraftingContainer craftSlots = new CraftingContainer(this, 3, 3);
    private final ResultContainer resultSlots = new ResultContainer();
    private final ContainerLevelAccess access;
    private final Player player;

    public FurnitureWorkbenchMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, ContainerLevelAccess.NULL);
    }

    public FurnitureWorkbenchMenu(int id, Inventory playerInventory, ContainerLevelAccess worldPosCallable) {
        super(InteriorMenuTypes.FURNITURE_WORKBENCH.get(), id);
        this.access = worldPosCallable;
        this.player = playerInventory.player;
        this.addSlot(new FurnitureResultSlot(playerInventory.player, this.craftSlots, this.resultSlots, 0, 124, 35));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.craftSlots, j + i * 3, 30 + j * 18, 17 + i * 18));
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

    protected static void slotChangedCraftingGrid(AbstractContainerMenu menu, Level level, Player player, CraftingContainer craftingContainer, ResultContainer resultContainer) {
        if (!level.isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<CraftingRecipe> recipe = serverPlayer.getServer().getRecipeManager().getRecipeFor(InteriorRecipeTypes.FURNITURE_CRAFTING.get(), craftingContainer, level);
            if (recipe.isPresent()) {
                CraftingRecipe craftingRecipe = recipe.get();
                if (resultContainer.setRecipeUsed(level, serverPlayer, craftingRecipe)) {
                    itemStack = craftingRecipe.assemble(craftingContainer, level.registryAccess());
                }
            }

            resultContainer.setItem(0, itemStack);
            menu.setRemoteSlot(0, itemStack);
            serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, itemStack));
        }
    }

    @Override
    public void slotsChanged(Container container) {
        this.access.execute((level, pos) -> {
            slotChangedCraftingGrid(this, level, this.player, this.craftSlots, this.resultSlots);
        });
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, pos) -> {
            this.clearContainer(player, this.craftSlots);
        });
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, InteriorBlocks.FURNITURE_WORKBENCH.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();
            if (index == 0) {
                this.access.execute((level, pos) -> {
                    itemStack1.getItem().onCraftedBy(itemStack1, level, player);
                });
                if (!this.moveItemStackTo(itemStack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemStack1, itemStack);
            } else if (index >= 10 && index < 46) {
                if (!this.moveItemStackTo(itemStack1, 1, 10, false)) {
                    if (index < 37) {
                        if (!this.moveItemStackTo(itemStack1, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemStack1, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemStack1, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack1);
            if (index == 0) {
                player.drop(itemStack1, false);
            }
        }

        return itemStack;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }
}
