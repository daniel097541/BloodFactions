package crypto.factions.bloodfactions.commons.events.player.callback;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckPlayerAutoFlyingEvent extends PlayerEvent {

    private boolean autoFlying;

    public CheckPlayerAutoFlyingEvent(FPlayer player) {
        super(player);
        this.launch();
    }
}
