package crypto.factions.bloodfactions.commons.events.player.callback;

import crypto.factions.bloodfactions.commons.events.NextGenFactionsEvent;
import crypto.factions.bloodfactions.commons.model.land.FLocation;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class FindPlayersInRadiusEvent extends NextGenFactionsEvent {

    private Set<FPlayer> nearPlayers;
    private final int radius;
    private final FLocation location;

    public FindPlayersInRadiusEvent(FLocation location, int radius) {
        this.radius = radius;
        this.location = location;
        this.launch();
    }
}
