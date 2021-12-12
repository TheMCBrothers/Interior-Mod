package net.themcbrothers.interiormod.container.slot;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import net.themcbrothers.interiormod.init.InteriorRecipeTypes;

import javax.annotation.Nonnull;

public class FurnitureResultSlot extends ResultSlot {
    private final CraftingContainer craftSlots;
    private final Player player;

    public FurnitureResultSlot(Player player, CraftingContainer craftSlots, Container resultSlots, int slot, int x, int y) {
        super(player, craftSlots, resultSlots, slot, x, y);
        this.craftSlots = craftSlots;
        this.player = player;
    }

    @Override
    public void onTake(@Nonnull Player player, @Nonnull ItemStack stack) {
        this.checkTakeAchievements(stack);
        NonNullList<ItemStack> remainingItems = player.level.getRecipeManager().getRemainingItemsFor(InteriorRecipeTypes.FURNITURE_CRAFTING, this.craftSlots, player.level);
        for (int i = 0; i < remainingItems.size(); ++i) {
            ItemStack itemStack = this.craftSlots.getItem(i);
            ItemStack itemStack1 = remainingItems.get(i);
            if (!itemStack.isEmpty()) {
                this.craftSlots.removeItem(i, 1);
                itemStack = this.craftSlots.getItem(i);
            }

            if (!itemStack1.isEmpty()) {
                if (itemStack.isEmpty()) {
                    this.craftSlots.setItem(i, itemStack1);
                } else if (ItemStack.isSame(itemStack, itemStack1) && ItemStack.tagMatches(itemStack, itemStack1)) {
                    itemStack1.grow(itemStack.getCount());
                    this.craftSlots.setItem(i, itemStack1);
                } else if (!this.player.getInventory().add(itemStack1)) {
                    this.player.drop(itemStack1, false);
                }
            }
        }
    }
}
