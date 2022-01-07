package crypto.factions.bloodfactions.commons.events.player.unpermissioned;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

public class FPlayerLoginEvent extends PlayerEvent {
    public FPlayerLoginEvent(FPlayer player) {
        super(player);
        this.launch();
    }
}
