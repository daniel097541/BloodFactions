package crypto.anguita.nextgenfactionscommons.events.player.action;

import crypto.anguita.nextgenfactionscommons.events.player.PlayerEvent;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;
import lombok.Getter;

@Getter
public class SavePlayerEvent extends PlayerEvent {
    public SavePlayerEvent(FPlayer player) {
        super(player);
        this.launch();
    }
}
