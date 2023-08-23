package com.ncg233.teasdelight.common.block;

import com.nhoryzon.mc.farmersdelight.block.BasketBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.tick.OrderedTick;
import org.jetbrains.annotations.Nullable;

public class DryRackBlock extends Block implements Waterloggable {
    private static final EnumProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.createCuboidShape(0,0,0,16,10,16);
    public DryRackBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER, pos));
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING,WATERLOGGED);
    }
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }
    @Override
    public BlockState mirror(BlockState pState, BlockMirror pMirror) {
        return pState.rotate(pMirror.getRotation((Direction)pState.get(FACING)));
    }
}
