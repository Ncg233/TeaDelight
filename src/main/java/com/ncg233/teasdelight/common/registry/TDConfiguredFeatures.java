package com.ncg233.teasdelight.common.registry;

import com.ncg233.teasdelight.TeaDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.registries.DeferredRegister;

public class TDConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> GREEN_TEA_KEY = registerKey("green_tea");
    public static void bootstrap(BootstapContext<ConfiguredFeature<?,?>> context){
        register(context,GREEN_TEA_KEY,Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(TDBlocks.CARNELIAN_LOG.get()),
                new StraightTrunkPlacer(5,6,3),
                BlockStateProvider.simple(TDBlocks.GREEN_TEA_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0),4),
                new TwoLayersFeatureSize(1,0,2)).build()
        );
    }
    public static final DeferredRegister<ConfiguredFeature<?,?>> aA = DeferredRegister.create(Registries.CONFIGURED_FEATURE,TeaDelight.MODID);
    public static ResourceKey<ConfiguredFeature<?,?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(TeaDelight.MODID, name));
    }
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                           ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
