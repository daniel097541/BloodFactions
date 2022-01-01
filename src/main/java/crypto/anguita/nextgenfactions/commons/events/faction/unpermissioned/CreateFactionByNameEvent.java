package crypto.anguita.nextgenfactions.commons.events.faction.unpermissioned;

import crypto.anguita.nextgenfactions.commons.events.shared.FactionPlayerEvent;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import lombok.Getter;

@Getter
public class CreateFactionByNameEvent extends FactionPlayerEvent {

    private final String name;

    public CreateFactionByNameEvent(String name, FPlayer player) {
        super(player);
        this.name = name;
        this.launch();
    }
}
