package crypto.anguita.nextgenfactions.commons.events.land.permissioned;

import crypto.anguita.nextgenfactions.commons.events.land.MultiLandEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.land.FChunk;
import crypto.anguita.nextgenfactions.commons.model.permission.PermissionType;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;

import java.util.Set;


public class MultiClaimEvent extends MultiLandEvent {
    public MultiClaimEvent(Faction faction, FPlayer player, Set<FChunk> chunks) {
        super(faction, player, PermissionType.MULTI_CLAIM, chunks);
        this.launch();
    }
}
