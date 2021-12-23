package crypto.anguita.nextgenfactionscommons.events.faction.callback;

import crypto.anguita.nextgenfactionscommons.events.faction.FactionEvent;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class GetFactionByNameEvent extends FactionEvent {
    private final String name;

    public GetFactionByNameEvent(@NotNull String name) {
        this.name = name;
        this.launch();
    }
}
