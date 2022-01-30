package crypto.factions.bloodfactions.commons.contex;

import crypto.factions.bloodfactions.BloodFactions;
import crypto.factions.bloodfactions.commons.events.faction.SetCoreEvent;
import crypto.factions.bloodfactions.commons.events.faction.permissioned.*;
import crypto.factions.bloodfactions.commons.events.land.permissioned.*;
import crypto.factions.bloodfactions.commons.events.player.permissioned.PlayerAutoFlyEvent;
import crypto.factions.bloodfactions.commons.events.player.permissioned.PlayerFlightEvent;
import crypto.factions.bloodfactions.commons.events.role.ChangeRankOfPlayerEvent;
import crypto.factions.bloodfactions.commons.events.role.CreateRankEvent;
import crypto.factions.bloodfactions.commons.events.role.DeleteRankEvent;
import crypto.factions.bloodfactions.commons.logger.Logger;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.land.FLocation;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRank;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class PermissionContextHandler {

    private static void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(BloodFactions.getInstance(), runnable);
    }

    private static void logAction(long start, long end, PermissionedContextAction action) {
        String message = "Time to perform permission action {action}: {time} ms";
        message = message
                .replace("{action}", action.name())
                .replace("{time}", String.valueOf(end - start));
        Logger.logInfo("&bPERMISSIONS CONTEXT", message);
    }

    /**
     * Un Claims a Set of chunks from a Faction.
     *
     * @param faction
     * @param chunks
     * @param player
     * @return
     */
    public static void multiUnClaim(Faction faction, Set<FChunk> chunks, FPlayer player) {
        PermissionContextHandler.runAsync(() -> {
            long start = System.currentTimeMillis();
            MultiUnClaimEvent event = new MultiUnClaimEvent(faction, player, chunks);
            Map<FChunk, Boolean> result = event.getResult();
            long end = System.currentTimeMillis();
            logAction(start, end, PermissionedContextAction.MULTI_UN_CLAIM);
        });
    }

    /**
     * Un Claims a chunk of a Faction.
     *
     * @param faction
     * @param chunk
     * @param player
     * @return
     */
    public static void unClaim(Faction faction, FChunk chunk, FPlayer player) {
        PermissionContextHandler.runAsync(() -> {
            long start = System.currentTimeMillis();
            UnClaimEvent event = new UnClaimEvent(faction, player, chunk);
            boolean result = event.isSuccess();
            long end = System.currentTimeMillis();
            logAction(start, end, PermissionedContextAction.UN_CLAIM);
        });
    }

    /**
     * Claims multiple chunks for a Faction.
     *
     * @param faction
     * @param chunks
     * @param player
     * @return
     */
    public static void multiClaim(Faction faction, Set<FChunk> chunks, FPlayer player, int radius) {
        PermissionContextHandler.runAsync(() -> {
            long start = System.currentTimeMillis();
            MultiClaimEvent event = new MultiClaimEvent(faction, player, chunks, radius);
            Map<FChunk, Boolean> result = event.getResult();
            long end = System.currentTimeMillis();
            logAction(start, end, PermissionedContextAction.MULTI_CLAIM);
        });
    }

    /**
     * Claims a chunk for the Faction.
     *
     * @param faction
     * @param chunk
     * @param player
     * @return
     */
    public static void claim(Faction faction, FChunk chunk, FPlayer player) {
        PermissionContextHandler.runAsync(() -> {
            long start = System.currentTimeMillis();
            ClaimEvent claimEvent = new ClaimEvent(faction, player, chunk);
            boolean claimed = claimEvent.isSuccess();
            long end = System.currentTimeMillis();
            logAction(start, end, PermissionedContextAction.CLAIM);
        });
    }

    /**
     * Disbands a Faction.
     *
     * @param faction
     * @param player
     */
    public static void disbandFaction(Faction faction, FPlayer player) {
        PermissionContextHandler.runAsync(() -> {
            long start = System.currentTimeMillis();
            DisbandFactionEvent event = new DisbandFactionEvent(player, faction);
            boolean disbanded = event.isDisbanded();
            long end = System.currentTimeMillis();
            logAction(start, end, PermissionedContextAction.DISBAND);
        });
    }

    /**
     * Invites a Player to the Faction.
     *
     * @param invitedPlayer
     * @param faction
     * @param memberThatInvites
     * @return
     */
    public static void invitePlayerToFaction(FPlayer invitedPlayer, Faction faction, FPlayer memberThatInvites) {
        PermissionContextHandler.runAsync(() -> {
            long start = System.currentTimeMillis();
            InvitePlayerToFactionEvent event = new InvitePlayerToFactionEvent(faction, memberThatInvites, invitedPlayer);
            boolean invited = event.isInvited();
            long end = System.currentTimeMillis();
            logAction(start, end, PermissionedContextAction.INVITE);
        });
    }

    /**
     * Kicks a player from the Faction.
     *
     * @param kickedPlayer
     * @param faction
     * @param playerThatKicks
     * @return
     */
    public static void kickPlayerFromFaction(FPlayer kickedPlayer, Faction faction, FPlayer playerThatKicks) {
        PermissionContextHandler.runAsync(() -> {
            long start = System.currentTimeMillis();
            KickPlayerFromFactionEvent event = new KickPlayerFromFactionEvent(faction, playerThatKicks, kickedPlayer);
            boolean kicked = event.isKicked();
            long end = System.currentTimeMillis();
            logAction(start, end, PermissionedContextAction.KICK);
        });
    }

    /**
     * Changes the role of a player.
     *
     * @param player
     * @param newRole
     * @param playerChangingTheRole
     * @return
     */
    public static void changeRankOfPlayer(@NotNull FPlayer player, @NotNull FactionRank newRole, @NotNull FPlayer playerChangingTheRole) {
        PermissionContextHandler.runAsync(() -> {
            long start = System.currentTimeMillis();
            if (player.hasFaction()) {
                new ChangeRankOfPlayerEvent(player, newRole, playerChangingTheRole);
            }
            long end = System.currentTimeMillis();
            logAction(start, end, PermissionedContextAction.CHANGE_ROLE);
        });
    }

    public static void overClaim(@NotNull Faction faction, @NotNull Faction overClaimedFaction, @NotNull FChunk chunk, @NotNull FPlayer player) {
        PermissionContextHandler.runAsync(() -> {
            long start = System.currentTimeMillis();
            OverClaimEvent overClaimEvent = new OverClaimEvent(faction, overClaimedFaction, player, chunk);
            boolean overClaimed = overClaimEvent.isSuccess();
            long end = System.currentTimeMillis();
            logAction(start, end, PermissionedContextAction.OVER_CLAIM);
        });
    }

    public static void setCore(FPlayer player, Faction faction, FLocation location) {
        PermissionContextHandler.runAsync(() -> {
            long start = System.currentTimeMillis();
            SetCoreEvent event = new SetCoreEvent(faction, player, location);
            boolean success = event.isSuccess();
            long end = System.currentTimeMillis();
            logAction(start, end, PermissionedContextAction.SET_CORE);
        });
    }

    public static void toggleFlightMode(FPlayer player, boolean toggle) {
        PermissionContextHandler.runAsync(() -> {
            long start = System.currentTimeMillis();
            PlayerFlightEvent event = new PlayerFlightEvent(player.getFaction(), player, toggle);
            boolean flying = event.isFlying();
            long end = System.currentTimeMillis();
            logAction(start, end, PermissionedContextAction.FLY);
        });
    }

    public static void toggleAutoFly(FPlayer player) {
        PermissionContextHandler.runAsync(() -> {
            long start = System.currentTimeMillis();
            PlayerAutoFlyEvent event = new PlayerAutoFlyEvent(player.getFaction(), player);
            boolean success = event.isAutoFlying();
            long end = System.currentTimeMillis();
            logAction(start, end, PermissionedContextAction.AUTO_FLY);
        });
    }

    public static void createRank(String rankName, FPlayer player, Faction faction, Set<PermissionType> permissions) {
        PermissionContextHandler.runAsync(() -> {
            long start = System.currentTimeMillis();
            CreateRankEvent event = new CreateRankEvent(faction, player, rankName, permissions);
            long end = System.currentTimeMillis();
            logAction(start, end, PermissionedContextAction.CREATE_ROLE);
        });
    }

    public static void deleteRank(String rankName, FPlayer player, Faction faction) {
        PermissionContextHandler.runAsync(() -> {
            long start = System.currentTimeMillis();
            DeleteRankEvent event = new DeleteRankEvent(faction, player, rankName);
            long end = System.currentTimeMillis();
            logAction(start, end, PermissionedContextAction.DELETE_ROLE);
        });
    }

    public static void unClaimAll(FPlayer player, Faction faction) {
        PermissionContextHandler.runAsync(() -> {
            long start = System.currentTimeMillis();
            UnClaimAllEvent event = new UnClaimAllEvent(faction, player);
            long end = System.currentTimeMillis();
            logAction(start, end, PermissionedContextAction.UN_CLAIM_ALL);
        });
    }

    public static boolean breakBlock(FPlayer player, Faction faction, FLocation location) {
        long start = System.currentTimeMillis();
        PlayerBreakBlockInFactionEvent event = new PlayerBreakBlockInFactionEvent(faction, player, location);
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedContextAction.BREAK_BLOCK);
        return event.isSuccess();
    }

    public static boolean placeBlock(FPlayer player, Faction faction, FLocation location) {
        long start = System.currentTimeMillis();
        PlayerPlaceBlockInFactionEvent event = new PlayerPlaceBlockInFactionEvent(faction, player, location);
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedContextAction.PLACE_BLOCK);
        return event.isSuccess();
    }
}
