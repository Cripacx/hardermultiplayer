package de.cripacx.hardermultiplayer.soulrevival;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class SoulRevivalState {
    private SoulRevivalStage stage;
    private final ConcurrentMap<UUID, SoulRevivalKOPosition> knockedOutPlayers = new ConcurrentHashMap<>();

    public SoulRevivalState() {
        this(SoulRevivalStage.STAGE_1);
    }

    public SoulRevivalState(SoulRevivalStage stage) {
        this.stage = Objects.requireNonNull(stage);
    }

    public SoulRevivalStage stage() {
        return stage;
    }

    public void setStage(SoulRevivalStage stage) {
        this.stage = Objects.requireNonNull(stage);
    }

    public boolean isKnockedOut(UUID playerId) {
        return knockedOutPlayers.containsKey(playerId);
    }

    public Optional<SoulRevivalKOPosition> getKnockedOutPosition(UUID playerId) {
        return Optional.ofNullable(knockedOutPlayers.get(playerId));
    }

    public void setKnockedOut(UUID playerId, SoulRevivalKOPosition position) {
        knockedOutPlayers.put(Objects.requireNonNull(playerId), Objects.requireNonNull(position));
    }

    public boolean clearKnockedOut(UUID playerId) {
        return knockedOutPlayers.remove(Objects.requireNonNull(playerId)) != null;
    }

    public ConcurrentMap<UUID, SoulRevivalKOPosition> knockedOutPlayers() {
        return knockedOutPlayers;
    }
}
