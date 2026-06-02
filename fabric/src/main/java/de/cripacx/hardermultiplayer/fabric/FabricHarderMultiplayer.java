package de.cripacx.hardermultiplayer.fabric;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.fabricmc.api.ModInitializer;
import de.cripacx.hardermultiplayer.HarderMultiplayer;

public class FabricHarderMultiplayer implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initializeMod(HarderMultiplayer.MOD_ID, FabricLoadContext.INSTANCE, HarderMultiplayer::initialize);
    }
}
