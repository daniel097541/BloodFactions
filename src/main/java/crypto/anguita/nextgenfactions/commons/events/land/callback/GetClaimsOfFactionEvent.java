package crypto.anguita.nextgenfactions.commons.events.land.callback;

import crypto.anguita.nextgenfactions.commons.events.land.FChunksEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;

public class GetClaimsOfFactionEvent extends FChunksEvent {
    public GetClaimsOfFactionEvent(Faction faction) {
        super(faction);
    }
}
