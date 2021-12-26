package crypto.anguita.nextgenfactions.commons.events.shared;

import crypto.anguita.nextgenfactions.commons.events.NextGenFactionsEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class FactionPlayerEvent extends NextGenFactionsEvent {
    private Faction faction;
    private FPlayer player;

    public FactionPlayerEvent(Faction faction, FPlayer player) {
        this.faction = faction;
        this.player = player;
    }

    public FactionPlayerEvent(Faction faction) {
        this.faction = faction;
    }

    public FactionPlayerEvent(FPlayer player) {
        this.player = player;
    }
}
