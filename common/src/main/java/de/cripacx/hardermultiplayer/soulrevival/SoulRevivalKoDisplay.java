package de.cripacx.hardermultiplayer.soulrevival;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;

public final class SoulRevivalKoDisplay {
    private static final String DEAD_TEAM_NAME = "soulrevival_dead";
    private static final String NO_PREVIOUS_TEAM = "";
    private static final Component DEAD_PREFIX = Component.literal("[DEAD] ").withStyle(ChatFormatting.RED);
    private static final Component DEAD_TEAM_LABEL = Component.literal("Dead").withStyle(ChatFormatting.RED);
    private static final Component DEAD_ACTIONBAR = Component.literal("You are dead.").withStyle(ChatFormatting.RED);
    private static final Map<UUID, String> previousTeams = new ConcurrentHashMap<>();

    private SoulRevivalKoDisplay() {
    }

    public static void sync(ServerPlayer player) {
        if (SoulRevivalPersistence.getState().isKnockedOut(player.getUUID())) {
            applyDeadMarker(player);
        } else {
            clearDeadMarker(player);
        }
    }

    public static void tick(ServerPlayer player) {
        if (player.tickCount % 20 == 0) {
            player.sendSystemMessage(DEAD_ACTIONBAR, true);
        }
    }

    public static void clearDeadMarker(ServerPlayer player) {
        MinecraftServer server = player.level().getServer();
        if (server == null) {
            return;
        }

        ServerScoreboard scoreboard = server.getScoreboard();
        PlayerTeam deadTeam = scoreboard.getPlayerTeam(DEAD_TEAM_NAME);
        String scoreboardName = player.getScoreboardName();
        if (deadTeam != null && scoreboard.getPlayersTeam(scoreboardName) == deadTeam) {
            scoreboard.removePlayerFromTeam(scoreboardName, deadTeam);
        }

        String previousTeamName = previousTeams.remove(player.getUUID());
        if (previousTeamName == null || previousTeamName.isEmpty()) {
            return;
        }

        PlayerTeam previousTeam = scoreboard.getPlayerTeam(previousTeamName);
        if (previousTeam != null) {
            scoreboard.addPlayerToTeam(scoreboardName, previousTeam);
        }
    }

    private static void applyDeadMarker(ServerPlayer player) {
        MinecraftServer server = player.level().getServer();
        if (server == null) {
            return;
        }

        ServerScoreboard scoreboard = server.getScoreboard();
        PlayerTeam deadTeam = getOrCreateDeadTeam(scoreboard);
        String scoreboardName = player.getScoreboardName();
        PlayerTeam currentTeam = scoreboard.getPlayersTeam(scoreboardName);
        if (currentTeam == deadTeam) {
            return;
        }

        previousTeams.put(player.getUUID(), currentTeam != null ? currentTeam.getName() : NO_PREVIOUS_TEAM);
        if (currentTeam != null) {
            scoreboard.removePlayerFromTeam(scoreboardName, currentTeam);
        }
        scoreboard.addPlayerToTeam(scoreboardName, deadTeam);
    }

    private static PlayerTeam getOrCreateDeadTeam(ServerScoreboard scoreboard) {
        PlayerTeam team = scoreboard.getPlayerTeam(DEAD_TEAM_NAME);
        if (team != null) {
            return team;
        }

        team = scoreboard.addPlayerTeam(DEAD_TEAM_NAME);
        team.setDisplayName(DEAD_TEAM_LABEL);
        team.setColor(ChatFormatting.RED);
        team.setPlayerPrefix(DEAD_PREFIX);
        team.setNameTagVisibility(Team.Visibility.ALWAYS);
        return team;
    }
}