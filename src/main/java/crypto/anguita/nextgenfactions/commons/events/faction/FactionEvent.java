package crypto.anguita.nextgenfactions.commons.events.faction;

import crypto.anguita.nextgenfactions.commons.events.NextGenFactionsEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
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
