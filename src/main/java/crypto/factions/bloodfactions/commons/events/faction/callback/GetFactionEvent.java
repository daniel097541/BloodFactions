package crypto.factions.bloodfactions.commons.events.faction.callback;

import crypto.factions.bloodfactions.commons.events.faction.FactionEvent;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
public class GetFactionEvent extends FactionEvent {
    private final UUID id;

    public GetFactionEvent(@NotNull UUID id) {
        this.id = id;
        this.launch();
    }
}
