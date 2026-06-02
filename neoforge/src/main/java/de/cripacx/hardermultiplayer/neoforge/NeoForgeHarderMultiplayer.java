package de.cripacx.hardermultiplayer.neoforge;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.neoforge.platform.runtime.NeoForgeLoadContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import de.cripacx.hardermultiplayer.HarderMultiplayer;

@Mod(HarderMultiplayer.MOD_ID)
public class NeoForgeHarderMultiplayer {

    public NeoForgeHarderMultiplayer(ModContainer modContainer, IEventBus modEventBus) {
        final var context = new NeoForgeLoadContext(modContainer, modEventBus);
        Balm.initializeMod(HarderMultiplayer.MOD_ID, context, HarderMultiplayer::initialize);
    }
}
