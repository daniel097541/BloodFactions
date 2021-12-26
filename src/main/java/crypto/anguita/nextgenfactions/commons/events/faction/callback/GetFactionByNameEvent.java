package crypto.anguita.nextgenfactions.commons.events.faction.callback;

import crypto.anguita.nextgenfactions.commons.events.faction.FactionEvent;
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
