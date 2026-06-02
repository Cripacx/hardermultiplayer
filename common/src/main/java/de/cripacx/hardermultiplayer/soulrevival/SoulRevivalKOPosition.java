package de.cripacx.hardermultiplayer.soulrevival;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public record SoulRevivalKOPosition(String dimension, double x, double y, double z, float yaw, float pitch) {

    public static SoulRevivalKOPosition from(ServerLevel level, double x, double y, double z, float yaw, float pitch) {
        ResourceKey<Level> dimensionKey = level.dimension();
        Identifier id = dimensionKey.identifier();
        return new SoulRevivalKOPosition(id.toString(), x, y, z, yaw, pitch);
    }

    public ResourceKey<Level> dimensionKey() {
        return ResourceKey.create(Registries.DIMENSION, Identifier.parse(dimension));
    }
}
