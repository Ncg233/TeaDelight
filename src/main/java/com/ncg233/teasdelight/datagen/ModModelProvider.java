package com.ncg233.teasdelight.datagen;

import com.ncg233.teasdelight.TeaDelight;
import com.ncg233.teasdelight.common.registry.TDBlocks;
import com.ncg233.teasdelight.common.registry.TDItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.util.Identifier;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        generator.registerParentedItemModel(TDBlocks.DRY_RACK ,new Identifier(TeaDelight.MODID, "block/dry_rack"));
    }
    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(TDItems.GREEN_TEA, Models.GENERATED);
        generator.register(TDItems.DRIED_GREEN_TEA, Models.GENERATED);
    }
}
