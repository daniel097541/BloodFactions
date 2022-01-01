package crypto.anguita.nextgenfactions.commons.events.faction.unpermissioned;

import crypto.anguita.nextgenfactions.commons.events.shared.FactionPlayerEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import lombok.Getter;

@Getter
public class CreateFactionEvent extends FactionPlayerEvent {
    public CreateFactionEvent(Faction faction, FPlayer player) {
        super(faction, player);
    }
}
