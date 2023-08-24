package com.ncg233.teasdelight.client;

import com.ncg233.teasdelight.client.renderer.DryRackBlockEntityRenderer;
import com.ncg233.teasdelight.common.registry.TDBlockEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

public class TeaDelightClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(TDBlockEntityTypes.DRY_RACK, DryRackBlockEntityRenderer::new);
    }
}
