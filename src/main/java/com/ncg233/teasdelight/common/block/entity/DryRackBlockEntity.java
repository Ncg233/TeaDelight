package com.ncg233.teasdelight.common.block.entity;

import com.ncg233.teasdelight.common.registry.TDBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class DryRackBlockEntity extends BlockEntity {
    protected final DefaultedList<ItemStack> inventory;
    private final int[] dryingTotalTimes;
    private final int[] dryingTimes;

    public DryRackBlockEntity( BlockPos pos, BlockState state) {
        super(TDBlockEntityTypes.DRY_RACK, pos, state);
        inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
        dryingTotalTimes = new int[4];
        dryingTimes = new int[4];
    }
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        this.inventory.clear();
        Inventories.readNbt(tag, this.inventory);
        int[] dryingTotalTimeRead;
        if (tag.contains("DryingTimes", 11)) {
            dryingTotalTimeRead = tag.getIntArray("DryingTimes");
            System.arraycopy(dryingTotalTimeRead, 0, this.dryingTimes, 0, Math.min(this.dryingTotalTimes.length, dryingTotalTimeRead.length));
        }

        if (tag.contains("DryingTotalTimes", 11)) {
            dryingTotalTimeRead = tag.getIntArray("DryingTotalTimes");
            System.arraycopy(dryingTotalTimeRead, 0, this.dryingTotalTimes, 0, Math.min(this.dryingTotalTimes.length, dryingTotalTimeRead.length));
        }
    }
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        Inventories.writeNbt(tag, this.inventory, true);
        tag.putIntArray("DryingTimes", this.dryingTimes);
        tag.putIntArray("DryingTotalTimes", this.dryingTotalTimes);
    }
    private void inventoryChanged() {
        this.markDirty();
        if (this.world != null) {
            this.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
        }

    }
    public boolean addItem(ItemStack itemStack, int cookTime) {
        for(int i = 0; i < this.inventory.size(); ++i) {
            ItemStack itemstack = this.inventory.get(i);
            if (itemstack.isEmpty()) {
                this.dryingTotalTimes[i] = cookTime;
                this.dryingTimes[i] = 0;
                this.inventory.set(i, itemStack.split(1));
                this.inventoryChanged();
                return true;
            }
        }

        return false;
    }


}
