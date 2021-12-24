package crypto.anguita.nextgenfactionscommons.api;

import crypto.anguita.nextgenfactionscommons.events.faction.permissioned.DisbandFactionEvent;
import crypto.anguita.nextgenfactionscommons.events.faction.permissioned.InvitePlayerToFactionEvent;
import crypto.anguita.nextgenfactionscommons.events.faction.permissioned.KickPlayerFromFactionEvent;
import crypto.anguita.nextgenfactionscommons.events.land.permissioned.ClaimEvent;
import crypto.anguita.nextgenfactionscommons.events.land.permissioned.MultiClaimEvent;
import crypto.anguita.nextgenfactionscommons.events.land.permissioned.MultiUnClaimEvent;
import crypto.anguita.nextgenfactionscommons.events.land.permissioned.UnClaimEvent;
import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import crypto.anguita.nextgenfactionscommons.model.land.FChunk;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;

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


}
