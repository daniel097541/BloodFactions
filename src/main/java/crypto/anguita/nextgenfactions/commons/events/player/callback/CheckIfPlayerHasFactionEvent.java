package crypto.anguita.nextgenfactions.commons.events.player.callback;

import crypto.anguita.nextgenfactions.commons.events.player.PlayerEvent;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
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
