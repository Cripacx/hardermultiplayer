package de.cripacx.hardermultiplayer.soulrevival;

import de.cripacx.hardermultiplayer.HarderMultiplayer;
import net.blay09.mods.balm.platform.event.callback.ServerLifecycleCallback;

public final class SoulRevivalBootstrap {
    private SoulRevivalBootstrap() {
    }

    public static void initialize() {
        SoulRevivalCommands.register();
        SoulRevivalKoManager.initialize();

        ServerLifecycleCallback.Started.EVENT.register(server -> {
            SoulRevivalPersistence.load(server);
            HarderMultiplayer.logger.info("Soul Revival state loaded (stage={})", SoulRevivalPersistence.getState().stage().value());
        });

        ServerLifecycleCallback.Stopping.EVENT.register(SoulRevivalPersistence::save);
    }
}
