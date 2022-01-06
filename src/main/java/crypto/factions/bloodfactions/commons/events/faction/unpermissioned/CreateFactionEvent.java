package crypto.factions.bloodfactions.commons.events.faction.unpermissioned;

import crypto.factions.bloodfactions.commons.events.shared.FactionPlayerEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;

@Getter
public class CreateFactionEvent extends FactionPlayerEvent {
    public CreateFactionEvent(Faction faction, FPlayer player) {
        super(faction, player);
    }
}
