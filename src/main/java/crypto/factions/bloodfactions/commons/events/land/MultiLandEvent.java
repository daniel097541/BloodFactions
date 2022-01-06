package crypto.factions.bloodfactions.commons.events.land;

import crypto.factions.bloodfactions.commons.events.PermissionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
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
