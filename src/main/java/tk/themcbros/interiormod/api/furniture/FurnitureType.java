package tk.themcbros.interiormod.api.furniture;

import java.util.Locale;
import java.util.function.Supplier;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import tk.themcbros.interiormod.init.InteriorBlocks;

public enum FurnitureType implements StringRepresentable {

	CHAIR(() -> InteriorBlocks.CHAIR),
	TABLE(() -> InteriorBlocks.TABLE);

	private final Supplier<Block> blockSupplier;

	FurnitureType(Supplier<Block> supplier) {
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
