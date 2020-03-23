package tk.themcbros.interiormod.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import tk.themcbros.interiormod.blocks.LampOnAStickBlock;
import tk.themcbros.interiormod.init.InteriorTileEntities;

public class LampOnAStickTileEntity extends TileEntity implements ITickableTileEntity {

	public LampOnAStickTileEntity() {
		super(InteriorTileEntities.LAMP_ON_A_STICK);
	}

	@Override
	public void tick() {
		if (this.world != null && !this.world.isRemote && this.world.getGameTime() % 20L == 0L) {
			BlockState blockstate = this.getBlockState();
			Block block = blockstate.getBlock();
			if (block instanceof LampOnAStickBlock) {
				LampOnAStickBlock.updatePower(blockstate, this.world, this.pos);
			}
		}
	}

}
