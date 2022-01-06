package crypto.factions.bloodfactions.commons.events.shared;

import crypto.factions.bloodfactions.commons.events.NextGenFactionsEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
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
