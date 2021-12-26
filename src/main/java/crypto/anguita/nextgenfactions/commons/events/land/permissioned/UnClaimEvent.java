package crypto.anguita.nextgenfactions.commons.events.land.permissioned;

import crypto.anguita.nextgenfactions.commons.events.land.SimpleLandEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.land.FChunk;
import crypto.anguita.nextgenfactions.commons.model.permission.PermissionType;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;

public class UnClaimEvent extends SimpleLandEvent {
    public UnClaimEvent(Faction faction, FPlayer player, FChunk chunk) {
        super(faction, player, PermissionType.UN_CLAIM, chunk);
    }
}
