package crypto.factions.bloodfactions.commons.events.player.unpermissioned;

import crypto.factions.bloodfactions.commons.events.shared.FactionPlayerEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

public class ListInvitationsToFactionEvent extends FactionPlayerEvent {
    public ListInvitationsToFactionEvent(Faction faction, FPlayer player) {
        super(faction, player);
        this.launch();
    }
}
