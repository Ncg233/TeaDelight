package com.ncg233.teasdelight.common.registry;

import net.minecraft.block.Block;
import com.ncg233.teasdelight.TeaDelight;
import com.ncg233.teasdelight.common.block.entity.DryRackBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TDBlockEntityTypes {
    public static BlockEntityType<DryRackBlockEntity> DRY_RACK = entity("juicer", DryRackBlockEntity::new, TDBlocks.DRY_RACK);
    public static void registerBlockEntity() {
        TeaDelight.LOGGER.info("Registering BlockEntities for" + TeaDelight.MODID);
    }
    private static BlockEntityType entity(String name, FabricBlockEntityTypeBuilder.Factory<? extends BlockEntity> entity, Block block) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(TeaDelight.MODID, name),
                FabricBlockEntityTypeBuilder.create(entity, block).build(null));
    }
}
