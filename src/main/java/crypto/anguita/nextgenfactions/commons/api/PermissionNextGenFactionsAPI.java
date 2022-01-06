package crypto.anguita.nextgenfactions.commons.api;

import crypto.anguita.nextgenfactions.commons.events.faction.permissioned.DisbandFactionEvent;
import crypto.anguita.nextgenfactions.commons.events.faction.permissioned.InvitePlayerToFactionEvent;
import crypto.anguita.nextgenfactions.commons.events.faction.permissioned.KickPlayerFromFactionEvent;
import crypto.anguita.nextgenfactions.commons.events.land.permissioned.*;
import crypto.anguita.nextgenfactions.commons.events.role.ChangeRoleOfPlayerEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.land.FChunk;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import crypto.anguita.nextgenfactions.commons.model.role.FactionRole;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class PermissionNextGenFactionsAPI {

    /**
     * Un Claims a Set of chunks from a Faction.
     *
     * @param faction
     * @param chunks
     * @param player
     * @return
     */
    public static Map<FChunk, Boolean> multiUnClaim(Faction faction, Set<FChunk> chunks, FPlayer player) {
        MultiUnClaimEvent event = new MultiUnClaimEvent(faction, player, chunks);
        return event.getResult();
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
        UnClaimEvent event = new UnClaimEvent(faction, player, chunk);
        return event.isSuccess();
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
        MultiClaimEvent event = new MultiClaimEvent(faction, player, chunks);
        return event.getResult();
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
        ClaimEvent claimEvent = new ClaimEvent(faction, player, chunk);
        return claimEvent.isSuccess();
    }

    /**
     * Disbands a Faction.
     *
     * @param faction
     * @param player
     */
    public static boolean disbandFaction(Faction faction, FPlayer player) {
        DisbandFactionEvent event = new DisbandFactionEvent(player, faction);
        return event.isDisbanded();
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
        InvitePlayerToFactionEvent event = new InvitePlayerToFactionEvent(faction, memberThatInvites, invitedPlayer);
        return event.isInvited();
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
        KickPlayerFromFactionEvent event = new KickPlayerFromFactionEvent(faction, playerThatKicks, kickedPlayer);
        return event.isKicked();
    }

    /**
     * Changes the role of a player.
     * @param player
     * @param newRole
     * @param playerChangingTheRole
     * @return
     */
    public static boolean changeRoleOfPlayer(@NotNull FPlayer player, @NotNull FactionRole newRole, @NotNull FPlayer playerChangingTheRole){
        boolean changed = false;
        if(player.hasFaction()){
            ChangeRoleOfPlayerEvent event = new ChangeRoleOfPlayerEvent(player, newRole, playerChangingTheRole);
            changed = event.isChanged();
        }
        return changed;
    }

    public static boolean overClaim(Faction faction, FChunk chunk, FPlayer player) {
        OverClaimEvent overClaimEvent = new OverClaimEvent(player.getFaction(), faction, player, chunk);
        return overClaimEvent.isSuccess();
    }
}
