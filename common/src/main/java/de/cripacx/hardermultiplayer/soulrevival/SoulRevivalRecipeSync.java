package de.cripacx.hardermultiplayer.soulrevival;

import java.util.List;
import java.util.Optional;

import de.cripacx.hardermultiplayer.HarderMultiplayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

public final class SoulRevivalRecipeSync {
    private static final ResourceKey<Recipe<?>> SOUL_CHARM_STAGE_1 = recipeKey("soul_charm_stage_1");
    private static final ResourceKey<Recipe<?>> SOUL_CHARM_STAGE_2 = recipeKey("soul_charm_stage_2");
    private static final ResourceKey<Recipe<?>> SOUL_CHARM_STAGE_3 = recipeKey("soul_charm_stage_3");
    private static final List<ResourceKey<Recipe<?>>> SOUL_CHARM_RECIPES = List.of(
            SOUL_CHARM_STAGE_1,
            SOUL_CHARM_STAGE_2,
            SOUL_CHARM_STAGE_3
    );

    private SoulRevivalRecipeSync() {
    }

    public static void syncAll(MinecraftServer server) {
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            sync(player);
        }
    }

    public static void sync(ServerPlayer player) {
        MinecraftServer server = player.level().getServer();
        if (server == null) {
            return;
        }

        RecipeManager recipeManager = server.getRecipeManager();
        List<RecipeHolder<?>> stageRecipes = SOUL_CHARM_RECIPES.stream()
                .map(recipeManager::byKey)
                .flatMap(Optional::stream)
                .toList();

        if (stageRecipes.isEmpty()) {
            return;
        }

        player.getRecipeBook().removeRecipes(stageRecipes, player);
        recipeManager.byKey(recipeKeyForStage(SoulRevivalPersistence.getState().stage()))
                .ifPresent(recipeHolder -> player.getRecipeBook().addRecipes(List.of(recipeHolder), player));
    }

    private static ResourceKey<Recipe<?>> recipeKeyForStage(SoulRevivalStage stage) {
        return switch (stage) {
            case STAGE_1 -> SOUL_CHARM_STAGE_1;
            case STAGE_2 -> SOUL_CHARM_STAGE_2;
            case STAGE_3 -> SOUL_CHARM_STAGE_3;
        };
    }

    private static ResourceKey<Recipe<?>> recipeKey(String path) {
        return ResourceKey.create(Registries.RECIPE, HarderMultiplayer.id(path));
    }
}