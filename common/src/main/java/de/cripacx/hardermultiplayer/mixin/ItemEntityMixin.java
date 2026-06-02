package de.cripacx.hardermultiplayer.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.cripacx.hardermultiplayer.item.ModItems;
import de.cripacx.hardermultiplayer.soulrevival.SoulRevivalKoManager;
import de.cripacx.hardermultiplayer.soulrevival.SoulRevivalPersistence;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(method = "playerTouch", at = @At("HEAD"), cancellable = true)
    private void blockKoPickupExceptSoulCharm(Player player, CallbackInfo callbackInfo) {
        if (!SoulRevivalPersistence.getState().isKnockedOut(player.getUUID())) {
            return;
        }

        ItemEntity itemEntity = (ItemEntity) (Object) this;
        ItemStack stack = itemEntity.getItem();
        if (stack.is(ModItems.soulCharm)) {
            if (SoulRevivalKoManager.reviveBySoulCharmPickup(player, itemEntity)) {
                callbackInfo.cancel();
            }
            return;
        }

        callbackInfo.cancel();
    }
}