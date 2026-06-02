package de.cripacx.hardermultiplayer.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static de.cripacx.hardermultiplayer.HarderMultiplayer.id;

public class ModItemTags {
    public static final TagKey<Item> SOUL_CHARMS = TagKey.create(Registries.ITEM, id("soul_charms"));
}
