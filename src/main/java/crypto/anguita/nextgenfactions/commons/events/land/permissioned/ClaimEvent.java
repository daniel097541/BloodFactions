package crypto.anguita.nextgenfactions.commons.events.land.permissioned;

import crypto.anguita.nextgenfactions.commons.events.land.SimpleLandEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.land.FChunk;
import crypto.anguita.nextgenfactions.commons.model.permission.PermissionType;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;

public class ClaimEvent extends SimpleLandEvent {
    public ClaimEvent(Faction faction, FPlayer player, FChunk chunk) {
        super(faction, player, PermissionType.CLAIM, chunk);
        this.launch();
    }
}
