package net.themcbrothers.interiormod.blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.themcbrothers.interiormod.api.furniture.FurnitureType;
import net.themcbrothers.interiormod.init.InteriorBlockEntities;
import net.themcbrothers.interiormod.util.ShapeUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TheMCBrothers
 */
public class TableBlock extends FurnitureBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY = new HashMap<Direction, BooleanProperty>() {{
        put(Direction.NORTH, NORTH);
        put(Direction.EAST, EAST);
        put(Direction.SOUTH, SOUTH);
        put(Direction.WEST, WEST);
    }};

    public final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public TableBlock(Properties properties) {
        super(FurnitureType.TABLE, properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, Boolean.FALSE)
                .setValue(EAST, Boolean.FALSE)
                .setValue(SOUTH, Boolean.FALSE)
                .setValue(WEST, Boolean.FALSE)
                .setValue(WATERLOGGED, Boolean.FALSE));
        SHAPES = this.generateShapes(this.stateDefinition.getPossibleStates());
    }

    private ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states) {
        final VoxelShape legNorthWest = (Block.box(1, 0, 1, 3, 14, 3)); // LEG1
        final VoxelShape legNorthEast = (Block.box(13, 0, 1, 15, 14, 3)); // LEG2
        final VoxelShape legSouthEast = (Block.box(13, 0, 13, 15, 14, 15)); // LEG3
        final VoxelShape legSouthWest = (Block.box(1, 0, 13, 3, 14, 15)); // LEG4
        final VoxelShape cube1 = Block.box(0, 14, 0, 16, 16, 16); // PLATE

        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();
        for (BlockState state : states) {
            boolean north = state.getValue(NORTH);
            boolean east = state.getValue(EAST);
            boolean south = state.getValue(SOUTH);
            boolean west = state.getValue(WEST);

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
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!facing.getAxis().isHorizontal())
            return stateIn;
        BooleanProperty property = FACING_TO_PROPERTY.get(facing.getOpposite());
        boolean flag = facingState.hasProperty(property) && facingState.getValue(property);
        return stateIn.setValue(FACING_TO_PROPERTY.get(facing), flag);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        FluidState fluidState = world.getFluidState(blockPos);
        boolean flag = context.isSecondaryUseActive();
        boolean north, east, south, west;
        if (flag) {
            north = false;
            east = false;
            south = false;
            west = false;
        } else {
            north = this.isTableBlock(world, blockPos.north());
            east = this.isTableBlock(world, blockPos.east());
            south = this.isTableBlock(world, blockPos.south());
            west = this.isTableBlock(world, blockPos.west());
        }
        return this.defaultBlockState().setValue(NORTH, north).setValue(EAST, east).setValue(SOUTH, south)
                .setValue(WEST, west).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    public boolean isTableBlock(LevelAccessor world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.is(this);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return InteriorBlockEntities.TABLE.create(blockPos, blockState);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.defaultFluidState() : Fluids.EMPTY.defaultFluidState();
    }

}
