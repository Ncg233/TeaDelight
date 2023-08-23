package com.ncg233.teasdelight.common.registry;

import com.ncg233.teasdelight.TeaDelight;
import com.ncg233.teasdelight.common.block.DryRackBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.ncg233.teasdelight.TeaDelight.createIdentifier;

public class TDBlocks {
    public static final Block DRY_RACK = registerBlock("dry_rack",
            new DryRackBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));
    private static Block registerBlock(String name, Block block){
        Item item = registerBlockItem(name, block);
        TDItems.ITEMS.add(item);
        return Registry.register(Registries.BLOCK,createIdentifier(name), block);
    }
    private static Item registerBlockItem(String name, Block block){
        return TDItems.registerItem(name,new BlockItem(block, new FabricItemSettings()));
    }
    public static void registerTDBlocks() {
        TeaDelight.LOGGER.info("registering blocks for" + TeaDelight.MODID);
    }
}
