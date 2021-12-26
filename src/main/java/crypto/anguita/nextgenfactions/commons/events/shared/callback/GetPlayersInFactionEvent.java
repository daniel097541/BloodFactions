package crypto.anguita.nextgenfactions.commons.events.shared.callback;

import crypto.anguita.nextgenfactions.commons.events.shared.MultiPlayerFactionEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import lombok.Getter;

@Getter
public class GetPlayersInFactionEvent extends MultiPlayerFactionEvent {
    public GetPlayersInFactionEvent(Faction faction) {
        super(faction, null);
        this.launch();
    }
}
