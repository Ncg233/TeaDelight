package com.ncg233.teasdelight.datagen;

import com.ncg233.teasdelight.TeaDelight;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = TeaDelight.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        if(event.includeServer()){
            generator.addProvider(true,new ModRecipes(packOutput));
            generator.addProvider(true,new ModBlockTagsProvider(packOutput,lookupProvider,helper));
            generator.addProvider(true,new ModWorldGenProvider(packOutput,lookupProvider));
        }
        if(event.includeClient()){
            generator.addProvider(true,new ModItemModelProvider(packOutput,helper));
            generator.addProvider(true,new ModBlockStateProvider(packOutput,helper));
        }


    }
}
