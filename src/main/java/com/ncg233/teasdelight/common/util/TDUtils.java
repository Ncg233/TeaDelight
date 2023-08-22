package com.ncg233.teasdelight.common.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TDUtils {
    public static ItemStack getItemStack(Player player,ItemStack stack){
        return player.getAbilities().instabuild ? stack.copy() : stack;
    }
}
