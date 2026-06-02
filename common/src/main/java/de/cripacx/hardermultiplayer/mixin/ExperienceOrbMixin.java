package de.cripacx.hardermultiplayer.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.cripacx.hardermultiplayer.soulrevival.SoulRevivalPersistence;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbMixin {

    @Inject(method = "playerTouch", at = @At("HEAD"), cancellable = true)
    private void blockKoExperiencePickup(Player player, CallbackInfo callbackInfo) {
        if (SoulRevivalPersistence.getState().isKnockedOut(player.getUUID())) {
            callbackInfo.cancel();
        }
    }
}