package crypto.factions.bloodfactions.commons.events.player.unpermissioned;

import crypto.factions.bloodfactions.commons.events.shared.FactionPlayerEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerLeftFactionEvent extends FactionPlayerEvent {

    private boolean left;

    public PlayerLeftFactionEvent(Faction faction, FPlayer player) {
        super(faction, player);
        this.launch();
    }
}
