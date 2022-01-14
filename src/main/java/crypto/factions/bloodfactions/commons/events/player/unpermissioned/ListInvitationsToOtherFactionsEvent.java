package crypto.factions.bloodfactions.commons.events.player.unpermissioned;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

public class ListInvitationsToOtherFactionsEvent extends PlayerEvent {
    public ListInvitationsToOtherFactionsEvent(FPlayer player) {
        super(player);
        this.launch();
    }
}
