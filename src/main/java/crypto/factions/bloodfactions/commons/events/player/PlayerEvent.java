package crypto.factions.bloodfactions.commons.events.player;

import crypto.factions.bloodfactions.commons.events.NextGenFactionsEvent;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class PlayerEvent extends NextGenFactionsEvent {
    private FPlayer player;
}
