package crypto.factions.bloodfactions.commons.events.land.permissioned;

import crypto.factions.bloodfactions.commons.events.land.SimpleLandEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
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
