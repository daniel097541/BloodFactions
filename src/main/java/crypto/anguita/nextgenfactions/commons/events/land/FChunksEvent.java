package crypto.anguita.nextgenfactions.commons.events.land;

import crypto.anguita.nextgenfactions.commons.events.NextGenFactionsEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.land.FChunk;
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
