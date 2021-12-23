package crypto.anguita.nextgenfactionscommons.api;

import crypto.anguita.nextgenfactionscommons.events.faction.permissioned.DisbandFactionEvent;
import crypto.anguita.nextgenfactionscommons.events.faction.permissioned.InvitePlayerToFactionEvent;
import crypto.anguita.nextgenfactionscommons.events.faction.permissioned.KickPlayerFromFactionEvent;
import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;

public class PermissionNextGenFactionsAPI {

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
