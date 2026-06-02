package de.cripacx.hardermultiplayer.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import de.cripacx.hardermultiplayer.HarderMultiplayer;
import de.cripacx.hardermultiplayer.block.ModBlocks;
import de.cripacx.hardermultiplayer.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                shaped(RecipeCategory.DECORATIONS, ModBlocks.yourBlock)
                        .pattern("DDD")
                        .pattern("SSS")
                        .pattern("DDD")
                        .define('D', Items.DIAMOND)
                        .define('S', Items.STICK)
                        .unlockedBy("has_diamond", has(Items.DIAMOND))
                        .save(exporter);

                    shaped(RecipeCategory.MISC, ModItems.soulCharm)
                        .pattern("DGD")
                        .pattern("GAG")
                        .pattern("DGD")
                        .define('D', Items.DIAMOND_BLOCK)
                        .define('G', Items.GOLD_BLOCK)
                        .define('A', Items.GOLDEN_APPLE)
                        .unlockedBy("has_golden_apple", has(Items.GOLDEN_APPLE))
                        .save(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return HarderMultiplayer.MOD_ID;
    }
}
