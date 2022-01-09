package crypto.factions.bloodfactions.commons.events.land.permissioned;

import crypto.factions.bloodfactions.commons.events.PermissionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;

public class UnClaimAllEvent extends PermissionEvent {
    public UnClaimAllEvent(Faction faction, FPlayer player) {
        super(faction, player, PermissionType.UN_CLAIM_ALL);
        this.launch();
    }
}
