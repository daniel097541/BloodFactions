package crypto.factions.bloodfactions.commons.events.faction.callback;

import crypto.factions.bloodfactions.commons.events.faction.FactionEvent;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class GetFactionAtChunkEvent extends FactionEvent {

    private final FChunk chunk;

    public GetFactionAtChunkEvent(@NotNull FChunk chunk) {
        super();
        this.chunk = chunk;
        this.launch();
    }
}
