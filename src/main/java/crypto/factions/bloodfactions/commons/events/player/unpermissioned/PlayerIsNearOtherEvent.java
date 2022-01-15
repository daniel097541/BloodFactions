package crypto.factions.bloodfactions.commons.events.player.unpermissioned;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;

@Getter
public class PlayerIsNearOtherEvent extends PlayerEvent {

    private final FPlayer otherPlayer;
    private final int radius;

    public PlayerIsNearOtherEvent(FPlayer player, FPlayer otherPlayer, int radius) {
        super(player);
        this.otherPlayer = otherPlayer;
        this.radius = radius;
        this.launch();
    }
}
