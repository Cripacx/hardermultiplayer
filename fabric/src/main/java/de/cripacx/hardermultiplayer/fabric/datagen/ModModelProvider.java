package de.cripacx.hardermultiplayer.fabric.datagen;

import java.util.Optional;

import de.cripacx.hardermultiplayer.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.resources.Identifier;

public class ModModelProvider extends FabricModelProvider {
    private static final ModelTemplate TOTEM_PARENT = new ModelTemplate(
        Optional.of(Identifier.withDefaultNamespace("item/totem_of_undying")),
            Optional.empty()
    );

    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        // This mod does not register custom blocks yet.
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(ModItems.soulCharm.asItem(), TOTEM_PARENT);
    }

}
