package de.cripacx.hardermultiplayer.fabric.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import de.cripacx.hardermultiplayer.block.ModBlocks;
import de.cripacx.hardermultiplayer.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.createTrivialCube(ModBlocks.yourBlock.asBlock());
        blockStateModelGenerator.registerSimpleItemModel(ModBlocks.yourBlock.asBlock(), ModelLocationUtils.getModelLocation(ModBlocks.yourBlock.asItem()));
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(ModItems.yourItem.asItem(), ModelTemplates.FLAT_ITEM);
    }

}
