package crypto.anguita.nextgenfactionscommons.events.land.callback;

import crypto.anguita.nextgenfactionscommons.events.land.FChunksEvent;
import crypto.anguita.nextgenfactionscommons.model.faction.Faction;

public class GetClaimsOfFactionEvent extends FChunksEvent {
    public GetClaimsOfFactionEvent(Faction faction) {
        super(faction);
    }
}
