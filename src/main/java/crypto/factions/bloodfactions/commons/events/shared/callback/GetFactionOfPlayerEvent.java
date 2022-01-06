package crypto.factions.bloodfactions.commons.events.shared.callback;

import crypto.factions.bloodfactions.commons.events.shared.FactionPlayerEvent;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;

@Getter
public class GetFactionOfPlayerEvent extends FactionPlayerEvent {
    public GetFactionOfPlayerEvent(FPlayer player) {
        super(null, player);
        this.launch();
    }
}
