package crypto.anguita.nextgenfactionscommons.events.faction.action;

import crypto.anguita.nextgenfactionscommons.events.shared.FactionPlayerEvent;
import lombok.Getter;

@Getter
public class CreateFactionByNameEvent extends FactionPlayerEvent {

    private final String name;

    public CreateFactionByNameEvent(String name) {
        super();
        this.name = name;
        this.launch();
    }
}
