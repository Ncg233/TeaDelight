package com.ncg233.teasdelight.datagen;

import com.ncg233.teasdelight.TeaDelight;
import com.ncg233.teasdelight.common.registry.TDItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public static final String GENERATED = "item/generated";
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TeaDelight.MODID, existingFileHelper);
    }
    @Override
    protected void registerModels() {
        //blockItem
        blockItem(TDItems.GREEN_TEA_LEAVES.getId());
        blockItem(TDItems.DRY_RACK.getId());
        blockItem(TDItems.CARNELIAN_LOG.getId());

        simpleItem(TDItems.GREEN_TEA);
        simpleItem(TDItems.DRIED_GREEN_TEA);
    }
    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation(GENERATED)).texture("layer0",
                new ResourceLocation(TeaDelight.MODID,"item/" + item.getId().getPath()));
    }
    public ItemModelBuilder blockItem(ResourceLocation item) {
        ResourceLocation resourceLocation = new ResourceLocation(TeaDelight.MODID, "block/" + item.getPath());
        return this.getBuilder(item.toString()).parent(new ModelFile.UncheckedModelFile(resourceLocation));
    }
}
