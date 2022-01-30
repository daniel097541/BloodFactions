package crypto.factions.bloodfactions.commons.events.land.permissioned;

import crypto.factions.bloodfactions.commons.events.land.MultiLandEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;

import java.util.Set;


@Getter
public class MultiClaimEvent extends MultiLandEvent {
    private final int radius;
    public MultiClaimEvent(Faction faction, FPlayer player, Set<FChunk> chunks, int radius) {
        super(faction, player, PermissionType.MULTI_CLAIM, chunks);
        this.radius = radius;
        this.launch();
    }
}
