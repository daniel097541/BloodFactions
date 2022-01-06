package crypto.factions.bloodfactions.commons.events.faction.unpermissioned;

import crypto.factions.bloodfactions.commons.events.shared.FactionPlayerEvent;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
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
