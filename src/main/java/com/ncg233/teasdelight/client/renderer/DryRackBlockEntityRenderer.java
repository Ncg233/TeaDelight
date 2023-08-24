package com.ncg233.teasdelight.client.renderer;

import com.ncg233.teasdelight.common.block.DryRackBlock;
import com.ncg233.teasdelight.common.block.entity.DryRackBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec2f;

@Environment(EnvType.CLIENT)
public class DryRackBlockEntityRenderer implements BlockEntityRenderer<DryRackBlockEntity> {
    public DryRackBlockEntityRenderer(BlockEntityRendererFactory.Context context){
    }
    @Override
    public void render(DryRackBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Direction direction = entity.getCachedState().get(DryRackBlock.FACING).getOpposite();
        DefaultedList<ItemStack> inventory = entity.getInventory();
        int intPos = (int)entity.getPos().asLong();

        for(int i = 0; i < inventory.size(); ++i) {
            ItemStack itemStack = inventory.get(i);
            if (!itemStack.isEmpty()) {
                matrices.push();
                matrices.translate(0.5, 0.5625, 0.5);
                float angle = -direction.asRotation();
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angle));
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
                Vec2f itemOffset = entity.getStoveItemOffset(i);
                matrices.translate(itemOffset.x, itemOffset.y, 0.0);
                matrices.scale(0.285F, 0.285F, 0.285F);
                if (entity.getWorld() != null) {
                    int lightPos = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos());
                    MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformationMode.FIXED, lightPos, overlay, matrices, vertexConsumers, entity.getWorld(), intPos + i);
                }
                matrices.pop();
            }
        }
    }
}
