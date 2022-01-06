package crypto.factions.bloodfactions.commons.events.faction.callback;

import crypto.factions.bloodfactions.commons.events.faction.FactionEvent;
import lombok.Getter;

@Getter
public class GetFactionLessFactionEvent extends FactionEvent {
    public GetFactionLessFactionEvent() {
        this.launch();
    }
}
