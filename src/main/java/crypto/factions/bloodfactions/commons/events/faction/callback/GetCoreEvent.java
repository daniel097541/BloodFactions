package crypto.factions.bloodfactions.commons.events.faction.callback;

import crypto.factions.bloodfactions.commons.events.faction.FactionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FLocation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCoreEvent extends FactionEvent {

    private FLocation core;

    public GetCoreEvent(Faction faction) {
        super(faction);
        this.launch();
    }
}
