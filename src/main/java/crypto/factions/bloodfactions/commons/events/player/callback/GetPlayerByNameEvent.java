package crypto.factions.bloodfactions.commons.events.player.callback;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import lombok.Getter;

@Getter
public class GetPlayerByNameEvent extends PlayerEvent {
    private final String name;

    public GetPlayerByNameEvent(String name) {
        this.name = name;
        this.launch();
    }
}
