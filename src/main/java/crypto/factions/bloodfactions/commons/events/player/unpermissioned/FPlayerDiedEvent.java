package crypto.factions.bloodfactions.commons.events.player.unpermissioned;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

public class FPlayerDiedEvent extends PlayerEvent {
    public FPlayerDiedEvent(FPlayer player) {
        super(player);
        this.launch();
    }
}
