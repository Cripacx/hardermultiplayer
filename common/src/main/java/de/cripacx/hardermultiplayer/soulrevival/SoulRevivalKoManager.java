package de.cripacx.hardermultiplayer.soulrevival;

import de.cripacx.hardermultiplayer.HarderMultiplayer;
import de.cripacx.hardermultiplayer.item.ModItems;
import net.blay09.mods.balm.platform.event.callback.BlockCallback;
import net.blay09.mods.balm.platform.event.callback.InteractionEventResult;
import net.blay09.mods.balm.platform.event.callback.ItemCallback;
import net.blay09.mods.balm.platform.event.callback.LivingEntityCallback;
import net.blay09.mods.balm.platform.event.callback.PlayerCallback;
import net.blay09.mods.balm.platform.event.callback.ServerPlayerCallback;
import net.blay09.mods.balm.platform.event.callback.ServerTickCallback;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class SoulRevivalKoManager {

    private static final Map<UUID, SoulRevivalKOPosition> pendingRespawns = new ConcurrentHashMap<>();

    private SoulRevivalKoManager() {
    }

    public static void initialize() {
        LivingEntityCallback.Death.Before.EVENT.register(SoulRevivalKoManager::onBeforeDeath);
        LivingEntityCallback.Damage.Before.EVENT.register(SoulRevivalKoManager::onDamage);

        ServerPlayerCallback.Respawn.EVENT.register(SoulRevivalKoManager::onRespawn);
        ServerPlayerCallback.Join.EVENT.register(SoulRevivalKoManager::onJoin);

        ServerTickCallback.ServerPlayerTick.AFTER.register(SoulRevivalKoManager::onPlayerTick);
        ServerTickCallback.ServerEntityTick.BEFORE.register(SoulRevivalKoManager::onEntityTick);

        PlayerCallback.Attack.Before.EVENT.register(SoulRevivalKoManager::allowAttack);
        BlockCallback.Break.Before.EVENT.register(SoulRevivalKoManager::allowBreakBlock);
        BlockCallback.Use.EVENT.register(SoulRevivalKoManager::onUseBlock);
        ItemCallback.Use.EVENT.register(SoulRevivalKoManager::onUseItem);
        ItemCallback.Toss.Before.EVENT.register(SoulRevivalKoManager::allowToss);
    }

    private static boolean onBeforeDeath(net.minecraft.world.entity.LivingEntity entity, net.minecraft.world.damagesource.DamageSource damageSource) {
        if (!HarderMultiplayer.config().enableSoulRevival || !(entity instanceof ServerPlayer player)) {
            return true;
        }

        if (isKnockedOut(player)) {
            return false;
        }

        SoulRevivalKOPosition position = SoulRevivalKOPosition.from(
            (ServerLevel) player.level(),
                player.getX(),
                player.getY(),
                player.getZ(),
                player.getYRot(),
                player.getXRot()
        );
        pendingRespawns.put(player.getUUID(), position);
        return true;
    }

    private static float onDamage(net.minecraft.world.entity.LivingEntity entity, net.minecraft.world.damagesource.DamageSource source, float damageAmount) {
        if (entity instanceof ServerPlayer player && isKnockedOut(player)) {
            return 0f;
        }
        return damageAmount;
    }

    private static void onRespawn(ServerPlayer oldPlayer, ServerPlayer newPlayer) {
        SoulRevivalKOPosition position = pendingRespawns.remove(oldPlayer.getUUID());
        if (position == null) {
            return;
        }

        SoulRevivalPersistence.getState().setKnockedOut(newPlayer.getUUID(), position);
        teleportToKoPosition(newPlayer, position);
        MinecraftServer server = newPlayer.level().getServer();
        if (server != null) {
            SoulRevivalPersistence.save(server);
        }
    }

    private static void onJoin(ServerPlayer player) {
        SoulRevivalPersistence.getState().getKnockedOutPosition(player.getUUID())
                .ifPresent(position -> teleportToKoPosition(player, position));
    }

    private static void onPlayerTick(ServerPlayer player) {
        handleAutoStageProgression(player);

        SoulRevivalPersistence.getState().getKnockedOutPosition(player.getUUID()).ifPresent(position -> {
            // Keep KO players anchored at their KO point while still allowing look/chat.
            if (player.distanceToSqr(position.x(), position.y(), position.z()) > 0.01) {
                teleportToKoPosition(player, position);
            }
            player.setDeltaMovement(0, 0, 0);
            player.fallDistance = 0;
        });
    }

    private static void onEntityTick(Entity entity) {
        if (!(entity instanceof Mob mob)) {
            return;
        }

        if (mob.getTarget() instanceof ServerPlayer target && isKnockedOut(target)) {
            mob.setTarget(null);
        }
    }

    private static boolean allowAttack(Player player, Entity target) {
        return !isKnockedOut(player);
    }

    private static boolean allowBreakBlock(net.minecraft.world.level.LevelAccessor level, net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state, net.minecraft.world.level.block.entity.BlockEntity blockEntity, Player player) {
        return player == null || !isKnockedOut(player);
    }

    private static InteractionEventResult onUseBlock(Player player, net.minecraft.world.level.Level level, net.minecraft.world.InteractionHand hand, net.minecraft.world.phys.BlockHitResult hitResult) {
        return isKnockedOut(player) ? InteractionEventResult.FAIL : InteractionEventResult.DEFAULT;
    }

    private static InteractionEventResult onUseItem(Player player, net.minecraft.world.level.Level level, net.minecraft.world.InteractionHand hand) {
        return isKnockedOut(player) ? InteractionEventResult.FAIL : InteractionEventResult.DEFAULT;
    }

    private static boolean allowToss(Player player, net.minecraft.world.item.ItemStack itemStack) {
        if (!isKnockedOut(player)) {
            return true;
        }

        // Keep toss blocked while KO; toss-based revive will be implemented via non-KO throwers.
        return false;
    }

    private static boolean isKnockedOut(Player player) {
        return SoulRevivalPersistence.getState().isKnockedOut(player.getUUID());
    }

    private static void teleportToKoPosition(ServerPlayer player, SoulRevivalKOPosition position) {
        MinecraftServer server = player.level().getServer();
        if (server == null) {
            return;
        }

        ServerLevel targetLevel = server.getLevel(position.dimensionKey());
        if (targetLevel == null) {
            HarderMultiplayer.logger.warn("Could not resolve KO dimension {} for {}", position.dimension(), player.getGameProfile().name());
            return;
        }

        player.teleportTo(targetLevel, position.x(), position.y(), position.z(), java.util.Set.of(), position.yaw(), position.pitch(), false);
    }

    private static void handleAutoStageProgression(ServerPlayer player) {
        if (!HarderMultiplayer.config().enableAutoStageProgression) {
            return;
        }

        SoulRevivalStage currentStage = SoulRevivalPersistence.getState().stage();
        if (currentStage == SoulRevivalStage.STAGE_1 && player.level().dimension() == Level.NETHER) {
            if (SoulRevivalPersistence.setStage(SoulRevivalStage.STAGE_2)) {
                MinecraftServer server = player.level().getServer();
                if (server != null) {
                    SoulRevivalPersistence.save(server);
                    HarderMultiplayer.logger.info("Soul Revival auto progression: stage 1 -> stage 2");
                }
            }
            return;
        }

        if (currentStage == SoulRevivalStage.STAGE_2 && player.level().dimension() == Level.END) {
            if (SoulRevivalPersistence.setStage(SoulRevivalStage.STAGE_3)) {
                MinecraftServer server = player.level().getServer();
                if (server != null) {
                    SoulRevivalPersistence.save(server);
                    HarderMultiplayer.logger.info("Soul Revival auto progression: stage 2 -> stage 3");
                }
            }
        }
    }

    public static boolean revive(ServerPlayer target, Player reviver) {
        if (!SoulRevivalPersistence.getState().isKnockedOut(target.getUUID())) {
            return false;
        }

        if (!consumeSoulCharm(reviver)) {
            return false;
        }

        SoulRevivalPersistence.getState().clearKnockedOut(target.getUUID());
        target.setHealth(target.getMaxHealth());
        target.removeAllEffects();
        MinecraftServer server = target.level().getServer();
        if (server != null) {
            SoulRevivalPersistence.save(server);
        }
        return true;
    }

    private static boolean consumeSoulCharm(Player player) {
        if (player.getMainHandItem().is(ModItems.soulCharm)) {
            player.getMainHandItem().shrink(1);
            return true;
        }
        if (player.getOffhandItem().is(ModItems.soulCharm)) {
            player.getOffhandItem().shrink(1);
            return true;
        }
        return false;
    }
}
