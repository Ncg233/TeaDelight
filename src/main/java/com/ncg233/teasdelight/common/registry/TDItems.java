package com.ncg233.teasdelight.common.registry;

import com.ncg233.teasdelight.TeaDelight;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TDItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            TeaDelight.MODID);
    //BLOCKS
    public static final RegistryObject<Item> DRY_RACK = ITEMS.register("dry_rack",
            () -> new BlockItem(TDBlocks.DRY_RACK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CARNELIAN_LOG = ITEMS.register("carnelian_log",
            () -> new BlockItem(TDBlocks.CARNELIAN_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_TEA_LEAVES = ITEMS.register("green_tea_leaves",
            () -> new BlockItem(TDBlocks.GREEN_TEA_LEAVES.get(), new Item.Properties()));
    //ITEMS
    public static final RegistryObject<Item> GREEN_TEA = ITEMS.register("green_tea",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DRIED_GREEN_TEA = ITEMS.register("dried_green_tea",
            () -> new Item(new Item.Properties()));
}
