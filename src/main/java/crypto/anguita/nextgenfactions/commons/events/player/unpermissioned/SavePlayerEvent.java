package crypto.anguita.nextgenfactions.commons.events.player.unpermissioned;

import crypto.anguita.nextgenfactions.commons.events.player.PlayerEvent;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import lombok.Getter;

@Getter
public class SavePlayerEvent extends PlayerEvent {
    public SavePlayerEvent(FPlayer player) {
        super(player);
        this.launch();
    }
}
