package crypto.anguita.nextgenfactions.commons.events.faction.unpermissioned;

import crypto.anguita.nextgenfactions.commons.events.shared.FactionPlayerEvent;
import lombok.Getter;

@Getter
public class CreateFactionByNameEvent extends FactionPlayerEvent {

    private final String name;

    public CreateFactionByNameEvent(String name) {
        super();
        this.name = name;
        this.launch();
    }
}
