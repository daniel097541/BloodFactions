package crypto.anguita.nextgenfactionscommons.events.player.callback;

import crypto.anguita.nextgenfactionscommons.events.player.PlayerEvent;
import crypto.anguita.nextgenfactionscommons.model.permission.Action;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerHasPermissionEvent extends PlayerEvent {

    private final Action action;
    private boolean hasPermission;

    public PlayerHasPermissionEvent(FPlayer player, Action action) {
        super(player);
        this.action = action;
        this.launch();
    }
}
