package crypto.anguita.nextgenfactionscommons.events.land;

import crypto.anguita.nextgenfactionscommons.events.NextGenFactionsEvent;
import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import crypto.anguita.nextgenfactionscommons.model.land.FChunk;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public abstract class FChunksEvent extends NextGenFactionsEvent {

    private final Faction faction;
    private Set<FChunk> chunks;

    public FChunksEvent(Faction faction) {
        this.faction = faction;
    }
}
