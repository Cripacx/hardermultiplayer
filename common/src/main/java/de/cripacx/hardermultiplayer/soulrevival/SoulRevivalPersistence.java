package de.cripacx.hardermultiplayer.soulrevival;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import de.cripacx.hardermultiplayer.HarderMultiplayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class SoulRevivalPersistence {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "soul_revival_state.json";

    private static SoulRevivalState state = new SoulRevivalState();

    private SoulRevivalPersistence() {
    }

    public static SoulRevivalState getState() {
        return state;
    }

    public static void load(MinecraftServer server) {
        Path path = statePath(server);
        if (!Files.exists(path)) {
            state = new SoulRevivalState();
            return;
        }

        try (Reader reader = Files.newBufferedReader(path)) {
            SavedState savedState = GSON.fromJson(reader, SavedState.class);
            if (savedState == null) {
                state = new SoulRevivalState();
                return;
            }
            state = new SoulRevivalState(SoulRevivalStage.fromValue(savedState.stage));
            if (savedState.knockedOutPlayers != null) {
                for (SavedKOPlayer entry : savedState.knockedOutPlayers) {
                    try {
                        UUID playerId = UUID.fromString(entry.playerId);
                        SoulRevivalKOPosition position = new SoulRevivalKOPosition(
                                entry.dimension,
                                entry.x,
                                entry.y,
                                entry.z,
                                entry.yaw,
                                entry.pitch
                        );
                        state.setKnockedOut(playerId, position);
                    } catch (Exception ignored) {
                        HarderMultiplayer.logger.warn("Skipping invalid KO entry in Soul Revival state file");
                    }
                }
            }
        } catch (IOException | JsonParseException ex) {
            HarderMultiplayer.logger.error("Failed to load Soul Revival state", ex);
            state = new SoulRevivalState();
        }
    }

    public static void save(MinecraftServer server) {
        Path path = statePath(server);
        try {
            Files.createDirectories(path.getParent());
            try (Writer writer = Files.newBufferedWriter(path)) {
                SavedState savedState = new SavedState();
                savedState.stage = state.stage().value();
                savedState.knockedOutPlayers = new ArrayList<>();
                for (var entry : state.knockedOutPlayers().entrySet()) {
                    SavedKOPlayer savedKOPlayer = new SavedKOPlayer();
                    savedKOPlayer.playerId = entry.getKey().toString();
                    savedKOPlayer.dimension = entry.getValue().dimension();
                    savedKOPlayer.x = entry.getValue().x();
                    savedKOPlayer.y = entry.getValue().y();
                    savedKOPlayer.z = entry.getValue().z();
                    savedKOPlayer.yaw = entry.getValue().yaw();
                    savedKOPlayer.pitch = entry.getValue().pitch();
                    savedState.knockedOutPlayers.add(savedKOPlayer);
                }
                GSON.toJson(savedState, writer);
            }
        } catch (IOException ex) {
            HarderMultiplayer.logger.error("Failed to save Soul Revival state", ex);
        }
    }

    public static boolean setStage(SoulRevivalStage stage) {
        if (state.stage() == stage) {
            return false;
        }
        state.setStage(stage);
        return true;
    }

    private static Path statePath(MinecraftServer server) {
        return server.getWorldPath(LevelResource.ROOT).resolve("data").resolve(HarderMultiplayer.MOD_ID).resolve(FILE_NAME);
    }

    private static final class SavedState {
        int stage = 1;
        List<SavedKOPlayer> knockedOutPlayers = List.of();
    }

    private static final class SavedKOPlayer {
        String playerId;
        String dimension;
        double x;
        double y;
        double z;
        float yaw;
        float pitch;
    }
}
