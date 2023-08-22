package com.ncg233.teasdelight.common.block;

import com.ncg233.teasdelight.common.block.entity.DryRackBlockEntity;
import com.ncg233.teasdelight.common.recipe.DryingRecipe;
import com.ncg233.teasdelight.common.registry.TDBlockEntityTypes;
import com.ncg233.teasdelight.common.util.TDUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.util.Optional;

public class DryRackBlock extends BaseEntityBlock implements SimpleWaterloggedBlock{
    private static final EnumProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.box(0,0,0,16,10,16);
//    private static final VoxelShape SHAPE = Shapes.or(Shapes.box(0,0,0,16 / 16,9 / 16d,16 / 16),
//    Shapes.box(0,9 / 16d,0,0,10 / 16d,16 / 16d),
//            Shapes.box(0,9 / 16d,0,0,10 / 16d,16 / 16d));
    public DryRackBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }
    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(blockEntity instanceof DryRackBlockEntity dryEntity){
            int doneItemSlot = dryEntity.hasItemDone();
            if(!level.isClientSide && doneItemSlot >= 0){
                dryEntity.takeAllItems(player);
                return InteractionResult.SUCCESS;
            }
            int slot = dryEntity.getNextEmptySlot();
            if(slot < 0){
                return InteractionResult.PASS;
            }
            ItemStack mainHandItem = player.getMainHandItem();
            if(!state.getValue(WATERLOGGED) ) {
                Optional<DryingRecipe> recipe = dryEntity.getMatchingRecipe(new SimpleContainer(mainHandItem), slot);
                if (recipe.isPresent()) {
                    if (!level.isClientSide && dryEntity.addItem(TDUtils.getItemStack(player,mainHandItem),recipe.get(), slot, state)) {
                        return InteractionResult.SUCCESS;
                    }
                }else if(!level.isClientSide && !mainHandItem.isEmpty()) {
                    sendMessage(player,"block.dry_rack.illegal_item");
                }
            }else if(!mainHandItem.isEmpty()){
                sendMessage(player,"block.dry_rack.invalid_item");
            }
        }
        return InteractionResult.CONSUME;
    }
    private void sendMessage(Player player,String message){
        player.displayClientMessage(Component.translatable(message),true);
    }


    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = level.getBlockEntity(pos);
            if (tileEntity instanceof DryRackBlockEntity entity) {
                ItemUtils.dropItems(level,pos,entity.getInventory());
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DryRackBlockEntity(pos,state);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
            return createTickerHelper(blockEntityType, TDBlockEntityTypes.DRY_RACK.get(),level.isClientSide
                    ? DryRackBlockEntity::animationTick :
                    DryRackBlockEntity::dryingTick);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED,fluid.getType() == Fluids.WATER);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING,WATERLOGGED);
    }
    @Override
    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate((Direction)pState.getValue(FACING)));
    }
    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation((Direction)pState.getValue(FACING)));
    }
}
