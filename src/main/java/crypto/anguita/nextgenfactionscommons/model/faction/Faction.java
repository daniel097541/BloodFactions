package crypto.anguita.nextgenfactionscommons.model.faction;

import crypto.anguita.nextgenfactionscommons.api.PermissionNextGenFactionsAPI;
import crypto.anguita.nextgenfactionscommons.api.NextGenFactionsAPI;
import crypto.anguita.nextgenfactionscommons.model.NextGenFactionEntity;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;

import java.util.Set;

public interface Faction extends NextGenFactionEntity {

    default Set<FPlayer> getMembers(){
        return NextGenFactionsAPI.getPlayersInFaction(this);
    }

    default boolean kickPlayer(FPlayer kickedPlayer, FPlayer playerKicking) {
        return PermissionNextGenFactionsAPI.kickPlayerFromFaction(kickedPlayer, this, playerKicking);
    }

    default boolean invitePlayer(FPlayer invitedPlayer, FPlayer playerInviting) {
        return PermissionNextGenFactionsAPI.invitePlayerToFaction(invitedPlayer, this, playerInviting);
    }

    default boolean disband(FPlayer playerDisbanding) {
        return PermissionNextGenFactionsAPI.disbandFaction(this, playerDisbanding);
    }
}
