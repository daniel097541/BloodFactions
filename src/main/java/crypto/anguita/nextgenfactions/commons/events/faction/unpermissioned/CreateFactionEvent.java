package crypto.anguita.nextgenfactions.commons.events.faction.unpermissioned;

import crypto.anguita.nextgenfactions.commons.events.faction.FactionEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import lombok.Getter;

@Getter
public class CreateFactionEvent extends FactionEvent {
    public CreateFactionEvent(Faction faction) {
        super(faction);
    }
}
