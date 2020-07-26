package tk.themcbros.interiormod.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import tk.themcbros.interiormod.blocks.NightlightLampBlock;
import tk.themcbros.interiormod.init.InteriorTileEntities;

/**
 * @author TheMCBrothers
 */
public class NightlightLampTileEntity extends TileEntity implements ITickableTileEntity {

    public NightlightLampTileEntity() {
        super(InteriorTileEntities.LAMP);
    }

    @Override
    public void tick() {
        if (this.world != null && !this.world.isRemote && this.world.getGameTime() % 20L == 0L) {
            BlockState blockstate = this.getBlockState();
            if (blockstate.has(BlockStateProperties.LIT)) {
                NightlightLampBlock.updatePower(blockstate, this.world, this.pos);
            }
        }
    }

}
