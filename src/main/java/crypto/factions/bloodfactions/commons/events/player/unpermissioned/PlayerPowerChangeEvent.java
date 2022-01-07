package crypto.factions.bloodfactions.commons.events.player.unpermissioned;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;

public class PlayerPowerChangeEvent extends PlayerEvent {

    @Getter
    private final int change;

    public PlayerPowerChangeEvent(FPlayer player, int change) {
        super(player);
        this.change = change;
        this.launch();
    }
}
