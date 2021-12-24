package crypto.anguita.nextgenfactionscommons.events.land.permissioned;

import crypto.anguita.nextgenfactionscommons.events.land.SimpleLandEvent;
import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import crypto.anguita.nextgenfactionscommons.model.land.FChunk;
import crypto.anguita.nextgenfactionscommons.model.permission.PermissionType;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;

public class ClaimEvent extends SimpleLandEvent {
    public ClaimEvent(Faction faction, FPlayer player, FChunk chunk) {
        super(faction, player, PermissionType.CLAIM, chunk);
        this.launch();
    }
}
