package de.cripacx.hardermultiplayer.fabric.datagen;

import java.util.concurrent.CompletableFuture;

import de.cripacx.hardermultiplayer.HarderMultiplayer;
import de.cripacx.hardermultiplayer.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
            shaped(RecipeCategory.MISC, ModItems.soulCharm)
                .pattern("DGD")
                .pattern("GAG")
                .pattern("DGD")
                .define('D', Items.DIAMOND_BLOCK)
                .define('G', Items.GOLD_BLOCK)
                .define('A', Items.GOLDEN_APPLE)
                .unlockedBy("has_golden_apple", has(Items.GOLDEN_APPLE))
                .save(exporter, recipeKey("soul_charm_stage_1"));

            shaped(RecipeCategory.MISC, ModItems.soulCharm)
                .pattern("BNB")
                .pattern("NGN")
                .pattern("BNB")
                .define('B', Items.BLAZE_ROD)
                .define('N', Items.NETHERITE_SCRAP)
                .define('G', Items.GHAST_TEAR)
                .unlockedBy("has_ghast_tear", has(Items.GHAST_TEAR))
                .save(exporter, recipeKey("soul_charm_stage_2"));

            shaped(RecipeCategory.MISC, ModItems.soulCharm)
                .pattern("SES")
                .pattern("NAN")
                .pattern("CEC")
                .define('S', Items.NETHER_STAR)
                .define('E', Items.ECHO_SHARD)
                .define('N', Items.NETHERITE_INGOT)
                .define('C', Items.END_CRYSTAL)
                .define('A', Items.ENCHANTED_GOLDEN_APPLE)
                .unlockedBy("has_nether_star", has(Items.NETHER_STAR))
                        .save(exporter, recipeKey("soul_charm_stage_3"));
            }
        };
    }

    private static ResourceKey<Recipe<?>> recipeKey(String path) {
        return ResourceKey.create(Registries.RECIPE, HarderMultiplayer.id(path));
    }

    @Override
    public String getName() {
        return HarderMultiplayer.MOD_ID;
    }
}
