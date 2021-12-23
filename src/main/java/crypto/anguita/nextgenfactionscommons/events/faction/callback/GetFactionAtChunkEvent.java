package crypto.anguita.nextgenfactionscommons.events.faction.callback;

import crypto.anguita.nextgenfactionscommons.events.faction.FactionEvent;
import crypto.anguita.nextgenfactionscommons.model.land.FChunk;
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
