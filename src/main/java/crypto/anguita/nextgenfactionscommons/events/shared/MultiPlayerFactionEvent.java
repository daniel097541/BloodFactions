package crypto.anguita.nextgenfactionscommons.events.shared;

import crypto.anguita.nextgenfactionscommons.events.NextGenFactionsEvent;
import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class MultiPlayerFactionEvent extends NextGenFactionsEvent {
    private Faction faction;
    private Set<FPlayer> players;
}
