package crypto.anguita.nextgenfactionscommons.events.player;

import crypto.anguita.nextgenfactionscommons.events.NextGenFactionsEvent;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;
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
