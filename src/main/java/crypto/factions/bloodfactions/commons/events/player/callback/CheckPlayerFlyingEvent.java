package crypto.factions.bloodfactions.commons.events.player.callback;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckPlayerFlyingEvent extends PlayerEvent {

    private boolean flying;

    public CheckPlayerFlyingEvent(FPlayer player) {
        super(player);
        this.launch();
    }
}
