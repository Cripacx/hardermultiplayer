package de.cripacx.hardermultiplayer.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.cripacx.hardermultiplayer.soulrevival.SoulRevivalRecipeRules;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;

@Mixin(CraftingMenu.class)
public class CraftingMenuMixin {

        @Inject(method = "slotChangedCraftingGrid", at = @At("TAIL"))
        private static void soulRevival$clearInvalidSoulCharmResult(
            AbstractContainerMenu menu,
            ServerLevel level,
            Player player,
            CraftingContainer container,
            ResultContainer resultSlots,
                        @Nullable RecipeHolder<CraftingRecipe> recipeHint,
                        CallbackInfo callbackInfo
    ) {
                ItemStack currentResult = resultSlots.getItem(0);
                if (currentResult.isEmpty()) {
                        return;
                }

                ItemStack filteredResult = SoulRevivalRecipeRules.filterSoulCharmCraftResult(currentResult, container);
                if (!filteredResult.isEmpty()) {
                        return;
                }

                resultSlots.setItem(0, filteredResult);
                menu.setRemoteSlot(0, filteredResult);
                if (player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, filteredResult));
                }
    }
}