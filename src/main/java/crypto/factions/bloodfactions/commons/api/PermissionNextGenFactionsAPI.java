package crypto.factions.bloodfactions.commons.api;

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
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class PermissionNextGenFactionsAPI {

    private static void logAction(long start, long end, PermissionedAPIAction action) {
        String message = "Time to perform permission action {action}: {time} ms";
        message = message
                .replace("{action}", action.name())
                .replace("{time}", String.valueOf(end - start));
        Logger.logInfo("&bPERMISSIONS API", message);
    }

    /**
     * Un Claims a Set of chunks from a Faction.
     *
     * @param faction
     * @param chunks
     * @param player
     * @return
     */
    public static Map<FChunk, Boolean> multiUnClaim(Faction faction, Set<FChunk> chunks, FPlayer player) {
        long start = System.currentTimeMillis();
        MultiUnClaimEvent event = new MultiUnClaimEvent(faction, player, chunks);
        Map<FChunk, Boolean> result = event.getResult();
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.MULTI_UN_CLAIM);
        return result;
    }

    /**
     * Un Claims a chunk of a Faction.
     *
     * @param faction
     * @param chunk
     * @param player
     * @return
     */
    public static boolean unClaim(Faction faction, FChunk chunk, FPlayer player) {
        long start = System.currentTimeMillis();
        UnClaimEvent event = new UnClaimEvent(faction, player, chunk);
        boolean result = event.isSuccess();
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.UN_CLAIM);
        return result;
    }

    /**
     * Claims multiple chunks for a Faction.
     *
     * @param faction
     * @param chunks
     * @param player
     * @return
     */
    public static Map<FChunk, Boolean> multiClaim(Faction faction, Set<FChunk> chunks, FPlayer player) {
        long start = System.currentTimeMillis();
        MultiClaimEvent event = new MultiClaimEvent(faction, player, chunks);
        Map<FChunk, Boolean> result = event.getResult();
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.MULTI_CLAIM);
        return result;
    }

    /**
     * Claims a chunk for the Faction.
     *
     * @param faction
     * @param chunk
     * @param player
     * @return
     */
    public static boolean claim(Faction faction, FChunk chunk, FPlayer player) {
        long start = System.currentTimeMillis();
        ClaimEvent claimEvent = new ClaimEvent(faction, player, chunk);
        boolean claimed = claimEvent.isSuccess();
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.CLAIM);
        return claimed;
    }

    /**
     * Disbands a Faction.
     *
     * @param faction
     * @param player
     */
    public static boolean disbandFaction(Faction faction, FPlayer player) {
        long start = System.currentTimeMillis();
        DisbandFactionEvent event = new DisbandFactionEvent(player, faction);
        boolean disbanded = event.isDisbanded();
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.DISBAND);
        return disbanded;
    }

    /**
     * Invites a Player to the Faction.
     *
     * @param invitedPlayer
     * @param faction
     * @param memberThatInvites
     * @return
     */
    public static boolean invitePlayerToFaction(FPlayer invitedPlayer, Faction faction, FPlayer memberThatInvites) {
        long start = System.currentTimeMillis();
        InvitePlayerToFactionEvent event = new InvitePlayerToFactionEvent(faction, memberThatInvites, invitedPlayer);
        boolean invited = event.isInvited();
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.INVITE);
        return invited;
    }

    /**
     * Kicks a player from the Faction.
     *
     * @param kickedPlayer
     * @param faction
     * @param playerThatKicks
     * @return
     */
    public static boolean kickPlayerFromFaction(FPlayer kickedPlayer, Faction faction, FPlayer playerThatKicks) {
        long start = System.currentTimeMillis();
        KickPlayerFromFactionEvent event = new KickPlayerFromFactionEvent(faction, playerThatKicks, kickedPlayer);
        boolean kicked = event.isKicked();
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.KICK);
        return kicked;
    }

    /**
     * Changes the role of a player.
     *
     * @param player
     * @param newRole
     * @param playerChangingTheRole
     * @return
     */
    public static boolean changeRankOfPlayer(@NotNull FPlayer player, @NotNull FactionRank newRole, @NotNull FPlayer playerChangingTheRole) {
        long start = System.currentTimeMillis();
        boolean changed = false;
        if (player.hasFaction()) {
            ChangeRankOfPlayerEvent event = new ChangeRankOfPlayerEvent(player, newRole, playerChangingTheRole);
            changed = event.isChanged();
        }
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.CHANGE_ROLE);
        return changed;
    }

    public static boolean overClaim(Faction faction, Faction overClaimedFaction, FChunk chunk, FPlayer player) {
        long start = System.currentTimeMillis();
        OverClaimEvent overClaimEvent = new OverClaimEvent(faction, overClaimedFaction, player, chunk);
        boolean overClaimed = overClaimEvent.isSuccess();
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.OVER_CLAIM);
        return overClaimed;
    }

    public static boolean setCore(FPlayer player, Faction faction, FLocation location) {
        long start = System.currentTimeMillis();
        SetCoreEvent event = new SetCoreEvent(faction, player, location);
        boolean success = event.isSuccess();
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.SET_CORE);
        return success;
    }

    public static boolean toggleFlightMode(FPlayer player, boolean toggle) {
        long start = System.currentTimeMillis();
        PlayerFlightEvent event = new PlayerFlightEvent(player.getFaction(), player, toggle);
        boolean flying = event.isFlying();
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.FLY);
        return flying;
    }

    public static boolean toggleAutoFly(FPlayer player) {
        long start = System.currentTimeMillis();
        PlayerAutoFlyEvent event = new PlayerAutoFlyEvent(player.getFaction(), player);
        boolean success = event.isAutoFlying();
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.AUTO_FLY);
        return success;
    }

    public static FactionRank createRank(String rankName, FPlayer player, Faction faction, Set<PermissionType> permissions) {
        long start = System.currentTimeMillis();
        CreateRankEvent event = new CreateRankEvent(faction, player, rankName, permissions);
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.CREATE_ROLE);
        return event.getRole();
    }

    public static boolean deleteRank(String rankName, FPlayer player, Faction faction) {
        long start = System.currentTimeMillis();
        DeleteRankEvent event = new DeleteRankEvent(faction, player, rankName);
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.DELETE_ROLE);
        return event.isSuccess();
    }

    public static boolean unClaimAll(FPlayer player, Faction faction) {
        long start = System.currentTimeMillis();
        UnClaimAllEvent event = new UnClaimAllEvent(faction, player);
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.UN_CLAIM_ALL);
        return event.isSuccess();
    }

    public static boolean breakBlock(FPlayer player, Faction faction, FLocation location) {
        long start = System.currentTimeMillis();
        PlayerBreakBlockInFactionEvent event = new PlayerBreakBlockInFactionEvent(faction, player, location);
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.BREAK_BLOCK);
        return event.isSuccess();
    }

    public static boolean placeBlock(FPlayer player, Faction faction, FLocation location) {
        long start = System.currentTimeMillis();
        PlayerPlaceBlockInFactionEvent event = new PlayerPlaceBlockInFactionEvent(faction, player, location);
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.PLACE_BLOCK);
        return event.isSuccess();
    }
}
