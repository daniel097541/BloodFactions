package crypto.factions.bloodfactions.commons.events.land;

import crypto.factions.bloodfactions.commons.events.PermissionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
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
