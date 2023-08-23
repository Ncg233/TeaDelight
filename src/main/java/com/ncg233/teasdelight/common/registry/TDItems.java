package com.ncg233.teasdelight.common.registry;

import com.google.common.collect.Sets;
import com.ncg233.teasdelight.TeaDelight;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.LinkedHashSet;

import static com.ncg233.teasdelight.TeaDelight.createIdentifier;

public class TDItems {
    public static final LinkedHashSet<Item> ITEMS = Sets.newLinkedHashSet();
    public static final Item GREEN_TEA = registerItem("green_tea", new Item(new FabricItemSettings()));
    public static final Item DRIED_GREEN_TEA = registerItem("dried_green_tea", new Item(new FabricItemSettings()));
    static Item registerItem(String name, Item item) {
        Item register = Registry.register(Registries.ITEM, createIdentifier(name), item);
        ITEMS.add(register);
        return register;
    }
    //load this class
    public static void registerItems(){
        TeaDelight.LOGGER.info("Registering items for" + TeaDelight.MODID);
    }
}
