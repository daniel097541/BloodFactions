package crypto.factions.bloodfactions.commons.events.player.callback;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckIfPlayerHasFactionEvent extends PlayerEvent {
    private boolean hasFaction;

    public CheckIfPlayerHasFactionEvent(FPlayer player) {
        super(player);
        this.launch();
    }
}
