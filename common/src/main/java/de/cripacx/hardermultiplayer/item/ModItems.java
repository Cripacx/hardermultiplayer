package de.cripacx.hardermultiplayer.item;

import net.blay09.mods.balm.world.item.BalmCreativeModeTabRegistrar;
import net.blay09.mods.balm.world.item.BalmItemRegistrar;
import net.blay09.mods.balm.world.item.DeferredItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import de.cripacx.hardermultiplayer.HarderMultiplayer;

import static de.cripacx.hardermultiplayer.HarderMultiplayer.id;

public class ModItems {
    public static DeferredItem soulCharm;

    public static void initialize(BalmItemRegistrar items) {
        soulCharm = items.register("soul_charm", Item::new, properties -> properties.stacksTo(1)).asDeferredItem();
    }

    public static void initialize(BalmCreativeModeTabRegistrar creativeModeTabs) {
        creativeModeTabs.register(HarderMultiplayer.MOD_ID, builder ->
                builder.title(Component.translatable(id(HarderMultiplayer.MOD_ID).toLanguageKey("itemGroup")))
                        .icon(() -> ModItems.soulCharm.createStack())
                        .displayItems((displayParameters, output) -> {
                            output.accept(ModItems.soulCharm);
                        })
        );
    }

}
