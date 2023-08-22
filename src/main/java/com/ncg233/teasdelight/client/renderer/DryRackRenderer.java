package com.ncg233.teasdelight.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ncg233.teasdelight.common.block.entity.DryRackBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;
import vectorwing.farmersdelight.common.block.StoveBlock;

public class DryRackRenderer implements BlockEntityRenderer<DryRackBlockEntity> {
    public DryRackRenderer(BlockEntityRendererProvider.Context context) {
    }
    @Override
    public void render(DryRackBlockEntity entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        Direction direction = entity.getBlockState().getValue(StoveBlock.FACING).getOpposite();
        ItemStackHandler inventory = entity.getInventory();
        int posLong = (int)entity.getBlockPos().asLong();

        for(int i = 0; i < inventory.getSlots(); ++i) {
            ItemStack stoveStack = inventory.getStackInSlot(i);
            if (!stoveStack.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.5, 0.5625, 0.5);
                float f = -direction.toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(f));
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                Vec2 itemOffset = entity.getStoveItemOffset(i);
                poseStack.translate(itemOffset.x, itemOffset.y, 0.0);
                poseStack.scale(0.285F, 0.285F, 0.285F);
                if (entity.getLevel() != null) {
                    Minecraft.getInstance().getItemRenderer().renderStatic(stoveStack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(entity.getLevel(), entity.getBlockPos()), combinedOverlayIn, poseStack, buffer, entity.getLevel(), posLong + i);
                }

                poseStack.popPose();
            }
        }
    }
}
