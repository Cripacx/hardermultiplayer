package de.cripacx.hardermultiplayer;

import net.blay09.mods.balm.platform.config.reflection.Comment;
import net.blay09.mods.balm.platform.config.reflection.Config;
import net.blay09.mods.balm.platform.config.reflection.Synced;

@Config(HarderMultiplayer.MOD_ID)
public class HarderMultiplayerConfig {

    @Comment("Enable Soul Revival gameplay logic.")
    @Synced
    public boolean enableSoulRevival = true;

    @Comment("Allow reviving KO players by right-clicking them with a Soul Charm.")
    @Synced
    public boolean enableRightClickRevive = true;

    @Comment("Allow reviving KO players by tossing a Soul Charm at them.")
    @Synced
    public boolean enableTossRevive = true;

    @Comment("Automatically advance stage on first Nether and End entry.")
    @Synced
    public boolean enableAutoStageProgression = true;
}
