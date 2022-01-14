package crypto.factions.bloodfactions.commons.model.invitation;

import crypto.factions.bloodfactions.commons.api.NextGenFactionsAPI;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

import java.util.Date;
import java.util.UUID;

public interface FactionInvitation {

    UUID getFactionId();

    UUID getPlayerId();

    UUID getInviterId();

    Date getDate();

    default Faction getFaction(){
        return NextGenFactionsAPI.getFaction(this.getFactionId());
    }

    default FPlayer getPlayer(){
        return NextGenFactionsAPI.getPlayer(this.getPlayerId());
    }

    default FPlayer getInviter(){
        return NextGenFactionsAPI.getPlayer(this.getInviterId());
    }
}
