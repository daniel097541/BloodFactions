package crypto.anguita.nextgenfactionscommons.events.player.callback;

import crypto.anguita.nextgenfactionscommons.events.player.PlayerEvent;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;
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
