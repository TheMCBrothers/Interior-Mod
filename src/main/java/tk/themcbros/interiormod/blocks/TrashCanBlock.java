package tk.themcbros.interiormod.blocks;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import tk.themcbros.interiormod.util.ShapeUtils;

import javax.annotation.Nullable;
import java.util.List;

public class TrashCanBlock extends Block implements SimpleWaterloggedBlock, MenuProvider, WorldlyContainerHolder {

    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final VoxelShape SHAPE;

    public TrashCanBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE));
        this.SHAPE = this.generateShape();
    }

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.defaultFluidState() : Fluids.EMPTY.defaultFluidState();
    }

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return  this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).equals(Fluids.WATER.defaultFluidState()));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext collisionContext) {
		return SHAPE;
	}

	private VoxelShape generateShape() {
        List<VoxelShape> shapes = Lists.newArrayList();
        shapes.add(Block.box(3, 15, 6.32, 4, 16, 9.63));
        shapes.add(Block.box(4.699999999999999, 15, 10.3, 5.699999999999999, 16, 11.3));
        shapes.add(Block.box(10.3, 15, 10.3, 11.3, 16, 11.3));
        shapes.add(Block.box(10.3, 15, 4.699999999999999, 11.3, 16, 5.699999999999999));
        shapes.add(Block.box(4.699999999999999, 15, 4.699999999999999, 5.699999999999999, 16, 5.699999999999999));
        shapes.add(Block.box(6.35, 15, 3, 9.66, 16, 4));
        shapes.add(Block.box(6.35, 15, 12, 9.66, 16, 13));
        shapes.add(Block.box(12, 15, 6.369999999999999, 13, 16, 9.68));
        shapes.add(Block.box(12.98, 14.94, 6.34, 14.64, 16.599999999999998, 9.66));
        shapes.add(Block.box(1.3599999999999994, 14.94, 6.34, 3.0199999999999996, 16.599999999999998, 9.66));
        shapes.add(Block.box(4.68, 14.94, 3.0200000000000005, 6.34, 16.599999999999998, 4.68));
        shapes.add(Block.box(3.0199999999999996, 14.94, 4.68, 4.68, 16.599999999999998, 6.34));
        shapes.add(Block.box(4.68, 14.94, 11.32, 6.34, 16.599999999999998, 12.98));
        shapes.add(Block.box(3.0199999999999996, 14.94, 9.66, 4.68, 16.599999999999998, 11.32));
        shapes.add(Block.box(9.66, 14.94, 11.32, 11.32, 16.599999999999998, 12.98));
        shapes.add(Block.box(11.32, 14.94, 9.66, 12.98, 16.599999999999998, 11.32));
        shapes.add(Block.box(6.34, 14.94, 12.98, 9.66, 16.599999999999998, 14.64));
        shapes.add(Block.box(9.66, 14.94, 3.0200000000000005, 11.32, 16.599999999999998, 4.68));
        shapes.add(Block.box(11.32, 14.94, 4.68, 12.98, 16.599999999999998, 6.34));
        shapes.add(Block.box(6.34, 14.94, 1.3600000000000003, 9.66, 16.599999999999998, 3.0200000000000005));
        shapes.add(Block.box(11.32, 0, 6.34, 12.98, 14.94, 9.66));
        shapes.add(Block.box(9.66, 0, 9.66, 11.32, 14.94, 11.32));
        shapes.add(Block.box(4.68, 0, 9.66, 6.34, 14.94, 11.32));
        shapes.add(Block.box(4.68, 0, 4.68, 6.34, 14.94, 6.34));
        shapes.add(Block.box(3.0199999999999996, 0, 6.34, 4.68, 14.94, 9.66));
        shapes.add(Block.box(9.66, 0, 4.68, 11.32, 14.94, 6.34));
        shapes.add(Block.box(6.34, 0, 3.0200000000000005, 9.66, 14.94, 4.68));
        shapes.add(Block.box(6.34, 0, 11.32, 9.66, 14.94, 12.98));
        shapes.add(Block.box(6.34, 0, 4.68, 9.66, 1.66, 6.34));
        shapes.add(Block.box(6.34, 0, 9.66, 9.66, 1.66, 11.32));
        shapes.add(Block.box(4.68, 0, 6.34, 11.32, 1.66, 9.66));
        return ShapeUtils.combineAll(shapes);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getBlock() instanceof MenuProvider && player instanceof ServerPlayer) {
            NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) state.getBlock());
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return (MenuProvider) state.getBlock();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return ChestMenu.oneRow(id, inventory);
    }

	@Override
	public Component getDisplayName() {
		return new TranslatableComponent("container.interiormod.trash_can");
	}

	@Override
    public WorldlyContainer getContainer(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos) {
        return new Inv();
    }

    static class Inv extends SimpleContainer implements WorldlyContainer {
        public Inv() {
            super(9);
        }

        @Override
        public int[] getSlotsForFace(Direction side) {
            return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        }

        @Override
        public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
            return true;
        }

        @Override
        public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
            return false;
        }
    }

}
