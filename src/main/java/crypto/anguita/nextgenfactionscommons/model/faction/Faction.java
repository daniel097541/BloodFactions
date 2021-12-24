package crypto.anguita.nextgenfactionscommons.model.faction;

import crypto.anguita.nextgenfactionscommons.api.NextGenFactionsAPI;
import crypto.anguita.nextgenfactionscommons.api.PermissionNextGenFactionsAPI;
import crypto.anguita.nextgenfactionscommons.model.NextGenFactionEntity;
import crypto.anguita.nextgenfactionscommons.model.land.FChunk;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;

import java.util.Map;
import java.util.Set;

public interface Faction extends NextGenFactionEntity {

    default Map<FChunk, Boolean> multiUnClaim(Set<FChunk> chunks, FPlayer player){
        return PermissionNextGenFactionsAPI.multiUnClaim(this, chunks, player);
    }

    default boolean unClaim(FChunk chunk, FPlayer player){
        return PermissionNextGenFactionsAPI.unClaim(this, chunk, player);
    }

    default Map<FChunk, Boolean> multiClaim(Set<FChunk> chunks, FPlayer player) {
        return PermissionNextGenFactionsAPI.multiClaim(this, chunks, player);
    }

    default boolean claim(FChunk chunk, FPlayer player) {
        return PermissionNextGenFactionsAPI.claim(this, chunk, player);
    }

    default Set<FPlayer> getMembers() {
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
