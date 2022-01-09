package crypto.factions.bloodfactions.commons.events.player.permissioned;

import crypto.factions.bloodfactions.commons.events.PermissionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerAutoFlyEvent extends PermissionEvent {

    private boolean autoFlying;

    public PlayerAutoFlyEvent(Faction faction, FPlayer player) {
        super(faction, player, PermissionType.FLY);
        this.launch();
    }
}
