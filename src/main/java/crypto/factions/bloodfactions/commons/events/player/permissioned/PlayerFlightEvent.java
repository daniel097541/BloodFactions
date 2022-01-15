package crypto.factions.bloodfactions.commons.events.player.permissioned;

import crypto.factions.bloodfactions.commons.events.PermissionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerFlightEvent extends PermissionEvent {

    private boolean flying;
    private final boolean toggle;

    public PlayerFlightEvent(Faction faction, FPlayer player, boolean toggle) {
        super(faction, player, PermissionType.FLY);
        this.toggle = toggle;
        this.launch();
    }
}
