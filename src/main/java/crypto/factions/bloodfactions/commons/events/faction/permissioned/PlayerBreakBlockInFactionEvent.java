package crypto.factions.bloodfactions.commons.events.faction.permissioned;

import crypto.factions.bloodfactions.commons.events.PermissionEvent;
import crypto.factions.bloodfactions.commons.model.faction.Faction;
import crypto.factions.bloodfactions.commons.model.land.FLocation;
import crypto.factions.bloodfactions.commons.model.permission.PermissionType;
import crypto.factions.bloodfactions.commons.model.player.FPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerBreakBlockInFactionEvent extends PermissionEvent {

    private final FLocation location;

    public PlayerBreakBlockInFactionEvent(Faction faction, FPlayer player, FLocation location) {
        super(faction, player, PermissionType.BREAK_BLOCKS);
        this.location = location;
        this.launch();
    }
}
