package crypto.factions.bloodfactions.commons.model.invitation;

import crypto.factions.bloodfactions.commons.contex.ContextHandler;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

import java.util.UUID;

public interface FactionInvitation {

    UUID getFactionId();

    UUID getPlayerId();

    UUID getInviterId();

    String getDate();

    default Faction getFaction(){
        return ContextHandler.getFaction(this.getFactionId());
    }

    default FPlayer getPlayer(){
        return ContextHandler.getPlayer(this.getPlayerId());
    }

    default FPlayer getInviter(){
        return ContextHandler.getPlayer(this.getInviterId());
    }
}
