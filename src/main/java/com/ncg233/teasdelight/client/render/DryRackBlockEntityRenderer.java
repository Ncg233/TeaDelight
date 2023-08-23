package com.ncg233.teasdelight.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class DryRackBlockEntityRenderer {
//    public DryRackBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
//    }
//
//    public void render(StoveBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
//        Direction direction = entity.getCachedState().get(StoveBlock.FACING).getOpposite();
//        DefaultedList<ItemStack> inventory = entity.getInventory();
//        int intPos = (int)entity.getPos().asLong();
//
//        for(int i = 0; i < inventory.size(); ++i) {
//            ItemStack itemStack = (ItemStack)inventory.get(i);
//            if (!itemStack.isEmpty()) {
//                matrices.push();
//                matrices.translate(0.5, 1.02, 0.5);
//                float angle = -direction.asRotation();
//                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angle));
//                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
//                Vec2f itemOffset = entity.getStoveItemOffset(i);
//                matrices.translate((double)itemOffset.x, (double)itemOffset.y, 0.0);
//                matrices.scale(0.375F, 0.375F, 0.375F);
//                if (entity.getWorld() != null) {
//                    int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
//                    MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformationMode.FIXED, lightAbove, overlay, matrices, vertexConsumers, entity.getWorld(), intPos + i);
//                }
//
//                matrices.pop();
//            }
//        }
//
//    }
}
