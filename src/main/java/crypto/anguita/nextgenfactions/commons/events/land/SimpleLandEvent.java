package crypto.anguita.nextgenfactions.commons.events.land;

import crypto.anguita.nextgenfactions.commons.events.PermissionEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.land.FChunk;
import crypto.anguita.nextgenfactions.commons.model.permission.PermissionType;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
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
