package tk.themcbros.interiormod.blocks;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ISidedInventoryProvider;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import tk.themcbros.interiormod.util.ShapeUtils;

import javax.annotation.Nullable;

public class TrashCanBlock extends Block implements IWaterLoggable, INamedContainerProvider, ISidedInventoryProvider {

	private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	private final VoxelShape SHAPE;
	
	public TrashCanBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, Boolean.FALSE));
		this.SHAPE = this.generateShape();
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}
	
	@Override
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getDefaultState() : Fluids.EMPTY.getDefaultState();
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(WATERLOGGED, context.getWorld().getFluidState(context.getPos()).equals(Fluids.WATER.getDefaultState()));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	private VoxelShape generateShape() {
		List<VoxelShape> shapes = Lists.newArrayList();
		shapes.add(Block.makeCuboidShape(3, 15, 6.32, 4, 16, 9.63));
		shapes.add(Block.makeCuboidShape(4.699999999999999, 15, 10.3, 5.699999999999999, 16, 11.3));
		shapes.add(Block.makeCuboidShape(10.3, 15, 10.3, 11.3, 16, 11.3));
		shapes.add(Block.makeCuboidShape(10.3, 15, 4.699999999999999, 11.3, 16, 5.699999999999999));
		shapes.add(Block.makeCuboidShape(4.699999999999999, 15, 4.699999999999999, 5.699999999999999, 16, 5.699999999999999));
		shapes.add(Block.makeCuboidShape(6.35, 15, 3, 9.66, 16, 4));
		shapes.add(Block.makeCuboidShape(6.35, 15, 12, 9.66, 16, 13));
		shapes.add(Block.makeCuboidShape(12, 15, 6.369999999999999, 13, 16, 9.68));
		shapes.add(Block.makeCuboidShape(12.98, 14.94, 6.34, 14.64, 16.599999999999998, 9.66));
		shapes.add(Block.makeCuboidShape(1.3599999999999994, 14.94, 6.34, 3.0199999999999996, 16.599999999999998, 9.66));
		shapes.add(Block.makeCuboidShape(4.68, 14.94, 3.0200000000000005, 6.34, 16.599999999999998, 4.68));
		shapes.add(Block.makeCuboidShape(3.0199999999999996, 14.94, 4.68, 4.68, 16.599999999999998, 6.34));
		shapes.add(Block.makeCuboidShape(4.68, 14.94, 11.32, 6.34, 16.599999999999998, 12.98));
		shapes.add(Block.makeCuboidShape(3.0199999999999996, 14.94, 9.66, 4.68, 16.599999999999998, 11.32));
		shapes.add(Block.makeCuboidShape(9.66, 14.94, 11.32, 11.32, 16.599999999999998, 12.98));
		shapes.add(Block.makeCuboidShape(11.32, 14.94, 9.66, 12.98, 16.599999999999998, 11.32));
		shapes.add(Block.makeCuboidShape(6.34, 14.94, 12.98, 9.66, 16.599999999999998, 14.64));
		shapes.add(Block.makeCuboidShape(9.66, 14.94, 3.0200000000000005, 11.32, 16.599999999999998, 4.68));
		shapes.add(Block.makeCuboidShape(11.32, 14.94, 4.68, 12.98, 16.599999999999998, 6.34));
		shapes.add(Block.makeCuboidShape(6.34, 14.94, 1.3600000000000003, 9.66, 16.599999999999998, 3.0200000000000005));
		shapes.add(Block.makeCuboidShape(11.32, 0, 6.34, 12.98, 14.94, 9.66));
		shapes.add(Block.makeCuboidShape(9.66, 0, 9.66, 11.32, 14.94, 11.32));
		shapes.add(Block.makeCuboidShape(4.68, 0, 9.66, 6.34, 14.94, 11.32));
		shapes.add(Block.makeCuboidShape(4.68, 0, 4.68, 6.34, 14.94, 6.34));
		shapes.add(Block.makeCuboidShape(3.0199999999999996, 0, 6.34, 4.68, 14.94, 9.66));
		shapes.add(Block.makeCuboidShape(9.66, 0, 4.68, 11.32, 14.94, 6.34));
		shapes.add(Block.makeCuboidShape(6.34, 0, 3.0200000000000005, 9.66, 14.94, 4.68));
		shapes.add(Block.makeCuboidShape(6.34, 0, 11.32, 9.66, 14.94, 12.98));
		shapes.add(Block.makeCuboidShape(6.34, 0, 4.68, 9.66, 1.66, 6.34));
		shapes.add(Block.makeCuboidShape(6.34, 0, 9.66, 9.66, 1.66, 11.32));
		shapes.add(Block.makeCuboidShape(4.68, 0, 6.34, 11.32, 1.66, 9.66));
		return ShapeUtils.combineAll(shapes);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (state.getBlock() instanceof INamedContainerProvider && player instanceof ServerPlayerEntity) {
			NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) state.getBlock());
		}
		return ActionResultType.SUCCESS;
	}

	@Nullable
	@Override
	public INamedContainerProvider getContainer(BlockState state, World world, BlockPos pos) {
		return (INamedContainerProvider) state.getBlock();
	}

	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return ChestContainer.createGeneric9X1(id, playerInventory);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.interiormod.trash_can");
	}

	@Override
	public ISidedInventory createInventory(BlockState state, IWorld world, BlockPos pos) {
		return new Inv();
	}

	static class Inv extends Inventory implements ISidedInventory {
		public Inv() {
			super(9);
		}

		@Override
		public int[] getSlotsForFace(Direction side) {
			return new int[] {0,1,2,3,4,5,6,7,8};
		}

		@Override
		public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
			return true;
		}

		@Override
		public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
			return false;
		}
	}

}
