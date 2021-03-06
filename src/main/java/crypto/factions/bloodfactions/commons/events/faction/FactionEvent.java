package crypto.factions.bloodfactions.commons.events.faction;

import crypto.factions.bloodfactions.commons.events.NextGenFactionsEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
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
