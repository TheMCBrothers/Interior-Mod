package tk.themcbros.interiormod.blocks;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import tk.themcbros.interiormod.tileentity.NightlightLampTileEntity;
import tk.themcbros.interiormod.util.ShapeUtils;

public class LampOnAStickBlock extends Block implements IWaterLoggable {

	public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	
	private final VoxelShape SHAPE;

	public LampOnAStickBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(PART, Part.BOTTOM).with(LIT, Boolean.FALSE));
		this.SHAPE = this.generateShape();
	}
	
	private VoxelShape generateShape() {
		List<VoxelShape> shapes = Lists.newArrayList();
		shapes.add(Block.makeCuboidShape(6, 0, 6, 10, 32, 10));
		shapes.add(Block.makeCuboidShape(0, 32, 0, 16, 48, 16));
		return ShapeUtils.combineAll(shapes);
	}

	@SuppressWarnings("deprecation")
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
//		if (state.get(PART) != Part.TOP) {
//			return super.isValidPosition(state, worldIn, pos);
//		} else {
//			BlockState blockstate = worldIn.getBlockState(pos.down());
//			if (state.getBlock() != this)
//				return super.isValidPosition(state, worldIn, pos);
//			return blockstate.getBlock() == this && blockstate.get(PART) == Part.BOTTOM;
//		}

		Part part = state.get(PART);
		if (part == Part.MIDDLE) {
			BlockState bottomState = worldIn.getBlockState(pos.down());
			return bottomState.getBlock() == this && bottomState.get(PART) == Part.BOTTOM;
		} else if (part == Part.TOP) {
			BlockState middleState = worldIn.getBlockState(pos.down());
			return middleState.getBlock() == this && middleState.get(PART) == Part.MIDDLE;
		} else {
			return super.isValidPosition(state, worldIn, pos);
		}
	}

	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state,
			@Nullable TileEntity te, ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
	}

	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		Part part = state.get(PART);
		BlockPos bottomPos = part == Part.BOTTOM ? pos : part == Part.MIDDLE ? pos.down() : pos.down(2);
		BlockPos middlePos = part == Part.MIDDLE ? pos : part == Part.BOTTOM ? pos.up() : pos.down();
		BlockPos topPos = part == Part.TOP ? pos : part == Part.MIDDLE ? pos.up() : pos.up(2);
		BlockState bottomState = worldIn.getBlockState(bottomPos);
		BlockState middleState = worldIn.getBlockState(middlePos);
		BlockState topState = worldIn.getBlockState(topPos);
		if (bottomState.getBlock() == this && middleState.getBlock() == this && topState.getBlock() == this) {
			worldIn.setBlockState(bottomPos, Blocks.AIR.getDefaultState(), 35);
			worldIn.setBlockState(middlePos, Blocks.AIR.getDefaultState(), 35);
			worldIn.setBlockState(topPos, Blocks.AIR.getDefaultState(), 35);
			worldIn.playEvent(player, 2001, bottomPos, Block.getStateId(bottomState));
			worldIn.playEvent(player, 2001, middlePos, Block.getStateId(middleState));
			worldIn.playEvent(player, 2001, topPos, Block.getStateId(topState));
			if (!worldIn.isRemote && !player.isCreative()) {
				spawnDrops(bottomState, worldIn, bottomPos, (TileEntity) null, player, player.getHeldItemMainhand());
				spawnDrops(middleState, worldIn, middlePos, (TileEntity) null, player, player.getHeldItemMainhand());
				spawnDrops(topState, worldIn, topPos, (TileEntity) null, player, player.getHeldItemMainhand());
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		this.placeRestAt(worldIn, pos, 3);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos blockpos = context.getPos();
		return blockpos.getY() < context.getWorld().getDimension().getHeight() - 2
				&& context.getWorld().getBlockState(blockpos.up()).isReplaceable(context)
				&& context.getWorld().getBlockState(blockpos.up(2)).isReplaceable(context) ? this.getDefaultState()
						: null;
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (state.get(LIT) && !worldIn.isBlockPowered(pos)) {
			worldIn.setBlockState(pos, state.cycle(LIT), 2);
		}

	}

	public void placeAt(IWorld worldIn, BlockPos pos, int flags) {
		worldIn.setBlockState(pos, this.getDefaultState().with(PART, Part.BOTTOM), flags);
		this.placeRestAt(worldIn, pos, flags);
	}

	public void placeRestAt(IWorld worldIn, BlockPos pos, int flags) {
		worldIn.setBlockState(pos.up(), this.getDefaultState().with(PART, Part.MIDDLE), flags);
		worldIn.setBlockState(pos.up(2), this.getDefaultState().with(PART, Part.TOP), flags);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(PART, LIT);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Part part = state.get(PART);
		return part == Part.BOTTOM ? SHAPE
				: part == Part.MIDDLE ? SHAPE.withOffset(0, -1d, 0)
				: part == Part.TOP ? SHAPE.withOffset(0, -2d, 0) : VoxelShapes.fullCube();
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.get(LIT) && state.get(PART) == Part.TOP ? 15 : 0;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return state.get(PART) == Part.TOP;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return state.get(PART) == Part.TOP ? new NightlightLampTileEntity() : null;
	}

	public static enum Part implements IStringSerializable {

		BOTTOM, MIDDLE, TOP;

		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}

	}

}
