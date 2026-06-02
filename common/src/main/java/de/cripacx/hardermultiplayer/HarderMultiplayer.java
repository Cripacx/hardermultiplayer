package de.cripacx.hardermultiplayer;

import net.blay09.mods.balm.Balm;
import net.minecraft.resources.Identifier;
import net.blay09.mods.balm.core.BalmRegistrars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.cripacx.hardermultiplayer.block.ModBlocks;
import de.cripacx.hardermultiplayer.item.ModItems;
import de.cripacx.hardermultiplayer.soulrevival.SoulRevivalBootstrap;

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

        registrars.blocks(ModBlocks::initialize);
        registrars.items(ModItems::initialize);
        registrars.creativeModeTabs(ModItems::initialize);

        SoulRevivalBootstrap.initialize();
    }

}
