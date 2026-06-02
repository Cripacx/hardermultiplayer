package de.cripacx.hardermultiplayer.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.blay09.mods.kuma.api.InputBinding;
import net.blay09.mods.kuma.api.Kuma;
import net.blay09.mods.kuma.api.ManagedKeyMapping;
import de.cripacx.hardermultiplayer.HarderMultiplayer;

import static de.cripacx.hardermultiplayer.HarderMultiplayer.id;

public class ModKeyMappings {

    public static ManagedKeyMapping yourKey;

    public static void initialize() {
        yourKey = Kuma.createKeyMapping(id("your_key"))
                .withDefault(InputBinding.key(InputConstants.KEY_B))
                .handleScreenInput(event -> {
                    HarderMultiplayer.logger.info("B was pressed - " + HarderMultiplayer.MOD_ID);
                    return true;
                })
                .build();
    }
}
