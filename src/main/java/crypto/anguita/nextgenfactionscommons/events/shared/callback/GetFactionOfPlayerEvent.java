package crypto.anguita.nextgenfactionscommons.events.shared.callback;

import crypto.anguita.nextgenfactionscommons.events.shared.FactionPlayerEvent;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;
import lombok.Getter;

@Getter
public class GetFactionOfPlayerEvent extends FactionPlayerEvent {
    public GetFactionOfPlayerEvent(FPlayer player) {
        super(null, player);
        this.launch();
    }
}
