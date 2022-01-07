package crypto.factions.bloodfactions.commons.api;

import crypto.factions.bloodfactions.commons.events.faction.SetCoreEvent;
import crypto.factions.bloodfactions.commons.events.faction.permissioned.DisbandFactionEvent;
import crypto.factions.bloodfactions.commons.events.faction.permissioned.InvitePlayerToFactionEvent;
import crypto.factions.bloodfactions.commons.events.faction.permissioned.KickPlayerFromFactionEvent;
import crypto.factions.bloodfactions.commons.events.land.permissioned.*;
import crypto.factions.bloodfactions.commons.events.player.permissioned.PlayerFlightEvent;
import crypto.factions.bloodfactions.commons.events.role.ChangeRoleOfPlayerEvent;
import crypto.factions.bloodfactions.commons.logger.Logger;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.land.FLocation;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import crypto.factions.bloodfactions.commons.model.role.FactionRole;
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
    public static boolean changeRoleOfPlayer(@NotNull FPlayer player, @NotNull FactionRole newRole, @NotNull FPlayer playerChangingTheRole) {
        long start = System.currentTimeMillis();
        boolean changed = false;
        if (player.hasFaction()) {
            ChangeRoleOfPlayerEvent event = new ChangeRoleOfPlayerEvent(player, newRole, playerChangingTheRole);
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

    public static boolean toggleFlightMode(FPlayer player) {
        long start = System.currentTimeMillis();
        PlayerFlightEvent event = new PlayerFlightEvent(player.getFaction(), player);
        boolean success = event.isSuccess();
        long end = System.currentTimeMillis();
        logAction(start, end, PermissionedAPIAction.FLY);
        return success;
    }
}
