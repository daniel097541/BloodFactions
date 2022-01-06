package crypto.anguita.nextgenfactions.commons.events.land.permissioned;

import crypto.anguita.nextgenfactions.commons.events.land.SimpleLandEvent;
import crypto.anguita.nextgenfactions.commons.model.faction.Faction;
import crypto.anguita.nextgenfactions.commons.model.land.FChunk;
import crypto.anguita.nextgenfactions.commons.model.permission.PermissionType;
import crypto.anguita.nextgenfactions.commons.model.player.FPlayer;
import lombok.Getter;

@Getter
public class OverClaimEvent extends SimpleLandEvent {
    private final Faction overClaimedFaction;

    public OverClaimEvent(Faction faction, Faction overClaimedFaction, FPlayer player, FChunk chunk) {
        super(faction, player, PermissionType.CLAIM, chunk);
        this.overClaimedFaction = overClaimedFaction;
        this.launch();
    }
}
