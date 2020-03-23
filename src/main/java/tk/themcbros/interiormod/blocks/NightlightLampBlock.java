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
import tk.themcbros.interiormod.tileentity.NightlightLampTileEntity;

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
		return new NightlightLampTileEntity();
	}
	
	public static void updatePower(BlockState state, World worldIn, BlockPos pos) {
		if (worldIn.dimension.hasSkyLight()) {
			int i = worldIn.getLightFor(LightType.SKY, pos) - worldIn.getSkylightSubtracted();
			float f = worldIn.getCelestialAngleRadians(1.0F);
			boolean flag = true;
			if (flag) {
				i = 15 - i;
			} else if (i > 0) {
				float f1 = f < (float) Math.PI ? 0.0F : ((float) Math.PI * 2F);
				f = f + (f1 - f) * 0.2F;
				i = Math.round((float) i * MathHelper.cos(f));
			}

			i = MathHelper.clamp(i, 0, 15);
			if (state.get(LIT) != (i > 0)) {
				worldIn.setBlockState(pos, state.with(LIT, Boolean.valueOf(i > 0)), 3);
			}

		}
	}

}
