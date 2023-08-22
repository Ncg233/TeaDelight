package com.ncg233.teasdelight.common.registry;

import com.ncg233.teasdelight.TeaDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;

public class TDCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            TeaDelight.MODID);
    public static final RegistryObject<CreativeModeTab> TEA_DELIGHT = CREATIVE_TABS.register("teasdelight",() ->
        CreativeModeTab.builder().title(Component.translatable("itemGroup.teasdelight"))
                .icon(() -> new ItemStack(TDItems.GREEN_TEA.get()))
                .displayItems(TDCreativeTab::buildDisplayItems)
                .build()
    );
    private static void buildDisplayItems(CreativeModeTab.ItemDisplayParameters parameters,
                                          CreativeModeTab.Output output){
        //add Items to the tab
        TDItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()));
    }
}
