package tk.themcbros.interiormod.furniture;

import java.util.Locale;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import tk.themcbros.interiormod.init.InteriorBlocks;

public enum FurnitureType implements IStringSerializable {

	CHAIR(() -> InteriorBlocks.CHAIR);
	
	private final Supplier<Block> blockSupplier;
	
	private FurnitureType(Supplier<Block> supplier) {
		this.blockSupplier = supplier;
	}

	@Override
	public String getName() {
		return name().toLowerCase(Locale.ROOT);
	}
	
	public ItemStack getStack() {
		return new ItemStack(this.blockSupplier.get());
	}
	
}
