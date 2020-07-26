package tk.themcbros.interiormod.blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.init.InteriorTileEntities;
import tk.themcbros.interiormod.util.ShapeUtils;

import java.util.List;

/**
 * @author TheMCBrothers
 */
public class TableBlock extends FurnitureBlock implements IWaterLoggable {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public TableBlock(Properties properties) {
        super(FurnitureType.TABLE, properties);
        this.setDefaultState(this.stateContainer.getBaseState()
                .with(NORTH, Boolean.FALSE)
                .with(EAST, Boolean.FALSE)
                .with(SOUTH, Boolean.FALSE)
                .with(WEST, Boolean.FALSE)
                .with(WATERLOGGED, Boolean.FALSE));
        SHAPES = this.generateShapes(this.stateContainer.getValidStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states) {
        final VoxelShape legNorthWest = (Block.makeCuboidShape(1, 0, 1, 3, 14, 3)); // LEG1
        final VoxelShape legNorthEast = (Block.makeCuboidShape(13, 0, 1, 15, 14, 3)); // LEG2
        final VoxelShape legSouthEast = (Block.makeCuboidShape(13, 0, 13, 15, 14, 15)); // LEG3
        final VoxelShape legSouthWest = (Block.makeCuboidShape(1, 0, 13, 3, 14, 15)); // LEG4
        final VoxelShape cube1 = Block.makeCuboidShape(0, 14, 0, 16, 16, 16); // PLATE

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for (BlockState state : states) {
            boolean north = state.get(NORTH);
            boolean east = state.get(EAST);
            boolean south = state.get(SOUTH);
            boolean west = state.get(WEST);

            List<VoxelShape> shapes = Lists.newArrayList(cube1);
            if (!north && !west) {
                shapes.add(legNorthWest);
            }
            if (!north && !east) {
                shapes.add(legNorthEast);
            }
            if (!south && !west) {
                shapes.add(legSouthWest);
            }
            if (!south && !east) {
                shapes.add(legSouthEast);
            }
            builder.put(state, ShapeUtils.combineAll(shapes));
        }
        return builder.build();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state);
    }

    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        Boolean north = this.isTableBlock(worldIn, currentPos.north());
        Boolean east = this.isTableBlock(worldIn, currentPos.east());
        Boolean south = this.isTableBlock(worldIn, currentPos.south());
        Boolean west = this.isTableBlock(worldIn, currentPos.west());
        return stateIn.with(NORTH, north).with(EAST, east).with(SOUTH, south).with(WEST, west);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getPos();
        IFluidState fluidState = world.getFluidState(blockPos);
        Boolean north = this.isTableBlock(world, blockPos.north());
        Boolean east = this.isTableBlock(world, blockPos.east());
        Boolean south = this.isTableBlock(world, blockPos.south());
        Boolean west = this.isTableBlock(world, blockPos.west());
        return this.getDefaultState().with(NORTH, north).with(EAST, east).with(SOUTH, south).with(WEST, west).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    public boolean isTableBlock(IWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getBlock() == this.getBlock();
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return InteriorTileEntities.TABLE.create();
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getDefaultState() : Fluids.EMPTY.getDefaultState();
    }

}
