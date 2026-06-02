package de.cripacx.hardermultiplayer.soulrevival;

import de.cripacx.hardermultiplayer.HarderMultiplayer;
import net.blay09.mods.balm.platform.event.callback.ServerLifecycleCallback;
import net.blay09.mods.balm.platform.event.callback.ServerPlayerCallback;

public final class SoulRevivalBootstrap {
    private SoulRevivalBootstrap() {
    }

    public static void initialize() {
        SoulRevivalCommands.register();
        SoulRevivalKoManager.initialize();

        ServerLifecycleCallback.Started.EVENT.register(server -> {
            SoulRevivalPersistence.load(server);
            SoulRevivalRecipeSync.syncAll(server);
            HarderMultiplayer.logger.info("Soul Revival state loaded (stage={})", SoulRevivalPersistence.getState().stage().value());
        });

        ServerPlayerCallback.Join.EVENT.register(SoulRevivalRecipeSync::sync);

        ServerLifecycleCallback.Stopping.EVENT.register(SoulRevivalPersistence::save);
    }
}
