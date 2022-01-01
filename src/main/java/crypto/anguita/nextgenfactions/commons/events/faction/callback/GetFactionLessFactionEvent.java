package crypto.anguita.nextgenfactions.commons.events.faction.callback;

import crypto.anguita.nextgenfactions.commons.events.faction.FactionEvent;
import lombok.Getter;

@Getter
public class GetFactionLessFactionEvent extends FactionEvent {
    public GetFactionLessFactionEvent() {
        this.launch();
    }
}
