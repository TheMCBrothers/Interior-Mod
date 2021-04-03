package tk.themcbros.interiormod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import tk.themcbros.interiormod.init.InteriorTileEntities;

/**
 * @author TheMCBrothers
 */
public class NightlightLampBlock extends Block {

    private static final BooleanProperty LIT = BlockStateProperties.LIT;

    public NightlightLampBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(LIT, Boolean.FALSE));
    }

    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.get(LIT) ? 15 : 0;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return InteriorTileEntities.LAMP.create();
    }

    /**
     * Updates the LIT property of BlockState
     *
     * @param state {@link BlockState} of Block
     * @param world {@link World} of Block
     * @param pos   {@link BlockPos} of Block
     */
    public static void updatePower(BlockState state, World world, BlockPos pos) {
        if (world.getDimensionType().hasSkyLight()) {
            int i = world.getLightFor(LightType.SKY, pos.up()) - world.getSkylightSubtracted();
            i = 15 - i;
            i = MathHelper.clamp(i, 0, 15);
            boolean lit = i > 0; // minimum light level 1
            if (state.get(LIT) != lit) {
                world.setBlockState(pos, state.with(LIT, lit), 2 | 1);
            }
        }
    }

}
