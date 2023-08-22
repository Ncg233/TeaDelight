package com.ncg233.teasdelight.common.registry;

import com.ncg233.teasdelight.TeaDelight;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TDItemGroup {
    public static final ItemGroup TEA_DELIGHT = Registry.register(Registries.ITEM_GROUP,
            new Identifier(TeaDelight.MODID,"tea_delight"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.teasdelight"))
                    .icon(() -> new ItemStack(TDItems.GREEN_TEA))
                    .entries(TDItemGroup::buildDisplayItems).build());
    private static void buildDisplayItems(ItemGroup.DisplayContext displayContext,
                                          ItemGroup.Entries entries){
        //add Items to the tab
        TDItems.ITEMS.forEach(entries::add);
    }
    //load this class
    public static void registerItemGroups(){
        TeaDelight.LOGGER.info("Registering ItemGroups for" + TeaDelight.MODID);
    }
}
