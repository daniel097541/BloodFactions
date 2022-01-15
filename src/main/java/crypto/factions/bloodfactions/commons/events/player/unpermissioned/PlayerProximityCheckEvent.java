package crypto.factions.bloodfactions.commons.events.player.unpermissioned;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

public class PlayerProximityCheckEvent extends PlayerEvent {
    public PlayerProximityCheckEvent(FPlayer player) {
        super(player);
        this.launch();
    }
}
