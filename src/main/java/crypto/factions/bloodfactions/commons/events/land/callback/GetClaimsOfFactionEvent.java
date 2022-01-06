package crypto.factions.bloodfactions.commons.events.land.callback;

import crypto.factions.bloodfactions.commons.events.land.FChunksEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;

public class GetClaimsOfFactionEvent extends FChunksEvent {
    public GetClaimsOfFactionEvent(Faction faction) {
        super(faction);
    }
}
