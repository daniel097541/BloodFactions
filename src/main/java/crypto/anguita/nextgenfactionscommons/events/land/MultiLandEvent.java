package crypto.anguita.nextgenfactionscommons.events.land;

import crypto.anguita.nextgenfactionscommons.events.PermissionEvent;
import crypto.anguita.nextgenfactionscommons.model.faction.Faction;
import crypto.anguita.nextgenfactionscommons.model.land.FChunk;
import crypto.anguita.nextgenfactionscommons.model.permission.PermissionType;
import crypto.anguita.nextgenfactionscommons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public abstract class MultiLandEvent extends PermissionEvent {

    private final Set<FChunk> chunks;
    private Map<FChunk, Boolean> result;

    public MultiLandEvent(Faction faction, FPlayer player, PermissionType permissionType, Set<FChunk> chunks) {
        super(faction, player, permissionType);
        this.chunks = chunks;
    }
}
