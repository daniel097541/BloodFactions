package crypto.anguita.nextgenfactionscommons.events.player.callback;

import crypto.anguita.nextgenfactionscommons.events.player.PlayerEvent;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GetPlayerEvent extends PlayerEvent {
    private final UUID id;

    public GetPlayerEvent(UUID id) {
        this.id = id;
        this.launch();
    }
}
