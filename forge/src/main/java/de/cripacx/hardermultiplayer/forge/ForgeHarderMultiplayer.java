package de.cripacx.hardermultiplayer.forge;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.BalmClient;
import net.blay09.mods.balm.forge.platform.runtime.ForgeLoadContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import de.cripacx.hardermultiplayer.HarderMultiplayer;
import de.cripacx.hardermultiplayer.client.HarderMultiplayerClient;

@Mod(HarderMultiplayer.MOD_ID)
public class ForgeHarderMultiplayer {

    public ForgeHarderMultiplayer(FMLJavaModLoadingContext context) {
        final var loadContext = new ForgeLoadContext(context.getModBusGroup());
        Balm.initializeMod(HarderMultiplayer.MOD_ID, loadContext, HarderMultiplayer::initialize);
        if (FMLEnvironment.dist.isClient()) {
            BalmClient.initializeMod(HarderMultiplayer.MOD_ID, loadContext, HarderMultiplayerClient::initialize);
        }
    }

}
