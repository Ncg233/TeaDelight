package com.ncg233.teasdelight.common.registry;

import com.ncg233.teasdelight.TeaDelight;
import com.ncg233.teasdelight.common.block.DryRackBlock;
import com.ncg233.teasdelight.common.worldgen.tree.GreenTeaTreeGrower;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TDBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            TeaDelight.MODID);
    public static final RegistryObject<Block> DRY_RACK = BLOCKS.register("dry_rack",
            () -> new DryRackBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
    public static final RegistryObject<RotatedPillarBlock> CARNELIAN_LOG = BLOCKS.register("carnelian_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<LeavesBlock> GREEN_TEA_LEAVES = BLOCKS.register("green_tea_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistryObject<SaplingBlock> GREEN_TEA_SAPLING = BLOCKS.register("green_tea_sapling",
            () -> new SaplingBlock(new GreenTeaTreeGrower(),BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

}
