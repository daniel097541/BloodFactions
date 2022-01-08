package crypto.factions.bloodfactions.commons.events.player.unpermissioned;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

public class FPlayerFallDamageEvent extends PlayerEvent {

    public FPlayerFallDamageEvent(FPlayer player) {
        super(player);
        this.launch();
    }
}
