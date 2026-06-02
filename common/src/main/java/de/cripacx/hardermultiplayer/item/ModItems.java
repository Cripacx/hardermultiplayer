package de.cripacx.hardermultiplayer.item;

import net.blay09.mods.balm.world.item.BalmCreativeModeTabRegistrar;
import net.blay09.mods.balm.world.item.BalmItemRegistrar;
import net.blay09.mods.balm.world.item.DeferredItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import de.cripacx.hardermultiplayer.HarderMultiplayer;

import static de.cripacx.hardermultiplayer.HarderMultiplayer.id;

public class ModItems {
    public static DeferredItem yourItem;

    public static void initialize(BalmItemRegistrar items) {
        yourItem = items.register("your_item", Item::new).asDeferredItem();
    }

    public static void initialize(BalmCreativeModeTabRegistrar creativeModeTabs) {
        creativeModeTabs.register(HarderMultiplayer.MOD_ID, builder ->
                builder.title(Component.translatable(id(HarderMultiplayer.MOD_ID).toLanguageKey("itemGroup")))
                        .icon(() -> ModItems.yourItem.createStack())
                        .displayItems((displayParameters, output) -> {
                            output.accept(ModItems.yourItem);
                        })
        );
    }

}
