package crypto.factions.bloodfactions.commons.events.land.permissioned;

import crypto.factions.bloodfactions.commons.events.land.SimpleLandEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FChunk;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

public class ClaimEvent extends SimpleLandEvent {
    public ClaimEvent(Faction faction, FPlayer player, FChunk chunk) {
        super(faction, player, PermissionType.CLAIM, chunk);
        this.launch();
    }
}
