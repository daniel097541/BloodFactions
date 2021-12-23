package crypto.anguita.nextgenfactionscommons.events.player.callback;

import crypto.anguita.nextgenfactionscommons.events.player.PlayerEvent;
import lombok.Getter;

@Getter
public class GetPlayerByNameEvent extends PlayerEvent {
    private final String name;

    public GetPlayerByNameEvent(String name) {
        this.name = name;
        this.launch();
    }
}
