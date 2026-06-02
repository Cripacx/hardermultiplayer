package de.cripacx.hardermultiplayer.neoforge.client;

import net.blay09.mods.balm.client.BalmClient;
import net.blay09.mods.balm.neoforge.platform.runtime.NeoForgeLoadContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import de.cripacx.hardermultiplayer.HarderMultiplayer;
import de.cripacx.hardermultiplayer.client.HarderMultiplayerClient;

@Mod(value = HarderMultiplayer.MOD_ID, dist = Dist.CLIENT)
public class NeoForgeHarderMultiplayerClient {

    public NeoForgeHarderMultiplayerClient(ModContainer modContainer, IEventBus modEventBus) {
        final var context = new NeoForgeLoadContext(modContainer, modEventBus);
        BalmClient.initializeMod(HarderMultiplayer.MOD_ID, context, HarderMultiplayerClient::initialize);
    }
}
