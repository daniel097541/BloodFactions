package crypto.anguita.nextgenfactionscommons.events.land;

import crypto.anguita.nextgenfactionscommons.events.PermissionEvent;
import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import crypto.anguita.nextgenfactionscommons.model.land.FChunk;
import crypto.anguita.nextgenfactionscommons.model.permission.PermissionType;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SimpleLandEvent extends PermissionEvent {

    private final FChunk chunk;

    public SimpleLandEvent(Faction faction, FPlayer player, PermissionType permissionType, FChunk chunk) {
        super(faction, player, permissionType);
        this.chunk = chunk;
    }
}
