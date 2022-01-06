package crypto.factions.bloodfactions.commons.events.land.permissioned;

import crypto.factions.bloodfactions.commons.events.land.MultiLandEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

import java.util.Set;

public class MultiUnClaimEvent extends MultiLandEvent {

    public MultiUnClaimEvent(Faction faction, FPlayer player, Set<FChunk> chunks) {
        super(faction, player, PermissionType.MULTI_UN_CLAIM, chunks);
    }
}
