package crypto.anguita.nextgenfactions.commons.events.player;

import crypto.anguita.nextgenfactions.commons.events.NextGenFactionsEvent;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
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
