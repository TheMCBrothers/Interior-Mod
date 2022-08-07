package net.themcbrothers.interiormod.api.furniture;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.themcbrothers.interiormod.init.InteriorBlocks;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * @author TheMCBrothers
 */
public enum FurnitureType implements StringRepresentable {

    CHAIR(InteriorBlocks.CHAIR),
    TABLE(InteriorBlocks.TABLE);

    private final Supplier<? extends Block> blockSupplier;

    FurnitureType(Supplier<? extends Block> supplier) {
        this.blockSupplier = supplier;
    }

    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public ItemStack getStack() {
        return new ItemStack(this.blockSupplier.get());
    }

    public Block getBlock() {
        return this.blockSupplier.get();
    }

}
