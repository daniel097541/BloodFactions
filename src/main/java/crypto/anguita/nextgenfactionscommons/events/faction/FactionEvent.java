package crypto.anguita.nextgenfactionscommons.events.faction;

import crypto.anguita.nextgenfactionscommons.events.NextGenFactionsEvent;
import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class FactionEvent extends NextGenFactionsEvent {
    private Faction faction;
}
