package crypto.factions.bloodfactions.commons.events.player.unpermissioned;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;

@Getter
public class SavePlayerEvent extends PlayerEvent {
    public SavePlayerEvent(FPlayer player) {
        super(player);
        this.launch();
    }
}
