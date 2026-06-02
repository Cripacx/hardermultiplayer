package de.cripacx.hardermultiplayer.mixin;

import de.cripacx.hardermultiplayer.soulrevival.SoulRevivalRecipeRules;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResultSlot.class)
public class ResultSlotMixin {

    @Shadow
    @Final
    private CraftingContainer craftSlots;

    @Inject(method = "onTake(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    private void soulRevival$blockInvalidSoulCharmRecipe(Player player, ItemStack itemStack, CallbackInfo ci) {
        if (SoulRevivalRecipeRules.shouldBlockSoulCharmCraft(player, itemStack, craftSlots)) {
            ci.cancel();
        }
    }
}
