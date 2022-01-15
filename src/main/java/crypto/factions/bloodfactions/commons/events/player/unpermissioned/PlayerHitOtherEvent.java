package crypto.factions.bloodfactions.commons.events.player.unpermissioned;

import crypto.factions.bloodfactions.commons.events.player.PlayerEvent;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;

@Getter
public class PlayerHitOtherEvent extends PlayerEvent {

    private final FPlayer otherPlayer;

    public PlayerHitOtherEvent(FPlayer player, FPlayer otherPlayer) {
        super(player);
        this.otherPlayer = otherPlayer;
        this.launch();
    }
}
