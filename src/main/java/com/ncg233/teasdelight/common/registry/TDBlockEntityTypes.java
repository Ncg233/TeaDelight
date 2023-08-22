package com.ncg233.teasdelight.common.registry;

import com.ncg233.teasdelight.TeaDelight;
import com.ncg233.teasdelight.common.block.entity.DryRackBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TDBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES,
            TeaDelight.MODID);
    public static final RegistryObject<BlockEntityType<DryRackBlockEntity>> DRY_RACK =
            BLOCK_ENTITIES.register("dry_rack",
            () -> BlockEntityType.Builder.of(DryRackBlockEntity::new,TDBlocks.DRY_RACK.get()).build(null));
}
