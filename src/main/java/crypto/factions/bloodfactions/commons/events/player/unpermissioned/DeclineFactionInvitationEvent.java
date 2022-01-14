package crypto.factions.bloodfactions.commons.events.player.unpermissioned;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import crypto.factions.bloodfactions.commons.events.shared.FactionPlayerEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

public class DeclineFactionInvitationEvent extends FactionPlayerEvent {
    public DeclineFactionInvitationEvent(Faction faction, FPlayer player) {
        super(faction, player);
        this.launch();
    }
}
