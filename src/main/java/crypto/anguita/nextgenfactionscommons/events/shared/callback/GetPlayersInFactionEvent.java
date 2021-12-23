package crypto.anguita.nextgenfactionscommons.events.shared.callback;

import crypto.anguita.nextgenfactionscommons.events.shared.MultiPlayerFactionEvent;
import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import lombok.Getter;

@Getter
public class GetPlayersInFactionEvent extends MultiPlayerFactionEvent {
    public GetPlayersInFactionEvent(Faction faction) {
        super(faction, null);
        this.launch();
    }
}
