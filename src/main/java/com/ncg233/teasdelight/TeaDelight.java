package com.ncg233.teasdelight;

import com.ncg233.teasdelight.common.registry.TDItemGroup;
import com.ncg233.teasdelight.common.registry.TDItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeaDelight implements ModInitializer {
	public static final String MODID = "teasdelight";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		TDItems.registerModItems();
		TDItemGroup.registerItemGroups();
		LOGGER.info("Hello Tea's Delight");
	}
}