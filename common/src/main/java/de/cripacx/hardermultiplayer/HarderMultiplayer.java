package de.cripacx.hardermultiplayer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cripacx.hardermultiplayer.item.ModItems;
import de.cripacx.hardermultiplayer.soulrevival.SoulRevivalBootstrap;
import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.core.BalmRegistrars;
import net.minecraft.resources.Identifier;

public class HarderMultiplayer {

    public static final Logger logger = LoggerFactory.getLogger(HarderMultiplayer.class);

    public static final String MOD_ID = "hardermultiplayer";

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static HarderMultiplayerConfig config() {
        return Balm.config().getActiveConfig(HarderMultiplayerConfig.class);
    }

    public static void initialize(BalmRegistrars registrars) {
        Balm.config().registerConfig(HarderMultiplayerConfig.class);

        registrars.items(ModItems::initialize);
        registrars.creativeModeTabs(ModItems::initialize);

        SoulRevivalBootstrap.initialize();
    }

}
