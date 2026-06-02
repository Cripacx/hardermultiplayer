package de.cripacx.hardermultiplayer.fabric.client;

import net.blay09.mods.balm.client.BalmClient;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.fabricmc.api.ClientModInitializer;
import de.cripacx.hardermultiplayer.HarderMultiplayer;
import de.cripacx.hardermultiplayer.client.HarderMultiplayerClient;

public class FabricHarderMultiplayerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BalmClient.initializeMod(HarderMultiplayer.MOD_ID, FabricLoadContext.INSTANCE, HarderMultiplayerClient::initialize);
    }
}
