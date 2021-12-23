package crypto.anguita.nextgenfactionscommons.events.faction.action;

import crypto.anguita.nextgenfactionscommons.events.faction.FactionEvent;
import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import lombok.Getter;

@Getter
public class CreateFactionEvent extends FactionEvent {
    public CreateFactionEvent(Faction faction) {
        super(faction);
    }
}
