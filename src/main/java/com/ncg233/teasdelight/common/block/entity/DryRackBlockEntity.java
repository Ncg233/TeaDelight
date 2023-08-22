package com.ncg233.teasdelight.common.block.entity;

import com.ncg233.teasdelight.common.block.DryRackBlock;
import com.ncg233.teasdelight.common.recipe.DryingRecipe;
import com.ncg233.teasdelight.common.registry.TDBlockEntityTypes;
import com.ncg233.teasdelight.common.registry.TDRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.mixin.accessor.RecipeManagerAccessor;

import java.util.Optional;

public class DryRackBlockEntity extends SyncedBlockEntity {
    private final ItemStackHandler inventory = createHandler();
    private static final int SIZE = 4;
    private final int[] dryingTimes = new int[SIZE];
    private final int[] dryingTimesTotal = new int[SIZE];
    private ResourceLocation[] lastRecipeIDs = new ResourceLocation[SIZE];
    public DryRackBlockEntity(BlockPos pos, BlockState state) {
        super(TDBlockEntityTypes.DRY_RACK.get(), pos, state);
    }

    public boolean addItem(ItemStack itemStackIn, DryingRecipe recipe, int slot, BlockState state) {
        if(0 <= slot && slot < this.inventory.getSlots() && !state.getValue(DryRackBlock.WATERLOGGED)){
            ItemStack slotStack = this.inventory.getStackInSlot(slot);
            if(slotStack.isEmpty()){
                dryingTimes[slot] = 0;
                dryingTimesTotal[slot] = recipe.getDryingTime();
                inventory.setStackInSlot(slot, itemStackIn.split(1));
                lastRecipeIDs[slot] = recipe.getId();
                inventoryChanged();
                return true;
            }
        }
        return false;
    }
    public void takeAllItems(Player player){
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            if(this.dryingTimes[i] > this.dryingTimesTotal[i]){
                ItemStack stackInSlot = inventory.getStackInSlot(i);
                player.addItem(stackInSlot.copy());
                this.inventory.setStackInSlot(i, ItemStack.EMPTY);
                this.dryingTimes[i] = 0;
                inventoryChanged();
                level.playSound(null,this.getBlockPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,0.1f,0.2f);
            }
        }
    }
    public int hasItemDone(){
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            if(this.dryingTimes[i] > this.dryingTimesTotal[i]){
                return i;
            }
        }
        return -1;
    }
    public Optional<DryingRecipe> getMatchingRecipe(SimpleContainer simpleContainer, int slot) {
        if (level == null) return Optional.empty();

        if (lastRecipeIDs[slot] != null) {
            Recipe<SimpleContainer> recipe = ((RecipeManagerAccessor) level.getRecipeManager())
                    .getRecipeMap(TDRecipeTypes.DRYING.get())
                    .get(lastRecipeIDs[slot]);
            if (recipe instanceof DryingRecipe && recipe.matches(simpleContainer, level)) {
                return Optional.of((DryingRecipe) recipe);
            }
        }
        return level.getRecipeManager().getRecipeFor(TDRecipeTypes.DRYING.get(), simpleContainer, level);
    }
    public ItemStackHandler getInventory() {
        return this.inventory;
    }
    public void load(CompoundTag compound) {
        super.load(compound);
        if (compound.contains("Inventory")) {
            this.inventory.deserializeNBT(compound.getCompound("Inventory"));
        } else {
            this.inventory.deserializeNBT(compound);
        }
        int[] arrayDryingTimesTotal;
        if (compound.contains("DryingTimes", 11)) {
            arrayDryingTimesTotal = compound.getIntArray("DryingTimes");
            System.arraycopy(arrayDryingTimesTotal, 0, this.dryingTimes, 0, Math.min(this.dryingTimesTotal.length, arrayDryingTimesTotal.length));
        }

        if (compound.contains("DryingTotalTimes", 11)) {
            arrayDryingTimesTotal = compound.getIntArray("DryingTotalTimes");
            System.arraycopy(arrayDryingTimesTotal, 0, this.dryingTimesTotal, 0, Math.min(this.dryingTimesTotal.length, arrayDryingTimesTotal.length));
        }
    }
    public void saveAdditional(CompoundTag compound) {
        this.writeItems(compound);
        compound.putIntArray("DryingTimes", this.dryingTimes);
        compound.putIntArray("DryingTotalTimes", this.dryingTimesTotal);
    }
    public static <E extends BlockEntity> void animationTick(Level level, BlockPos pos, BlockState state, E e) {

    }
    public static void dryingTick(Level level, BlockPos pos, BlockState state, DryRackBlockEntity e) {
        e.dryingAndOutputItem();
    }

    private void dryingAndOutputItem() {
        if(this.level != null){
            boolean didInventoryChange = false;
            for (int i = 0; i < this.inventory.getSlots(); i++) {
                ItemStack stack = this.inventory.getStackInSlot(i);
                if(!stack.isEmpty()){
                    this.dryingTimes[i]++;
                    if(this.dryingTimes[i] >= this.dryingTimesTotal[i]){
                        SimpleContainer inventoryWrapper = new SimpleContainer(stack);
                        Optional<DryingRecipe> recipe = getMatchingRecipe(inventoryWrapper, i);
                        if (recipe.isPresent()) {
                            ItemStack resultStack = recipe.get().getResultItem(level.registryAccess());
                            if (!resultStack.isEmpty()) {
                                this.inventory.setStackInSlot(i,resultStack.copy());
                            }
                        }
                        didInventoryChange = true;
                    }
                }
            }
            if (didInventoryChange) {
                this.inventoryChanged();
            }
        }
    }
    public int getNextEmptySlot() {
        for(int i = 0; i < this.inventory.getSlots(); ++i) {
            ItemStack slotStack = this.inventory.getStackInSlot(i);
            if (slotStack.isEmpty()) {
                return i;
            }
        }
        return -1;
    }
    public Vec2 getStoveItemOffset(int index) {
        float X_OFFSET = 0.2F;
        float Y_OFFSET = 0.2F;
        Vec2[] OFFSETS = new Vec2[]{
                new Vec2(X_OFFSET, Y_OFFSET), new Vec2(-X_OFFSET, Y_OFFSET),
                new Vec2(X_OFFSET, -Y_OFFSET), new Vec2(-X_OFFSET, -Y_OFFSET)};
        return OFFSETS[index];
    }
    public CompoundTag getUpdateTag() {
        return this.writeItems(new CompoundTag());
    }

    private CompoundTag writeItems(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Inventory", this.inventory.serializeNBT());
        return compound;
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(SIZE) {
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }

}
