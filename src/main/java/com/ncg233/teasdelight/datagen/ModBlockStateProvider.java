package com.ncg233.teasdelight.datagen;

import com.ncg233.teasdelight.TeaDelight;
import com.ncg233.teasdelight.common.registry.TDBlocks;
import com.ncg233.teasdelight.common.registry.TDItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output,ExistingFileHelper exFileHelper) {
        super(output, TeaDelight.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        logBlock(TDBlocks.CARNELIAN_LOG);
        leavesBlock(TDBlocks.GREEN_TEA_LEAVES,new ResourceLocation(TeaDelight.MODID, "block/green_tea_leaves"));
        //blockItem
        blockItem(TDItems.GREEN_TEA_LEAVES.getId());
        blockItem(TDItems.DRY_RACK.getId());
        blockItem(TDItems.CARNELIAN_LOG.getId());
    }
    public void leavesBlock(RegistryObject<? extends LeavesBlock> block, ResourceLocation resourceLocation) {
        this.getVariantBuilder(block.get()).partialState().modelForState().modelFile(this.models().singleTexture(block.getId().getPath(), new ResourceLocation("block/leaves"), "all", resourceLocation)).addModel();
    }
    public void logBlock(RegistryObject<? extends RotatedPillarBlock> block){
        logBlock(block.get());
    }
    public ItemModelBuilder blockItem(ResourceLocation item) {
        ResourceLocation resourceLocation = new ResourceLocation(TeaDelight.MODID, "block/" + item.getPath());
        return this.itemModels().getBuilder(item.toString()).parent(new ModelFile.UncheckedModelFile(resourceLocation));
    }
}
