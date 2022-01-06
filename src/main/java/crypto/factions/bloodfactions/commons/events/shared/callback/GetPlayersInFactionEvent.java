package crypto.factions.bloodfactions.commons.events.shared.callback;

import crypto.factions.bloodfactions.commons.events.shared.MultiPlayerFactionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import lombok.Getter;

@Getter
public class GetPlayersInFactionEvent extends MultiPlayerFactionEvent {
    public GetPlayersInFactionEvent(Faction faction) {
        super(faction, null);
        this.launch();
    }
}
